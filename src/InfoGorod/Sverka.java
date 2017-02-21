package InfoGorod;

import InfoGorod.readFile.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Sverka implements Action {

    private ArrayList<String> dList;
    private File file;
    private JTabbedPane tabbPane;
    private File sverkaLog;

    private RandomAccessFile raf;

    public Sverka(ArrayList<String> dList, JTabbedPane tabbPane, File file, File sverkaLog) {
        this.dList = dList;
        this.tabbPane = tabbPane;
        this.file = file;
        this.sverkaLog = sverkaLog;
    }

    public void action() {
        try {
            raf = new RandomAccessFile(sverkaLog, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            DomenReadActionChoose dm = new DomenExcel(file);
            dm.choose();
            dm.action();

            this.tabbPane.removeAll();
            this.dList = dm.getDomenName();
            for (int t=0; t<this.dList.size(); t++){
                final ArrayList<String> vigruzka = new ArrayList<>();
                final StringBuilder sb = new StringBuilder();
                final StringBuilder erSps = new StringBuilder();
                final String thisDom;
                thisDom = this.dList.get(t);
                ArrayList<String> firstCol = new ArrayList<>();
                JPanel Glavnoe = new JPanel();
                JButton myButton = new JButton("Сравнить списки");
                final JButton vseOk = new JButton("Проверено");
                final JButton addUserInFile = new JButton("Добавить в файл");
                final JTextArea myText1 = new JTextArea(0, 25);
                final JTextArea myText2 = new JTextArea(0, 25);
                final JTextArea finS = new JTextArea("Тут будут пользователи на удаление", 15, 0);
                JPanel bottomPanel = new JPanel();
                JPanel buttonPanel = new JPanel();
                JPanel holdAll = new JPanel();
                JScrollPane scroll;
                JScrollPane scrFin;
                JPanel topPanel = new JPanel();
                JLabel sp1 = new JLabel("Cписки из базы");
                JLabel sp2 = new JLabel("Cписки лиц допущенных");
                final ArrayList<String> removeUser = new ArrayList<>();
                final ArrayList<String> extraUser = new ArrayList<>();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.add(myButton);
                buttonPanel.add(vseOk);
                buttonPanel.add(addUserInFile);

                bottomPanel.setLayout(new BorderLayout());
                bottomPanel.add(buttonPanel, BorderLayout.NORTH);
                bottomPanel.setBackground(Color.lightGray);
                bottomPanel.add(finS, BorderLayout.SOUTH);
                finS.setEditable(false);

                holdAll.setLayout(new BorderLayout());
                holdAll.add(myText1, BorderLayout.WEST);
                holdAll.add(myText2, BorderLayout.EAST);
                myText2.setLineWrap(true);
                myText1.setLineWrap(true);

                topPanel.setLayout(new GridLayout());
                topPanel.add(sp1);
                topPanel.add(sp2);
                sp1.setHorizontalAlignment(SwingConstants.CENTER);
                sp2.setHorizontalAlignment(SwingConstants.CENTER);
                sp1.setFont(new Font("Serif", Font.BOLD, 16));
                sp2.setFont(new Font("Serif", Font.BOLD, 16));

                Glavnoe.setLayout(new BorderLayout());
                Glavnoe.add(holdAll, BorderLayout.CENTER);
                Glavnoe.add(bottomPanel, BorderLayout.SOUTH);
                Glavnoe.add(topPanel, BorderLayout.NORTH);

                ReadFileChoose readFile = new ReadExcel(firstCol, thisDom, file);
                readFile.choose();
                readFile.read();

                StringBuilder pol = new StringBuilder();
                for (String s : firstCol) {
                    pol.append(s);
                }
                myText1.setText(pol.toString());
                myText1.setCaretPosition(0);

                final int finalT = t;

                //Сверка пользователей.
                myButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("Сравнить списки")) {
                            Action algorithm = new Algorithm(removeUser, extraUser, sb, erSps, myText1, myText2, finS, tabbPane, finalT);
                            algorithm.action();
                        }
                    }
                });

                //Проверено.
                vseOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tabbPane.setForegroundAt(finalT, Color.RED);
                    }
                });

                //Добавление пользователей в файл.
                addUserInFile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        vigruzka.clear();
                        if (sb.length() != 0) {
                            if (extraUser.size() == 0) {
                                vigruzka.add("Домен " + thisDom + "\r\n");
                                vigruzka.add(sb.toString() + "\n");
                            } else {
                                vigruzka.add("Домен " + thisDom + "\r\n");
                                vigruzka.add(sb.toString() + "\n");
                                vigruzka.add("Есть в списках, но нет в базе:" + "\r\n");
                                vigruzka.add(erSps.toString());
                            }
                            for (String s : vigruzka) {
                                try {
                                    raf.write(s.getBytes());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                });

                scroll = new JScrollPane(holdAll);
                scroll.setBackground(Color.white);
                scroll.setPreferredSize(new Dimension(300, 300));
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                Glavnoe.add(scroll, BorderLayout.CENTER);

                scrFin = new JScrollPane(finS);
                scrFin.setBackground(Color.white);
                scrFin.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
                bottomPanel.add(scrFin);

                tabbPane.addTab(dList.get(t), Glavnoe);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
