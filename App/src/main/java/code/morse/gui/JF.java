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

public class JF extends JFrame {

    private final JPanel     contentPane;
    private final JTextField inputText;
    private final JLabel     outputLabel = new JLabel("");
    private final Coder      coder       = new Coder();

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
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        contentPane.add(outputLabel, BorderLayout.NORTH);

        inputText = new JTextField();
        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                encodeAndPrint();
            }
        });
        contentPane.add(inputText, BorderLayout.CENTER);
        inputText.setColumns(10);

        final JButton btnEncode = new JButton("Encode");
        btnEncode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                encodeAndPrint();
            }
        });
        contentPane.add(btnEncode, BorderLayout.SOUTH);
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
            else if (Signal.DOT == s) {
                result += ".";
            }
            else if (Signal.SIGN_STOP == s) {
                result += " ";
            }
            else {
                result += "/";
            }

        }

        return result;
    }

}
