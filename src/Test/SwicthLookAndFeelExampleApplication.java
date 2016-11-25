package Test;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SwicthLookAndFeelExampleApplication extends JFrame {

    protected class LafItem {
        private UIManager.LookAndFeelInfo info;

        public LafItem(UIManager.LookAndFeelInfo info){
            this.info = info;
        }

        @Override public String toString(){
            return info.getName();
        }

        public UIManager.LookAndFeelInfo getLafInfo(){
            return this.info;
        }

    }

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JComboBox jComboBox = null;
    private JCheckBox jCheckBox = null;
    private JRadioButton jRadioButton = null;
    private JButton jButton = null;

    private JComboBox getJComboBox() {
        if (jComboBox == null) {
            jComboBox = new JComboBox();
            jComboBox.setBounds(new Rectangle(20, 21, 256, 25));
            jComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            try {
                                UIManager.setLookAndFeel(((LafItem)jComboBox.getSelectedItem()).getLafInfo().getClassName());
                                SwingUtilities.updateComponentTreeUI(SwicthLookAndFeelExampleApplication.this);
                            } catch ( Exception ex ) {
                                JOptionPane.showMessageDialog(SwicthLookAndFeelExampleApplication.this, ex.toString());
                            }
                        }
                    });
                }
            });

            // Searching for installed Look&Feel
            UIManager.LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
            for (int i = 0, n = laf.length; i < n; i++)
                jComboBox.addItem(new LafItem(laf[i]));
        }
        return jComboBox;
    }

    private JCheckBox getJCheckBox() {
        if (jCheckBox == null) {
            jCheckBox = new JCheckBox();
            jCheckBox.setBounds(new Rectangle(64, 54, 171, 21));
            jCheckBox.setText("Test check box");
        }
        return jCheckBox;
    }

    private JRadioButton getJRadioButton() {
        if (jRadioButton == null) {
            jRadioButton = new JRadioButton();
            jRadioButton.setBounds(new Rectangle(64, 79, 167, 21));
            jRadioButton.setText("Test radio button");
        }
        return jRadioButton;
    }

    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(new Rectangle(90, 118, 105, 24));
            jButton.setText("Exit");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            SwicthLookAndFeelExampleApplication.this.dispose();
                        }
                    });
                }
            });
        }
        return jButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwicthLookAndFeelExampleApplication thisClass = new SwicthLookAndFeelExampleApplication();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public SwicthLookAndFeelExampleApplication() {
        super();
        initialize();
    }

    private void initialize() {
        this.setSize(300, 200);
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setTitle("Switch Look&Feel");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJComboBox(), null);
            jContentPane.add(getJCheckBox(), null);
            jContentPane.add(getJRadioButton(), null);
            jContentPane.add(getJButton(), null);
        }
        return jContentPane;
    }

}