package ru.itmo.core.common.exchange.request;


import ru.itmo.core.common.exchange.User;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.PriorityQueue;



public class Request implements Serializable {


    private User user;

    private PriorityQueue<ClientRequest> commandQueue
            = new PriorityQueue<>();

    private InetAddress clientInetAddress;
    private int port;


    public Request(PriorityQueue<ClientRequest> commandQueue) {
        this.commandQueue = commandQueue;
    }


    public Request(ClientRequest command) {
        addCommand(command);
    }


    public Request(ClientRequest... commands) {

        Arrays.stream(commands).forEach(
                this::addCommand
        );
    }


    public void addCommand(ClientRequest command) {
        commandQueue.add(command);
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public InetAddress getClientInetAddress() {
        return clientInetAddress;
    }


    public void setClientInetAddress(InetAddress clientInetAddress) {
        this.clientInetAddress = clientInetAddress;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {

        if (port < 0)
            throw new IllegalArgumentException(String.format(
                    "Invalid port : '%s'.",
                    port)
            );

        this.port = port;

    }

    public PriorityQueue<ClientRequest> getCommandQueue() {
        return commandQueue;
    }



}
