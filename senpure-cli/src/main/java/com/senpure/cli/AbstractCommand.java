package com.senpure.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.UsageFormatter;

/**
 * AbsCommand
 *
 * @author senpure
 * @time 2020-07-28 10:23:28
 */
public abstract class AbstractCommand implements Command {
    protected JCommander commander;
    protected UsageFormatter usageFormatter;

    public AbstractCommand() {
        commander = new JCommander(this);
        Parameters parameters = getClass().getAnnotation(Parameters.class);
        String name = "";
        if (parameters != null) {

            for (String commandName : parameters.commandNames()) {
                if (commandName.length() > name.length()) {
                    name = commandName;
                }
            }

        }
        if (name.length() == 0) {
            name = getClass().getSimpleName();
            if (name.endsWith("Command")) {
                name = name.substring(0, name.length() - 7);
            }
        }
        commander.setProgramName(name);

        usageFormatter = new UsageFormatter(commander);
    }

    @Override
    public String usage() {
        StringBuilder out = new StringBuilder();
        usageFormatter.usage(out);
        return out.toString();
    }

    public JCommander getCommander() {
        return commander;
    }
}
