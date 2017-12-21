package com.oop_pub.clase_interne.Bash;

class Command {
    private Bash bash;
    private String command;

    Command(String command, Bash bash) {
        this.bash = bash;
        this.command = command;
    }

    Bash getBash() {
        return bash;
    }

    String getCommand() {
        return command;
    }
}
