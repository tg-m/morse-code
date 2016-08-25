package code.morse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import morse.code.Coder;
import morse.code.Signal;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class JF extends JFrame {

    private final JPanel     contentPane;
    private final JTextField inputText;
    private final JLabel     outputLabel = new JLabel("");
    private final Coder      coder       = new Coder();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final JF frame = new JF();
                    frame.setVisible(true);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JF() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1021, 616);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
       
        contentPane.repaint();
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
       
        
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "T\u0142umacz", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("Calibri", Font.PLAIN, 18));
        panel.repaint();
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(7)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        			.addContainerGap())
        );
                        panel.setLayout(null);
                
                        final JButton btnEncode = new JButton("Przet\u0142umacz");
                        btnEncode.setBounds(168, 459, 103, 25);
                        panel.add(btnEncode);
                        btnEncode.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(final MouseEvent e) {
                                encodeAndPrint();
                            }
                        });
                
                JButton btnNewButton = new JButton("Odtw\u00F3rz");
                btnNewButton.setBounds(315, 459, 81, 25);
                panel.add(btnNewButton);
                
                JButton btnNewButton_1 = new JButton("Stop");
                btnNewButton_1.setBounds(450, 459, 59, 25);
                panel.add(btnNewButton_1);
                
                textField = new JTextField();
                textField.setBounds(12, 259, 554, 123);
                panel.add(textField);
                textField.setEditable(false);
                textField.setColumns(10);
        
                inputText = new JTextField();
                inputText.setBounds(22, 63, 850, 183);
                panel.add(inputText);
                inputText.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        encodeAndPrint();
                    }
                });
                inputText.setColumns(10);
                
                JButton btnNewButton_2 = new JButton("Zapisz");
                btnNewButton_2.setBounds(542, 459, 69, 25);
                panel.add(btnNewButton_2);
                outputLabel.setBounds(952, 259, 0, 0);
                panel.add(outputLabel);
        contentPane.setLayout(gl_contentPane);
       
    }

    void encodeAndPrint() {
        outputLabel.setText(JF.toString(coder.encode(inputText.getText())));
    }

    static String toString(final List<Signal> signals) {
        String result = "";

        for (final Signal s : signals) {
            if (Signal.DASH == s) {
                result += "-";
            }
            else {
                result += ".";
            }
        }

        return result;
    }
}
