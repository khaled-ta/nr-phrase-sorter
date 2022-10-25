package com.kta.newrelic.phrases;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class PhraseEnumerationService {

    private final ConcurrentHashMap<String, Integer> phraseMap = new ConcurrentHashMap<>();

    public void execute(String... filenames) throws InterruptedException {
        long start = System.currentTimeMillis();

        // todo: check number of filenames, if more than 1, then use ExecutorService
        HashSet<String> uniqueFilenames = new HashSet<>(Arrays.asList(filenames));
        ExecutorService executorService = Executors.newFixedThreadPool(uniqueFilenames.size());

        List<Callable<String>> callables = uniqueFilenames.stream()
                .map(f -> (Callable<String>) ()
                        -> new FileProcessor(new FileLoader()).process(f, phraseMap))
                .collect(Collectors.toList());
        List<Future<String>> futures = executorService.invokeAll(callables);

        // shutdown executor service once all tasks run successfully
        executorService.shutdown();

        try {
//             add 1 minute timeout if execution hangs (value can be modified)
            boolean termination = executorService.awaitTermination(1, TimeUnit.MINUTES);
            log.info("tasks completed in {} microseconds without interruption: {}", (System.currentTimeMillis() - start), termination);
        } catch (InterruptedException e) {
            log.error("running tasks interrupted", e);
        }

        handleFuturesOutcomes(futures);

        List<Map.Entry<String, Integer>> phrases = sortPhrasesByCountDesc(phraseMap);
        for (int i = 0; i < phrases.size(); i++) {
            System.out.printf("%d. %s: %s%n", (i + 1), phrases.get(i).getKey(), phrases.get(i).getValue());
        }
    }

    /**
     * Handle outcome of futures outcome triggered by execution of callable
     *
     * @param futures processing state of file
     */
    private static void handleFuturesOutcomes(List<Future<String>> futures) {
        futures.forEach(future -> {
            try {
                log.debug(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("unable to complete {}", future);
            }
        });
    }

    private static List<Map.Entry<String, Integer>> sortPhrasesByCountDesc(ConcurrentHashMap<String, Integer> phrasesMap) {
        return phrasesMap.entrySet()
                .stream()
                .sorted(new PhraseSortingByDescWordCount())
                .limit(100)
                .collect(Collectors.toList());
    }
}
