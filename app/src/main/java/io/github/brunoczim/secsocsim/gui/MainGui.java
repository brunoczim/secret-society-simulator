package io.github.brunoczim.secsocsim.gui;

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.github.brunoczim.secsocsim.ui.ConnectionRequestHandler;
import io.github.brunoczim.secsocsim.ui.MainUi;

public class MainGui implements MainUi {
    private GuiConfig config;
    private JFrame mainFrame;
    private JTextField addressField;

    MainGui(GuiConfig config) {
        MainGui self = this;
        this.config = config;

        this.mainFrame = new JFrame("Secret Society Simulator");
        this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainFrame.setSize(900, 400);

        this.addressField = new JTextField();

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    self.connect();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });

        this.mainFrame.add(addressField, BorderLayout.NORTH);
        this.mainFrame.add(connectButton, BorderLayout.CENTER);
    }

    private void connect() throws Exception {
        try {
            ConnectionRequestHandler handler = this.config.getConnectionRequestHandler();
            if (handler != null) {
                RoomGui roomGui = new RoomGui(this.config);
                handler.requestConnection(this.addressField.getText(), roomGui);
                roomGui.show();
            }
        } catch (Exception exc) {
            String message = exc.getMessage();
            if (message == null) {
                message = exc.toString();
            }
            JDialog dialog = new JDialog(this.mainFrame);
            JTextArea errorArea = new JTextArea(message);
            errorArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(errorArea);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.add(scrollPane);
            dialog.setSize(400, 300);
            dialog.setResizable(true);
            dialog.setVisible(true);
        }
    }

    public void start() {
        this.mainFrame.setVisible(true);
    }
}
