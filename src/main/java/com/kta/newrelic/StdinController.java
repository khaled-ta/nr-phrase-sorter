package com.kta.newrelic;

import com.kta.newrelic.phrases.PhraseEnumerationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

@Slf4j
public class StdinController {
    public static void listen() throws InterruptedException {
        log.debug("start listening to user input");
        Scanner scanner = new Scanner(System.in);
        String usrMsg = "*** enter files from which to count and sort most used phrases; or type `q` to quit";
        stdOutMsg(usrMsg);

        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            if (StringUtils.isBlank(next)) {
                stdOutMsg(usrMsg);
                continue;
            }
            if ("q".equals(next)) {
                System.exit(0);
            }
            new PhraseEnumerationService().execute(next.split(" "));
            stdOutMsg(usrMsg);
        }
    }

    private static void stdOutMsg(String usrMsg) {
        System.out.println(usrMsg);
    }
}
