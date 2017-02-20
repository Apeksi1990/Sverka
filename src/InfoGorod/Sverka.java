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

public class Sverka {

    private ArrayList<String> dList = new ArrayList<>();
    private ArrayList<String> vigruzka = new ArrayList<>();
    private String computerName = InetAddress.getLocalHost().getHostName();
    private File sverkaLog;


    public Sverka() throws UnknownHostException {
        final JFrame jfr = new JFrame("Сверка 1.4");
        jfr.setVisible(true);
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfr.setSize(700, 750);
        jfr.setLocation(600, 200);
        jfr.setResizable(false);
        jfr.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.white);

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        final JTabbedPane tabbPane = new JTabbedPane();

        final JButton fileS = new JButton("Выбрать файл");

        fileS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (.xlsx or .xls)", "xlsx", "xls");
                fileopen.setFileFilter(filter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    String fileName = file.getName();
                    if (fileName.endsWith(".xls") | fileName.endsWith(".xlsx")) {
                        String logFile;
                        if (fileName.endsWith(".xls")) {
                            logFile = fileName.substring(0, fileName.length() - 4);
                        } else {
                            logFile = fileName.substring(0, fileName.length() - 5);
                        }
                        jfr.setTitle(logFile);
                        sverkaLog = new File("SverkaLog_" + logFile + "_" + computerName + ".doc");
                        Action action = new ActionForSverka(dList, vigruzka, tabbPane, file);
                        action.action();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Требуется выбрать .xlsx или .xls файл",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton exlBut = new JButton("Выгрузить список");
        exlBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (RandomAccessFile raf = new RandomAccessFile(sverkaLog, "rw")){
                    for (String s : vigruzka) {
                        raf.write(s.getBytes());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jfr.add(topPanel, BorderLayout.NORTH);
        jfr.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(fileS, BorderLayout.CENTER);
        topPanel.add(exlBut, BorderLayout.CENTER);
        centerPanel.add(tabbPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws UnknownHostException { new Sverka(); }
}
