package com.senpure.cli;

import com.beust.jcommander.JCommander;

import java.util.StringTokenizer;

/**
 * RootCommand
 *
 * @author senpure
 * @time 2020-07-28 10:59:28
 */
public class RootCommand extends AbstractCommand {

    private final JCommander rootCommander;


    private final CommandProcessFactory commandProcessFactory;

    public RootCommand(JCommander rootCommander) {
        this(rootCommander,DefaultCommandProcess::new);
    }

    public RootCommand(JCommander rootCommander, CommandProcessFactory commandProcessFactory) {
        this.rootCommander = rootCommander;
        this.commandProcessFactory = commandProcessFactory;

    }

    public void process(String command) {
        if (command == null) {
            return;
        }
        StringTokenizer st = new StringTokenizer(command);
        String[] cmdArray = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            cmdArray[i] = st.nextToken();
        }
        if (command.endsWith("\t")) {
            CompletionUtil.completion(rootCommander, command);
        } else {
            CommandProcess commandProcess = commandProcessFactory.get();
            try {
                rootCommander.parse(cmdArray);
                process(commandProcess);
            } catch (Exception e) {
                commandProcess.feed(e.getMessage());
            }

        }


    }

    public void process(CommandProcess process) {

        String command = rootCommander.getParsedCommand();

        if (command != null) {
            JCommander jCommander = rootCommander.getCommands().get(command);
            process(jCommander, process);

        } else {

            process(commander, process);
        }
    }


    private void process(JCommander commander, CommandProcess process) {
        Object obj = commander.getObjects().get(0);
        if (obj instanceof Command) {
            Command cmd = (Command) obj;
            cmd.process(process);
        }
    }

    public JCommander getRootCommander() {
        return rootCommander;
    }
}
