package ru.itmo.core.connection;


import ru.itmo.core.common.exchange.request.ClientRequest;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.core.common.exchange.response.ServerResponse;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


// Client
public class ConnectionManager {

    private final DatagramChannel clientChannel;
    private final InetSocketAddress serverSocketAddress;

    private ByteBuffer buffer;
    private int bufferSize;


    public ConnectionManager(String serverAddress, int serverPort, int clientPort) {

        // bind client to any free port

        try {

            clientChannel = DatagramChannel.open();

            clientChannel.socket().bind(new InetSocketAddress(clientPort));

//            System.out.println("Client address : " + clientChannel.getLocalAddress());

            clientChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: Can't connect any free port on the client-side.");
        }

        try {
            serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);
//            clientChannel.connect(serverSocketAddress);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't connect to server.\n    Reason: " + e.getMessage());
        }

        bufferSize = 65536;

        buffer = ByteBuffer.allocate(bufferSize);
    }


    public ConnectionManager(String serverAddress, int serverPort) {
        this(serverAddress, serverPort, 0);
    }


    public void send(byte[] bytes) throws IOException {
        buffer = ByteBuffer.allocate(bufferSize);

        try {
            buffer.put(bytes);
            buffer.flip();
        } catch (BufferOverflowException e) {
            throw new IllegalArgumentException("Error: ByteBuffer is too small to put bytes in it.");
        }

        try {
            clientChannel.send(buffer, serverSocketAddress);
        } catch (IOException e) {
            throw new IOException("Error: Can't send data to server.");
        }

    }


    public void sendRequest(ClientRequest request) throws IOException {
        send(Serializer.toByteArray(request));
    }


    public byte[] receive() throws IOException {
        buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress receiveStatus = clientChannel.receive(buffer);

        if (receiveStatus == null)
            throw new StreamCorruptedException("Error: Channel is empty. Nothing to read..");

        return buffer.array();
    }


    public ServerResponse receiveResponse() throws IOException, ClassNotFoundException {
        return (ServerResponse) Serializer.toObject(receive());
    }


    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

}