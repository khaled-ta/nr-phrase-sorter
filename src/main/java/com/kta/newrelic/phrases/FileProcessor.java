package com.kta.newrelic.phrases;

import com.kta.newrelic.exceptions.FileLoadingException;

import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class FileProcessor {

    private static final Pattern pattern = Pattern.compile("[^A-Za-z0-9-_’']+");

    private final FileLoader fileLoader;

    public FileProcessor(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public String process(String filename, ConcurrentHashMap<String, Integer> phraseCounterMap) throws FileLoadingException {
        long start = System.currentTimeMillis();

        String fileContents = fileLoader.loadFile(filename);
        String fileContentsTransformed = replaceNonAlphaNumericCharsWithSpace(fileContents);

        if (fileContentsTransformed.length() == 0) {
            return String.format("filename %s is empty, process ending in %d milliSeconds!",
                    filename,
                    (System.currentTimeMillis() - start));
        }
        enumeratePhrases(fileContentsTransformed, phraseCounterMap);

        return String.format("filename %s completed successfully in %d milliSeconds!",
                filename,
                (System.currentTimeMillis() - start));
    }

    private String replaceNonAlphaNumericCharsWithSpace(String fileContents) {
        return pattern.matcher(fileContents).replaceAll(" ").toLowerCase().trim();
    }

    @Deprecated
    private void enumeratePhrasesAlt(String text, ConcurrentHashMap<String, Integer> phraseCounterMap) {
        Iterable<Object> wordsIter = () -> new StringTokenizer(text, " ").asIterator();
        String[] words = StreamSupport
                .stream(wordsIter.spliterator(), false)
                .map(o -> (String) o)
                .toArray(String[]::new);

        enumeratePhrases(words, phraseCounterMap);
    }

    private void enumeratePhrases(String text, ConcurrentHashMap<String, Integer> phraseCounterMap) {
        String[] words = text.split(" ");
        enumeratePhrases(words, phraseCounterMap);
    }

    /**
     * loop through file content and count phrase occurrences; a phrase is a sequence of 3 adjacent words
     *
     * @param words            array of all words in file
     * @param phraseCounterMap data structure to store phrase count
     */
    private void enumeratePhrases(String[] words, ConcurrentHashMap<String, Integer> phraseCounterMap) {
        if (words.length > 2) {
            for (int i = 2; i < words.length; i++) {
                String key = (words[i - 2] + " " + words[i - 1] + " " + words[i]).toLowerCase().trim();
                phraseCounterMap.compute(key, (k, v) -> (v == null) ? 1 : ++v);
            }
        }
    }
}
