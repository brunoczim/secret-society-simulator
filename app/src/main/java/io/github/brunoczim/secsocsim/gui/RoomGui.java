package io.github.brunoczim.secsocsim.gui;

import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.github.brunoczim.secsocsim.ui.MessageSendHandler;
import io.github.brunoczim.secsocsim.ui.RoomUi;

class RoomGui implements RoomUi {
    private GuiConfig config;
    private JFrame roomFrame;
    private JTextArea messageInput;

    RoomGui(GuiConfig config) {
        RoomGui self = this;
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

        this.roomFrame = new JFrame("Secret Society Simulator");
        this.roomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.roomFrame.add(logScrollPane, BorderLayout.NORTH);
        this.roomFrame.add(messageInputPane, BorderLayout.CENTER);
        this.roomFrame.add(sendButton, BorderLayout.SOUTH);
    }

    void show() {
        this.roomFrame.setVisible(true);
    }

    private void sendMessage() throws Exception {
        String message = this.messageInput.getText();
        MessageSendHandler handler = this.config.getMessageSendHandler();
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    public void logIncomingMessage(String text) {
    }

    public void logSystemMessage(String text) {
    }

    public void close() {
        this.roomFrame.dispatchEvent(new WindowEvent(this.roomFrame, WindowEvent.WINDOW_CLOSING));
    }
}
