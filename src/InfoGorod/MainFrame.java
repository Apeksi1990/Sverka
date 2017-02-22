package InfoGorod;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainFrame {

    private String computerName = InetAddress.getLocalHost().getHostName();

    public MainFrame() throws UnknownHostException {
        final JFrame mainJFrame = new JFrame("Сверка 1.4");
        mainJFrame.setVisible(true);
        mainJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainJFrame.setSize(700, 750);
        mainJFrame.setLocation(600, 200);
        mainJFrame.setResizable(false);
        mainJFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.white);

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        final JTabbedPane tabbPane = new JTabbedPane();

        final JButton fileS = new JButton("Выбрать файл");

        fileS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action fileChoose = new FileChoose(computerName, tabbPane, mainJFrame);
                fileChoose.action();
            }
        });

        mainJFrame.add(topPanel, BorderLayout.NORTH);
        mainJFrame.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(fileS, BorderLayout.CENTER);
        centerPanel.add(tabbPane, BorderLayout.CENTER);
    }
}
