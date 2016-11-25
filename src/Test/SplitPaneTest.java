package Test;

import java.awt.Color;

// Swing пример использование разделяемой панели JSplitPane

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class SplitPaneTest extends JFrame
{
    private JLabel lblMain;
    private int    dividerMain = 200;
    private final  String TEMPL_lbl = "dividerLocation = %d";
    public SplitPaneTest()
    {
        super("Пример разделяеомй панели JSplitPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Главная разделяемая панель
        final JSplitPane splitHorizontal = new JSplitPane();
        splitHorizontal.setOneTouchExpandable(true);
        // Размер разделяемой панели
        splitHorizontal.setDividerSize(8);
        // Положение разделяемой панели
        splitHorizontal.setDividerLocation(dividerMain);
        // Вертикальная разделяемая панель
        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        // Создание панелей
        splitVertical.setTopComponent   (new JTextArea());
        splitVertical.setBottomComponent(new JScrollPane());
        // Положение разделяемой панели
        splitVertical.setDividerLocation(100);
        // Текстовая метка для главной панели
        lblMain = new  JLabel(String.format(TEMPL_lbl, dividerMain));
        // Главная панель
        JPanel pnlMain = new JPanel();
        pnlMain.add(lblMain);
        pnlMain.setBackground(Color.cyan);
        // Настройка главной панели
        splitHorizontal.setLeftComponent(new JScrollPane(pnlMain));
        splitHorizontal.setRightComponent(splitVertical);
        // Слушатель изменения свойств разделяеомй панели
        splitHorizontal.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent arg0)
            {
                dividerMain = splitHorizontal.getDividerLocation();
                lblMain.setText(String.format(TEMPL_lbl, dividerMain));
            }
        });
        // Размещение панели в интерфейсе и вывод окна на экран
        getContentPane().add(splitHorizontal);
        setSize(600, 400);
        setVisible(true);
    }
    public static void main(String[] args) {
        new SplitPaneTest();
    }
}