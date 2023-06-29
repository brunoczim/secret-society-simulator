package io.github.brunoczim.secsocsim;

import io.github.brunoczim.secsocsim.gui.GuiConfig;
import io.github.brunoczim.secsocsim.tcp.TcpPeerFactory;

/*
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
*/

public class Main {
    public static void main(String[] args) {
        new App(new GuiConfig(), new TcpPeerFactory()).run();

        /*
         * JPanel logPanel = new JPanel();
         * logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
         * 
         * JScrollPane logScrollPane = new JScrollPane(logPanel);
         * 
         * JTextArea messageInput = new JTextArea();
         * JScrollPane messageInputPane = new JScrollPane(messageInput);
         * 
         * JButton sendButton = new JButton("Send");
         * 
         * JFrame mainFrame = new JFrame("Secret Society Simulator");
         * mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         * mainFrame.add(logScrollPane, BorderLayout.NORTH);
         * mainFrame.add(messageInputPane, BorderLayout.CENTER);
         * mainFrame.add(sendButton, BorderLayout.SOUTH);
         * mainFrame.setVisible(true);
         */
    }
}
