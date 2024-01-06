package org.charot.edu.platform;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;

public class ChatGPTMAin2 {
    public static void main(String[] args) throws Exception {
        Config config = ConfigBuilder.withAccessToken("45c77dddb72942a0a1ae7ab504418610")
                .environment("production")
                .codeVersion("1.0.0")
                .build();
        Rollbar rollbar = new Rollbar(config);

        try {
            //rollbar.
            // Code for interacting with the ChatGPT API
        } catch (Exception e) {
            rollbar.error(e);
        } finally {
            rollbar.close(false);
        }
    }

}
