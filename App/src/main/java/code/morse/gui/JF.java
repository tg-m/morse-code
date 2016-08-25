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

import morse.code.Codec;
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

public class JF extends JFrame {

    private final JPanel     contentPane;
    private final JTextField inputText;
    private final JLabel     outputLabel = new JLabel("");
    private final Codec      coder       = new Codec();
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
        
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("C", Font.PLAIN, 12));
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
                
                        final JButton btnEncode = new JButton("Przet\u0142umacz");
                        panel.add(btnEncode);
                        btnEncode.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(final MouseEvent e) {
                                encodeAndPrint();
                            }
                        });
                
                JButton btnNewButton = new JButton("Odtw\u00F3rz");
                panel.add(btnNewButton);
                
                JButton btnNewButton_1 = new JButton("Stop");
                panel.add(btnNewButton_1);
                
                textField = new JTextField();
                panel.add(textField);
                textField.setEditable(false);
                textField.setColumns(10);
        
                inputText = new JTextField();
                panel.add(inputText);
                inputText.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        encodeAndPrint();
                    }
                });
                inputText.setColumns(10);
                
                JButton btnNewButton_2 = new JButton("Zapisz");
                panel.add(btnNewButton_2);
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
