package com.sevendragons.hashcode2016.deliverysystem;

import java.util.LinkedList;
import java.util.List;

public class Output {
    final List<Command> commands;

    public Output(List<Command> commands) {
        this.commands = commands;
    }

    public List<String> toOutputLines() {
        int lineCount = 0;

        LinkedList<String> lines = new LinkedList<>();
        for (Command command : commands) {
            List<String> outputLines = command.generateOutputLines();
            for (String line : outputLines) {
                lines.add(line);
            }
            lineCount += outputLines.size();
        }
        lines.addFirst(lineCount + "\n");
        return lines;
    }
}
