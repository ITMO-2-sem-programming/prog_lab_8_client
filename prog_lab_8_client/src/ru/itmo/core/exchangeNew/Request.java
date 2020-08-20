package ru.itmo.core.exchangeNew;


import ru.itmo.core.command.Command;

import java.io.Serializable;
import java.util.Arrays;
import java.util.PriorityQueue;



public class Request implements Serializable {


    private PriorityQueue<Command> commandQueue
            = new PriorityQueue<>();



    public Request(PriorityQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }


    public Request(Command command) {
        addCommand(command);
    }


    public Request(Command... commands) {

        Arrays.stream(commands).forEach(
                this::addCommand
        );
    }


    public void addCommand(Command command) {
        commandQueue.add(command);
    }


    public PriorityQueue<Command> getCommandQueue() {
        return commandQueue;
    }



}
