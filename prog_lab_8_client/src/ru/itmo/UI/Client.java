package ru.itmo.UI;



import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceCommandResponse.ServiceCommandResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userCommandResponse.UserCommandResponse;

import java.util.concurrent.ArrayBlockingQueue;



public class Client {



    private User user;


    private final int queueCapacity
            = 10;

//    ArrayBlockingQueue<> responsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<Request> requestsQueue = new ArrayBlockingQueue<>(queueCapacity);
    // TODO: 24.08.2020
    private final ArrayBlockingQueue<UserCommandResponse> userCommandResponsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<ServiceCommandResponse> serviceCommandResponsesQueue = new ArrayBlockingQueue<>(queueCapacity);

    
    public Client() {
        // TODO: 20.08.2020  
    }


    /**
     *
     * @param request
     * @return true if the specified request was successfully added to the queue. Returns false if the queue does not have free space.
     */
    public boolean addRequest(Request request) {
        System.out.println(
                "I've got new Request..." +
                "\nRequests list : " +
                "\n" + requestsQueue.toString());

        request.setUser(this.user);
        return requestsQueue.offer(request);
    }


    public int getQueueCapacity() {
        return queueCapacity;
    }


    public ArrayBlockingQueue<Request> getRequestsQueue() {
        return requestsQueue;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }



}


