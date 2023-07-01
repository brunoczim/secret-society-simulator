package io.github.brunoczim.secsocsim.tcp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.github.brunoczim.secsocsim.p2p.Firewall;
import io.github.brunoczim.secsocsim.p2p.IncomingMessageHandler;
import io.github.brunoczim.secsocsim.p2p.Peer;

public class TcpPeer implements Peer {
    private ServerSocket server;
    private CompletableFuture<Void> serverTask;
    private Collection<Socket> connections;
    private IncomingMessageHandler incomingMessageHandler;
    private Map<Socket, CompletableFuture<Void>> receiveTasks;

    private static final TcpCommandTable commandTable = new TcpCommandTable();

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
                InputStream stream = connection.getInputStream();
                TcpReader reader = new TcpReader(stream);
                for (;;) {
                    TcpPeer.commandTable.parseNext(reader).execute(this, connection);
                }
            } catch (EOFException exc) {

            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }));
    }

    private void sendMessageOnce(Socket connection, String message) {
        try {
            TcpCommand command = new TcpMessageCommand(message);
            command.emit(new TcpWriter(connection.getOutputStream()));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    protected void receiveMessage(String message, Socket sender) {
        int size = this.connections.size();
        CompletableFuture<Void>[] futures = (CompletableFuture<Void>[]) new CompletableFuture[size];
        futures[0] = CompletableFuture.runAsync(() -> this.incomingMessageHandler.receive(message));
        int i = 1;
        for (Socket connection : this.connections) {
            if (connection != sender) {
                futures[i] = CompletableFuture
                        .runAsync(() -> this.sendMessageOnce(connection, message));
                i++;
            }
        }
        CompletableFuture.allOf(futures);
    }

    @Override
    public void setFirewall(Firewall firewall) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sendMessage(String message) {
        int size = this.connections.size();
        CompletableFuture<Void>[] futures = (CompletableFuture<Void>[]) new CompletableFuture[size];
        int i = 0;
        for (Socket connection : this.connections) {
            futures[i] = CompletableFuture
                    .runAsync(() -> this.sendMessageOnce(connection, message));
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
