package InfoGorod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Algorithm implements Action {

    private ArrayList<String> removeUser;
    private ArrayList<String> extraUser;
    private StringBuilder sb;
    private StringBuilder erSps;
    private JTextArea myText1;
    private JTextArea myText2;
    private JTextArea finS;
    private JTabbedPane tabbPane;
    private int finalT;

    public Algorithm(ArrayList<String> removeUser, ArrayList<String> extraUser, StringBuilder sb, StringBuilder erSps, JTextArea myText1, JTextArea myText2, JTextArea finS, JTabbedPane tabbPane, int finalT) {
        this.removeUser = removeUser;
        this.extraUser = extraUser;
        this.sb = sb;
        this.erSps = erSps;
        this.myText1 = myText1;
        this.myText2 = myText2;
        this.finS = finS;
        this.tabbPane = tabbPane;
        this.finalT = finalT;
    }

    public void action() {
        removeUser.clear();
        String[] correctionTextFirstColumn = myText1.getText().split("\n");

        //замена 'ё' на 'е' в первой колонке
        for (int i=0; i<correctionTextFirstColumn.length; i++) {
            correctionTextFirstColumn[i] = correctionTextFirstColumn[i].replaceAll("   ", " ");
        }
        for (int i=0; i<correctionTextFirstColumn.length; i++) {
            correctionTextFirstColumn[i] = correctionTextFirstColumn[i].replaceAll("ё", "е");
        }
        for (int i=0; i<correctionTextFirstColumn.length; i++) {
            correctionTextFirstColumn[i] = correctionTextFirstColumn[i].replaceAll("  ", " ");
        }
        //замена 'ё' на 'е' во второй колонке
        String correctionTextSecondColumn = myText2.getText();
        correctionTextSecondColumn = correctionTextSecondColumn.replaceAll("   ", " ");
        correctionTextSecondColumn = correctionTextSecondColumn.replaceAll("  ", " ");
        correctionTextSecondColumn = correctionTextSecondColumn.replaceAll("ё", "е");


        //сверка списков
        for (int x = 0; x < correctionTextFirstColumn.length; x++)
            if (!(correctionTextSecondColumn.indexOf(correctionTextFirstColumn[x]) != -1)) {
                removeUser.add(correctionTextFirstColumn[x] + "\n");
            }
        sb.setLength(0);
        for (String s : removeUser) {
            sb.append(s);
        }

        //нет в базе
        extraUser.clear();
        String[] searchExtraSecondColumn = myText2.getText().split("\n");
        for (int i=0; i<searchExtraSecondColumn.length; i++) {
            searchExtraSecondColumn[i] = searchExtraSecondColumn[i].replaceAll("   ", " ");
        }
        for (int i=0; i<searchExtraSecondColumn.length; i++) {
            searchExtraSecondColumn[i] = searchExtraSecondColumn[i].replaceAll("ё", "е");
        }
        for (int i=0; i<searchExtraSecondColumn.length; i++) {
            searchExtraSecondColumn[i] = searchExtraSecondColumn[i].replaceAll("  ", " ");
        }
        for (int i=0; i<searchExtraSecondColumn.length; i++){
            searchExtraSecondColumn[i] = searchExtraSecondColumn[i].trim();
        }
        String searchExtraFirstColumn = myText1.getText();
        searchExtraFirstColumn = searchExtraFirstColumn.replaceAll("  ", " ");
        searchExtraFirstColumn = searchExtraFirstColumn.replaceAll("ё", "е");

        //сверка списков
        for (int x = 0; x < searchExtraSecondColumn.length; x++){
            if (!(searchExtraFirstColumn.indexOf(searchExtraSecondColumn[x]) != -1)){
                extraUser.add(searchExtraSecondColumn[x] + "\n");
            }
        }

        erSps.setLength(0);
        for (String s : extraUser) {
            erSps.append(s);
        }

        if (extraUser.size() == 0) {
            finS.setText("Требуется заблокировать пользователей:\n\n" + sb.toString());
        }
        else {
            finS.setText("Требуется заблокировать пользователей:\n\n" + sb.toString() +
                    "\n" + "______________\n" + "Есть в списках, но нет в базе:\n\n" + erSps.toString() );
        }
        finS.setCaretPosition(0);
        tabbPane.setForegroundAt(finalT, Color.LIGHT_GRAY);
    }
}
