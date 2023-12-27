package io.github.brunoczim.secsocsim.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.github.brunoczim.secsocsim.ui.MainUi;
import io.github.brunoczim.secsocsim.ui.ConnectionOpeningHandler;

class MainGui implements MainUi {
    private GuiConfig config;
    private JFrame mainFrame;
    private JTextField addressField;

    MainGui(GuiConfig config) {
        final MainGui self = this;

        this.config = config;

        this.mainFrame = new JFrame("Secret Society Simulator");
        this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainFrame.setSize(900, 400);

        this.addressField = new JTextField();

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener((event) -> {
            try {
                self.connect();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        this.mainFrame.add(addressField, BorderLayout.CENTER);
        this.mainFrame.add(connectButton, BorderLayout.SOUTH);
    }

    private synchronized void connect() {
        try {
            ConnectionOpeningHandler handler =
                this.config.getConnectionOpeningHandler();
            if (handler != null) {
                SocietyGui societyGui = new SocietyGui(this.config);
                handler.openConnection(this.addressField.getText(), societyGui);
                societyGui.show();
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

    public synchronized void start() {
        this.mainFrame.setVisible(true);
    }
}
