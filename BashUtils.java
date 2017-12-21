package com.oop_pub.clase_interne.Bash;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

enum Commands {
    CD("cd"),
    LS("ls"),
    ECHO("echo"),
    HISTORY("history");

    private final String text;

    Commands(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

class BashUtils {

    public static class Cd implements CommandSubscriber {

        @Override
        public void executeCommand(Command c) {
            if (c.getCommand().startsWith(Commands.CD.toString())) {
                c.getBash().currentDirectory = Paths.get(
                        c.getBash().currentDirectory.toString(),
                        c.getCommand().substring(3, c.getCommand().length())).toAbsolutePath();
            }
        }
    }

    public static class Echo implements CommandSubscriber {
        @Override
        public void executeCommand(Command c) {
            if (c.getCommand().startsWith(Commands.ECHO.toString())) {
                System.out.println(c.getCommand().substring(5, c.getCommand().length()));
            }
        }
    }

    public static class Ls implements CommandSubscriber {
        @Override
        public void executeCommand(Command c) {
            if (c.getCommand().startsWith(Commands.LS.toString())) {
                listDirContents(c.getBash().currentDirectory);
            }
        }

        private void listDirContents(Path dirPath) {
            File folder = dirPath.toFile();
            File[] listOfFiles = folder.listFiles();

            System.out.println(dirPath);

            for (File file : listOfFiles) {
                System.out.println(file.getName());
            }
        }
    }

    public static class History implements CommandSubscriber {

        @Override
        public void executeCommand(Command c) {
            c.getBash().history.append(c.getCommand());
            c.getBash().history.append(" | ");

            if (c.getCommand().startsWith(Commands.HISTORY.toString())) {
                System.out.println("History is: " + c.getBash().history);
            }
        }
    }

}
