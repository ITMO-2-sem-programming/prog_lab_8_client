package ru.itmo.UI;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.itmo.UI.controller.CollectionVisualizationController;
import ru.itmo.core.common.exchange.request.ClientRequest;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadOwnedElementsServiceRequest;
import ru.itmo.core.common.exchange.response.ServerResponse;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.AddElementResponse;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.MultidirectionalResponse;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.RemoveElementsResponse;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.UpdateElementResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.UnidirectionalResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.ServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.*;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponse;
import ru.itmo.core.connection.ConnectionManager;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.concurrent.ArrayBlockingQueue;



public class Client {

    private Main main;

    private User user;


    private final int queueCapacity
            = 10;

//    ArrayBlockingQueue<> responsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<ClientRequest> requestsQueue = new ArrayBlockingQueue<>(queueCapacity);
    // TODO: 24.08.2020

    private final ArrayBlockingQueue<GeneralResponse> userCommandResponsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<ServiceResponse> serviceResponsesQueue = new ArrayBlockingQueue<>(queueCapacity);
    private final ArrayBlockingQueue<ServerResponse> responsesQueue = new ArrayBlockingQueue<>(queueCapacity);

    ConnectionManager receiver;


    /**
     *
     * @param serverAddress
     * @param serverPort
     * @throws IllegalArgumentException if connection to server can't ve established
     */
    public Client(Main main, String serverAddress, int serverPort) {

        this.main = main;


        receiver = new ConnectionManager(serverAddress, serverPort);
//        System.out.println("receiver port : " + receiver.getClientPort());


    }


    public void start() {


        Runnable responseHandler = () -> {

            System.out.println("responseHandler is started...");
            while (true) {
                try {
                    ServerResponse response = responsesQueue.take();
                    System.out.println("I've got response :" + response);

                    if (response instanceof MultidirectionalResponse) {

                        if (response instanceof AddElementResponse) {
                            main.getCollection().add(((AddElementResponse) response).getElement());
                        } else if (response instanceof UpdateElementResponse) {
                            UpdateElementResponse updateElementResponse = (UpdateElementResponse) response;
                            main.getCollection().removeIf((element) -> element.getId().equals(updateElementResponse.getElementID()));
                            main.getCollection().add(updateElementResponse.getElement());
                            CollectionVisualizationController collectionVisualizationController = main.getCollectionVisualizationController();;
                            if (collectionVisualizationController != null) {
                                collectionVisualizationController.visualize(false); // bad fix here // TODO: 27.08.2020  
                            }
                        } else if (response instanceof RemoveElementsResponse) {
                            RemoveElementsResponse removeElementsResponse = (RemoveElementsResponse) response;
                            main.getCollection().removeIf((element) -> removeElementsResponse.getElementsID().contains(element.getId()));
                        }

                    } else if (response instanceof BackgroundServiceResponse) {
                        if (response instanceof LoadCollectionServiceResponse) {
                            LoadCollectionServiceResponse loadCollectionServiceResponse = (LoadCollectionServiceResponse) response;
                            main.setCollection(loadCollectionServiceResponse.getCollection());
                        } else if (response instanceof AddOwnedElementsIDServiceResponse) {
                            AddOwnedElementsIDServiceResponse addOwnedElementsIDServiceResponse = (AddOwnedElementsIDServiceResponse) response;
                            main.getOwnedElementsID().addAll(addOwnedElementsIDServiceResponse.getOwnedElementsID());
                        } else if (response instanceof LoadOwnedElementsIDServiceResponse) {
                            LoadOwnedElementsIDServiceResponse loadOwnedElementsIDServiceResponse = (LoadOwnedElementsIDServiceResponse) response;
                            main.setOwnedElementsID(FXCollections.observableArrayList(loadOwnedElementsIDServiceResponse.getOwnedElementsID()));
                        } else if (response instanceof RemoveOwnedElementsIDServiceResponse) {
                            RemoveOwnedElementsIDServiceResponse removeOwnedElementsIDServiceResponse = (RemoveOwnedElementsIDServiceResponse) response;
                            main.getOwnedElementsID().removeIf((element) -> removeOwnedElementsIDServiceResponse.getOwnedElementsID().contains(element));
                        }

                    } else if (response instanceof UserCommandResponse) {
                        userCommandResponsesQueue.offer((GeneralResponse) response);

                    } else if (response instanceof ServiceResponse) {
                        serviceResponsesQueue.offer((ServiceResponse) response);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };


        Runnable receiverTask = () -> {
            while (true) {


                try {
                    ServerResponse response = receiver.receiveResponse();

                    responsesQueue.offer(response);

                } catch (StreamCorruptedException ignore) {
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


                ClientRequest request = requestsQueue.poll();

                if (request != null) {
                    try {
                        receiver.sendRequest(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        new Thread(receiverTask).start();
        new Thread(responseHandler).start();

    }

    /**
     *
     * @param request
     * @return true if the specified request was successfully added to the queue. Returns false if the queue does not have free space.
     */
    public boolean addRequest(ClientRequest request) {
        System.out.println(
                "I've got new Request..." +
                "\nRequests list : " +
                "\n" + requestsQueue.toString());

        if (request.getUser() == null) {
            request.setUser(this.user);
        }

        requestsQueue.offer(request);
//        System.out.println("Requests queue : " + requestsQueue);

        return true;
    }


    public GeneralResponse getUserCommandResponse() {
        try {
            return userCommandResponsesQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public ServiceResponse getServiceResponse() {
        try {
            return serviceResponsesQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }


    public int getQueueCapacity() {
        return queueCapacity;
    }


    public ArrayBlockingQueue<ClientRequest> getRequestsQueue() {
        return requestsQueue;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    private int getClientPort() {
        return (int) (Math.random() * 1000) + 44000;
    }

}


