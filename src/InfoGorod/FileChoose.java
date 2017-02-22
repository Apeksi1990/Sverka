package InfoGorod;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class FileChoose implements Action {
    private String computerName;
    private JTabbedPane tabbPane;
    private JFrame mainJFrame;

    public FileChoose(String computerName, JTabbedPane tabbPane, JFrame mainJFrame) {
        this.computerName = computerName;
        this.tabbPane = tabbPane;
        this.mainJFrame = mainJFrame;
    }

    public void action() {
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
                this.mainJFrame.setTitle(logFile);
                File sverkaLog = new File("SverkaLog_" + logFile + "_" + this.computerName + ".doc");
                Action action = new Sverka(this.tabbPane, file, sverkaLog);
                action.action();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Требуется выбрать .xlsx или .xls файл",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
