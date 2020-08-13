package com.senpure.cli;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * CommandProcess
 *
 * @author senpure
 * @time 2020-07-23 16:01:20
 */
public interface CommandProcess {

    default void feed(String feed) {
        System.out.println(feed);
    }

    default void completionOptions(@Nonnull List<String> options) {

        for (String option : options) {
            System.out.println(option);
        }
    }

    default void completion(String command) {
        System.out.println(command);
    }
}
