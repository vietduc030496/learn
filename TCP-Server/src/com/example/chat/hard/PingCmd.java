package com.example.chat.hard;

public class PingCmd {

    public String handle(String[] command) {
        if (command.length == 1) {
            return "PONG";
        } else if (command.length == 2) {
            return command[1];
        }

        return "(error) ERR wrong number of arguments for 'ping' command";
    }
}
