package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.github.brunoczim.secsocsim.p2p.IncomingMessageHandler;
import io.github.brunoczim.secsocsim.p2p.Peer;

public class TcpPeer implements Peer {
    private ServerSocket server;
    private CompletableFuture<Void> serverTask;
    private Collection<Socket> connections;
    private IncomingMessageHandler incomingMessageHandler;
    private Map<Socket, CompletableFuture<Void>> receiveTasks;

    TcpPeer(InetAddress parentAddress, int parentPort, int localPort) throws IOException {
        this.server = new ServerSocket(localPort);
        this.connections = new HashSet<>();
        this.receiveTasks = new HashMap<>();
        this.incomingMessageHandler = null;

        this.serverTask = CompletableFuture.runAsync(() -> {
            try {
                for (;;) {
                    Socket connection = this.server.accept();
                    this.addConnection(connection);
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            } finally {
                try {
                    this.server.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        Socket parent = new Socket();
        parent.connect(new InetSocketAddress(parentAddress, parentPort));
        this.addConnection(parent);
    }

    private void addConnection(final Socket connection) {
        this.connections.add(connection);
        this.receiveTasks.put(connection, CompletableFuture.runAsync(() -> {
            try {
                InputStream inputStream = connection.getInputStream();
                int current = inputStream.read();
                while (current >= 0) {
                    switch (current) {
                        case 0:
                            current = -1;
                            break;
                        case 1:
                            this.receive(this.decodeString(inputStream), connection);
                            break;
                    }
                    if (current >= 0) {
                        current = inputStream.read();
                    }
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }));
    }

    private int decodeInt(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4];
        inputStream.read(buffer);
        return ((int) buffer[0]) | (((int) buffer[1]) << 8)
                | (((int) buffer[2]) << 16) | (((int) buffer[3]) << 24);
    }

    private String decodeString(InputStream inputStream) throws IOException {
        int length = this.decodeInt(inputStream);
        byte[] buffer = new byte[length];
        return new String(buffer, Charset.forName("UTF-8"));
    }

    private void sendToNode(Socket connection, String message) {
        try {
            connection.getOutputStream().write(message.getBytes());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    protected void receive(String message, Socket sender) {
        int size = this.connections.size();
        CompletableFuture<Void>[] futures = (CompletableFuture<Void>[]) new CompletableFuture[size];
        futures[0] = CompletableFuture.runAsync(() -> this.incomingMessageHandler.receive(message));
        int i = 1;
        for (Socket connection : this.connections) {
            if (connection != sender) {
                futures[i] = CompletableFuture.runAsync(() -> this.sendToNode(connection, message));
                i++;
            }
        }
        CompletableFuture.allOf(futures);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void send(String message) {
        int size = this.connections.size();
        CompletableFuture<Void>[] futures = (CompletableFuture<Void>[]) new CompletableFuture[size];
        int i = 0;
        for (Socket connection : this.connections) {
            futures[i] = CompletableFuture.runAsync(() -> this.sendToNode(connection, message));
            i++;
        }
        CompletableFuture.allOf(futures);
    }

    @Override
    public void setIncomingMessageHandler(IncomingMessageHandler handler) {
        this.incomingMessageHandler = handler;
    }

    @Override
    public void close() throws IOException {
        IOException exception = null;
        try {
            this.server.close();
        } catch (IOException exc) {
            exception = exc;
        }
        this.serverTask.cancel(false);
        for (Socket connection : this.connections) {
            try {
                connection.close();
            } catch (IOException exc) {
                if (exception != null) {
                    exception = exc;
                }
            }
        }
        for (CompletableFuture<Void> task : this.receiveTasks.values()) {
            task.cancel(false);
        }
        if (exception != null) {
            throw exception;
        }
    }
}
