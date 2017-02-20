package InfoGorod;

import InfoGorod.readFile.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sverka implements Action {

    private ArrayList<String> dList;
    private ArrayList<String> arr;
    private ArrayList<String> erSp;
    private ArrayList<String> vigruzka;
    private File file;
    private JTabbedPane tabbPane;

    public Sverka(ArrayList<String> dList, ArrayList<String> vigruzka, JTabbedPane tabbPane, File file) {
        this.dList = dList;
        this.vigruzka = vigruzka;
        this.tabbPane = tabbPane;
        this.file = file;
    }

    public void action() {
        try {
            DomenReadActionChoose dm = new DomenExcel(file);
            dm.choose();
            dm.action();

            tabbPane.removeAll();
            dList = dm.getDomenName();
            for (int t=0; t<dList.size(); t++){
                final String thisDom;
                thisDom = dList.get(t);
                ArrayList<String> firstCol = new ArrayList<>();
                JPanel Glavnoe = new JPanel();
                JButton myButton = new JButton("Сравнить списки");
                JButton vseOk = new JButton("Проверено");
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
                arr = new ArrayList<>();
                erSp = new ArrayList<>();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.add(myButton);
                buttonPanel.add(vseOk);
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
                myButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("Сравнить списки")) {
                            arr.clear();
                            String[] mT1 = myText1.getText().split("\n");

                            //замена 'ё' на 'е' в первой колонке
                            for (int i=0; i<mT1.length; i++) {
                                mT1[i] = mT1[i].replaceAll("   ", " ");
                            }
                            for (int i=0; i<mT1.length; i++) {
                                mT1[i] = mT1[i].replaceAll("ё", "е");
                            }
                            for (int i=0; i<mT1.length; i++) {
                                mT1[i] = mT1[i].replaceAll("  ", " ");
                            }
                            //замена 'ё' на 'е' во второй колонке
                            String mT22 = myText2.getText();
                            mT22 = mT22.replaceAll("   ", " ");
                            String mT21 = mT22;
                            mT21 = mT21.replaceAll("  ", " ");
                            String mT2 = mT21;
                            mT2 = mT2.replaceAll("ё", "е");

                            //сверка списков
                            for (int x = 0; x < mT1.length; x++)
                                if (!(mT2.indexOf(mT1[x]) != -1)) {
                                    arr.add(mT1[x] + "\n");
                                }
                            StringBuilder sb = new StringBuilder();
                            for (String s : arr) {
                                sb.append(s);
                            }

                            //нет в базе
                            erSp.clear();
                            String[] mT3 = myText2.getText().split("\n");
                            for (int i=0; i<mT3.length; i++) {
                                mT3[i] = mT3[i].replaceAll("   ", " ");
                            }
                            for (int i=0; i<mT3.length; i++) {
                                mT3[i] = mT3[i].replaceAll("ё", "е");
                            }
                            for (int i=0; i<mT3.length; i++) {
                                mT3[i] = mT3[i].replaceAll("  ", " ");
                            }
                            for (int i=0; i<mT3.length; i++){
                                mT3[i] = mT3[i].trim();
                            }
                            String mT41 = myText1.getText();
                            mT41 = mT41.replaceAll("  ", " ");
                            String mT4 = mT41;
                            mT4 = mT4.replaceAll("ё", "е");
                            for (int x = 0; x < mT3.length; x++){
                                if (!(mT4.indexOf(mT3[x]) != -1)){
                                    erSp.add(mT3[x] + "\n");
                                }
                            }
                            StringBuilder erSps = new StringBuilder();
                            for (String s : erSp) {
                                erSps.append(s);
                            }

                            if (erSp.size() == 0) {
                                finS.setText("Требуется заблокировать пользователей:\n\n" + sb.toString());
                                vigruzka.add("Домен " + thisDom + "\r\n");
                                vigruzka.add(sb.toString() + "\n");
                            }
                            else {
                                finS.setText("Требуется заблокировать пользователей:\n\n" + sb.toString() +
                                        "\n" + "______________\n" + "Есть в списках, но нет в базе:\n\n" + erSps );
                                vigruzka.add("Домен " + thisDom + "\r\n");
                                vigruzka.add(sb.toString() + "\n");
                                vigruzka.add("Есть в списках, но нет в базе:" + "\r\n");
                                vigruzka.add(erSps.toString());
                            }
                            finS.setCaretPosition(0);
                            tabbPane.setForegroundAt(finalT, Color.LIGHT_GRAY);
                        }
                    }
                });

                vseOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tabbPane.setForegroundAt(finalT, Color.RED);
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
