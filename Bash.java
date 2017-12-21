package com.oop_pub.clase_interne.Bash;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Bash {
    Path currentDirectory;
    StringBuffer history;

    private CommandPublisher publisher;
    private static final String EXIT = "exit";

    public Bash() {
        history = new StringBuffer();
        currentDirectory = Paths.get(".").toAbsolutePath();

        publisher = new BashCommandPublisher();
        publisher.subscribe(new BashUtils.History());
        publisher.subscribe(new BashUtils.Ls());
        publisher.subscribe(new BashUtils.Echo());
        publisher.subscribe(new BashUtils.Cd());
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals(EXIT)) {
                break;
            }

            final Bash bashInstance = this;

            Thread t = new Thread() {
                public void run() {
                    publisher.publish(new Command(input, bashInstance));
                }
            };
            t.start();
        }
    }

    private static class BashCommandPublisher implements CommandPublisher {
        ArrayList<CommandSubscriber> subscribers = new ArrayList<>();

        @Override
        public void subscribe(CommandSubscriber s) {
            subscribers.add(s);
        }

        @Override
        public void publish(Command command) {
            for (CommandSubscriber s : subscribers) {
                s.executeCommand(command);
            }
        }
    }
}
