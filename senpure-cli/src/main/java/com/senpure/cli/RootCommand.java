package com.senpure.cli;

import com.beust.jcommander.JCommander;

import java.util.List;
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

    private String completionChar = CompletionUtil.DEFAULT_COMPLETION_CHAR;

    public RootCommand(JCommander rootCommander) {
        this(rootCommander, DefaultCommandProcess::new);
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
        CommandProcess commandProcess = commandProcessFactory.get();
        if (command.endsWith(completionChar)) {
            do {
                command = command.substring(0, command.length() - completionChar.length());

            }
            while (command.endsWith(completionChar));
            List<String> options = CompletionUtil.completion(rootCommander, command, completionChar);
            if (options.size() == 1) {

                if (!command.endsWith(" ")) {
                    String end = cmdArray[cmdArray.length - 1];
                    int index = command.lastIndexOf(end);
                    if (index > -1) {
                        command = command.substring(0, index);
                    }
                }
                command += options.get(0);
                commandProcess.completion(command);
            } else {
                commandProcess.completionOptions(options);
            }

        } else {

            try {
                rootCommander.parse(cmdArray);
                process(commandProcess);
            } catch (Exception e) {
                String message = e.toString();
                commandProcess.feed(message);
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

    public String getCompletionChar() {
        return completionChar;
    }

    public void setCompletionChar(String completionChar) {
        this.completionChar = completionChar;
    }
}
