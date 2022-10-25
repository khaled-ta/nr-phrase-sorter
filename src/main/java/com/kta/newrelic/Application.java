package com.kta.newrelic;

import com.kta.newrelic.phrases.PhraseEnumerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * Evaluate
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) throws InterruptedException {
        log.debug("starting app...");
        if (args != null && args.length > 0) {
            log.debug("parsing files for {}", Arrays.toString(args));
            new PhraseEnumerationService().execute(args);
        }

        StdinController.listen();
    }
}