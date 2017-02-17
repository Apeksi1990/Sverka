package Test;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class SverkaTest {

    ArrayList<String> dList = new ArrayList<>();
    ArrayList<String> arr;
    ArrayList<String> erSp;
    ArrayList<String> vigruzka = new ArrayList<>();
    String path = "images/iconG.png";
    URL imgURL = SverkaTest.class.getResource(path);
    ImageIcon tabIcon = new ImageIcon(imgURL);
    File sverkaLog = new File("SverkaLog.txt");


    public SverkaTest(){
        JFrame jfr = new JFrame("Сверка 1.3 Test");
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
        JButton exlBut = new JButton("Выгрузить список");

        topPanel.add(fileS, BorderLayout.CENTER);
        topPanel.add(exlBut, BorderLayout.CENTER);
        centerPanel.add(tabbPane, BorderLayout.CENTER);
        jfr.add(topPanel, BorderLayout.NORTH);
        jfr.add(centerPanel, BorderLayout.CENTER);

        fileS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*JOptionPane.showMessageDialog(null,
                        "Требуется выбрать файл формата xls.\nТребуется пересохранить исходный файл из формата xlsx в формат xls",
                        "Костыль =)",JOptionPane.INFORMATION_MESSAGE);*/

                JFileChooser fileopen = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (.xls)", "xls");
                fileopen.setFileFilter(filter);
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    if (!file.getName().endsWith("xls")) {
                        JOptionPane.showMessageDialog(null,
                                "Требуется выбрать .xls файл",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else
                        try {
                        DomenTest dm = new DomenTest(file);

                            tabbPane.removeAll();
                        System.out.println(dm.getFileName());
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
                                final JTextArea finS = new JTextArea("Тут будут пользователи на удаление"/*, 15, 0*/);
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
                                myText2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                                myText1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                                sp1.setPreferredSize(new Dimension(277,25));
                                sp2.setPreferredSize(new Dimension(277,25));
                                topPanel.setLayout(new BorderLayout());
                                topPanel.add(sp1, BorderLayout.WEST);
                                topPanel.add(sp2, BorderLayout.EAST);
                                sp1.setHorizontalAlignment(SwingConstants.CENTER);
                                sp2.setHorizontalAlignment(SwingConstants.CENTER);
                                sp1.setFont(new Font("Serif", Font.BOLD, 16));
                                sp2.setFont(new Font("Serif", Font.BOLD, 16));


                                JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
                                splitVertical.setTopComponent(holdAll);
                                splitVertical.setBottomComponent(bottomPanel);
                                splitVertical.setDividerLocation(350);

                                Glavnoe.setLayout(new BorderLayout());
                                Glavnoe.add(topPanel, BorderLayout.NORTH);
                                Glavnoe.add(splitVertical, BorderLayout.CENTER);

                                InputStream ExcelFileToRead = new FileInputStream(file);
                                HSSFWorkbook myExcelBook = new HSSFWorkbook(ExcelFileToRead);
                                HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
                                for (int w=1; w<myExcelSheet.getLastRowNum()+1; w++){
                                    if (myExcelSheet.getRow(w).getCell(4) == null) {
                                        String fC = String.valueOf(myExcelSheet.getRow(w).getCell(2));
                                        if (Objects.equals(fC, thisDom)) {
                                            firstCol.add(String.valueOf(myExcelSheet.getRow(w).getCell(0)) + "\n");
                                        }
                                    }
                                    else {
                                        String fC = String.valueOf(myExcelSheet.getRow(w).getCell(4));
                                        if (Objects.equals(fC, thisDom)) {
                                            firstCol.add(String.valueOf(myExcelSheet.getRow(w).getCell(0)) + "\n");
                                        }
                                    }
                                }
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
                                            tabbPane.setIconAt(finalT, tabIcon);
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
                                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                                splitVertical.add(scroll);

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
        });

        exlBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder vig = new StringBuilder();
                /*for (String s : vigruzka) {
                    vig.append(s);
                }
                System.out.println(vig.toString());*/
                try (RandomAccessFile raf = new RandomAccessFile(sverkaLog, "rw")){
                    for (String s : vigruzka) {
                        raf.write(s.getBytes());
                        //raf.writeUTF("\r\n");
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) { new SverkaTest(); }
}
