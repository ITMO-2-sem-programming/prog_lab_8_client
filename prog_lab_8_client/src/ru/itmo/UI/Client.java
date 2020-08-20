package ru.itmo.UI;


import ru.itmo.core.common.exchange.request.Request;

import java.util.concurrent.ArrayBlockingQueue;



public class Client {



    private final int queueCapacity
            = 10;

//    ArrayBlockingQueue<> responsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<Request> requestsQueue = new ArrayBlockingQueue<>(queueCapacity);


    
    public Client() {
        // TODO: 20.08.2020  
    }


    /**
     *
     * @param request
     * @return true if the specified request was successfully added to the queue. Returns false if the queue does not have free space.
     */
    public boolean addRequest(Request request) {
        return requestsQueue.offer(request);
    }


    public int getQueueCapacity() {
        return queueCapacity;
    }


    public ArrayBlockingQueue<Request> getRequestsQueue() {
        return requestsQueue;
    }



}


