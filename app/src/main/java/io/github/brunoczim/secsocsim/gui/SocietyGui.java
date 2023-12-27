package io.github.brunoczim.secsocsim.gui;

import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.github.brunoczim.secsocsim.ui.MessageSendingHandler;
import io.github.brunoczim.secsocsim.ui.SocietyUi;

class SocietyGui implements SocietyUi {
    private GuiConfig config;
    private JFrame societyFrame;
    private JTextArea messageInput;

    SocietyGui(GuiConfig config) {
        SocietyGui self = this;
        this.config = config;

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

        JScrollPane logScrollPane = new JScrollPane(logPanel);

        this.messageInput = new JTextArea();
        JScrollPane messageInputPane = new JScrollPane(this.messageInput);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener((event) -> {
            try {
                self.sendMessage();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        this.societyFrame = new JFrame("Secret Society Simulator");
        this.societyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.societyFrame.add(logScrollPane, BorderLayout.NORTH);
        this.societyFrame.add(messageInputPane, BorderLayout.CENTER);
        this.societyFrame.add(sendButton, BorderLayout.SOUTH);
    }

    synchronized void show() {
        this.societyFrame.setVisible(true);
    }

    private synchronized void sendMessage() throws Exception {
        String message = this.messageInput.getText();
        MessageSendingHandler handler = this.config.getMessageSendingHandler();
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    public synchronized void logIncomingMessage(String text) {
        this.messageInput.append("[REMOTE]: ");
        this.messageInput.append(text);
        this.messageInput.append("\n");
    }

    public synchronized void logSystemMessage(String text) {
        this.messageInput.append("[SYSTEM]: ");
        this.messageInput.append(text);
        this.messageInput.append("\n");
    }

    public synchronized void close() {
        this.societyFrame.dispatchEvent(
            new WindowEvent(this.societyFrame, WindowEvent.WINDOW_CLOSING)
        );
    }
}
