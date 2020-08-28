package com.senpure.cli;

import com.beust.jcommander.JCommander;

import java.util.List;

/**
 * RootCommand
 *
 * @author senpure
 * @time 2020-07-28 10:59:28
 */
public class RootCommand extends AbstractCommand {
    public static final String DEFAULT_COMPLETION_CHAR = "\t";
    private final JCommander rootCommander;

    private final CommandProcess commandProcess;

    private final CommandSplitter commandSplitter;

    private String completionChar = DEFAULT_COMPLETION_CHAR;
    /**
     * 当补全只有一个选项时是否修饰为一个完整的命令
     */
    private boolean fullCompletion = true;
    private String command;

    public RootCommand(JCommander rootCommander) {
        this(rootCommander, new DefaultCommandProcess(), new DefaultCommandSplitter());
    }

    public RootCommand(JCommander rootCommander, CommandProcess commandProcess, CommandSplitter commandSplitter) {
        this.rootCommander = rootCommander;
        this.commandProcess = commandProcess;
        this.commandSplitter = commandSplitter;

    }

    public void process(String command) {
        if (command == null||command.trim().isEmpty()) {
            return;
        }
        this.command = command;

        if (command.endsWith(completionChar)) {
            do {
                command = command.substring(0, command.length() - completionChar.length());

            }
            while (command.endsWith(completionChar));
            String[] cmdArray = commandSplitter.split(command);
            completion(command, cmdArray);

        } else {
            String[] cmdArray = commandSplitter.split(command);
            try {
                rootCommander.parse(cmdArray);
                process(commandProcess);
            } catch (Exception e) {
                String message = e.toString();
                commandProcess.feed(message);
            }

        }

    }

    public void completion(String command) {
        String[] cmdArray = commandSplitter.split(command);
        completion(command, cmdArray);
    }

    private void completion(String command, String[] cmdArray) {
        List<String> options = CompletionUtil.completion(rootCommander, command, cmdArray);
        if (fullCompletion && options.size() == 1) {
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
    }

    public void process(CommandProcess process) {
        String command = rootCommander.getParsedCommand();
        if (command != null) {
            JCommander jCommander = rootCommander.getCommands().get(command);
            process(jCommander, process);
        } else {
            process.feed("无效命令[" + this.command + "]");
            // process(commander, process);
        }
    }


    private void process(JCommander commander, CommandProcess process) {
        Object obj = commander.getObjects().get(0);
        if (obj == null) {
            return;
        }
        if (obj instanceof Command) {
            Command cmd = (Command) obj;
            cmd.process(process);
        }
    }

    public void setFullCompletion(boolean fullCompletion) {
        this.fullCompletion = fullCompletion;
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

    public CommandProcess getCommandProcess() {
        return commandProcess;
    }
}
