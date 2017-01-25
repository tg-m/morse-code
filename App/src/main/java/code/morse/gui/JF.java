package code.morse.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import morse.code.Codec;
import morse.code.Signal;
import morse.code.audio.AudioMorseCoder;
import morse.code.audio.MorseCodePlayer;
import morse.code.io.WaveOut;

public class JF extends JFrame {

    private final JPanel          contentPane;
    private final Codec           coder             = new Codec();
    private final JTextArea       outputText        = new JTextArea();
    private final JTextArea       inputText         = new JTextArea();

    private Task                  task;
    Thread                        playerThread;

    private final int             samplingFrequency = 44100;
    private final int             sampleSizeInBytes = 2;
    private final int             channels          = 2;
    private final AudioMorseCoder audioMorseCoder   = new AudioMorseCoder(40, 440.0, samplingFrequency, channels, sampleSizeInBytes, 0.25);
    private final MorseCodePlayer player            = new MorseCodePlayer(audioMorseCoder);
    private final WaveOut         waveOut           = new WaveOut(samplingFrequency, sampleSizeInBytes, channels);

    private List<Signal>          message;

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

    class Task extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            String textToEncode;
            if (isMorse(inputText.getText())) {
                textToEncode = inputText.getText();
            }
            else {
                textToEncode = outputText.getText();
            }

            final List<Signal> message = coder.toSignal(textToEncode);
            try {
                player.play(message);
            }
            catch (final InterruptedException e1) {
                e1.printStackTrace();
            }
            catch (final LineUnavailableException e1) {
                e1.printStackTrace();
            }
            return null;
        }

    }

    /**
     * Create the frame.
     */
    public JF() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1021, 616);
        setMinimumSize(new Dimension(1051, 616));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPane.repaint();
        setContentPane(contentPane);

        final JPanel panel = new JPanel();

        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "T\u0142umacz", TitledBorder.LEADING, TitledBorder.TOP,
                null, new Color(0, 0, 0)));

        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("C", Font.PLAIN, 14));
        panel.repaint();
        final GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                                .addContainerGap()));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(7)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                                .addContainerGap()));

        final JButton btnEncode = new JButton("Przet\u0142umacz");
        btnEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                encodeAndPrint();
            }
        });
        // btnEncode.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(final MouseEvent e) {
        // encodeAndPrint();
        // }
        // });

        final JButton btnPlay = new JButton("Odtw\u00F3rz");
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                encodeAndPrint();
                task = new Task();
                // task.addPropertyChangeListener(this);
                task.execute();
            }
        });

        final JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                player.stop();
            }
        });

        // inputText.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(final ActionEvent e) {
        // encodeAndPrint();
        // }
        // });
        // inputText.setColumns(10);

        final JButton btnSave = new JButton("Zapisz");
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                encodeAndPrint();
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("file.wav"));
                if (fileChooser.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    final File file = fileChooser.getSelectedFile();
                    final String test = file.getAbsolutePath();
                    waveOut.save(audioMorseCoder.getWave(message).array(), file.getAbsolutePath());
                }

            }
        });
        final Font textFieldFont = new Font("Courier New", Font.PLAIN, 20);

        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        final JScrollPane scrollPane_1 = new JScrollPane();
        final GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(50)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                                .addGap(50)
                                                .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                                .addGap(50)
                                                .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                                .addGap(50)
                                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(scrollPane, Alignment.TRAILING)
                                        .addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE))
                                .addGap(61)));
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(35)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addGap(35)
                                .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addGap(35)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)

                                        .addGroup(gl_panel.createSequentialGroup()

                                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 70,
                                                                GroupLayout.PREFERRED_SIZE))))
                                .addGap(35)));
        final Font buttonFont = new Font("C", Font.PLAIN, 16);
        btnEncode.setFont(buttonFont);
        btnPlay.setFont(buttonFont);
        btnSave.setFont(buttonFont);
        btnStop.setFont(buttonFont);
        outputText.setEditable(false);

        scrollPane_1.setViewportView(outputText);
        outputText.setLineWrap(true);
        outputText.setFont(textFieldFont);
        scrollPane.setViewportView(inputText);
        inputText.setLineWrap(true);
        inputText.setFont(textFieldFont);
        panel.setLayout(gl_panel);
        contentPane.setLayout(gl_contentPane);

    }

    void encodeAndPrint() {
        if (!isMorse(inputText.getText())) {
            message = coder.encode(inputText.getText());

            outputText.setText(JF.toString(message));
        }
        else {
            final String result = coder.decode(coder.toSignal(inputText.getText()));

            message = coder.toSignal(inputText.getText());

            outputText.setText(result);
        }

        contentPane.repaint();
    }

    private boolean isMorse(final String text) {
        final List<Character> possibleChars = new LinkedList<Character>();
        possibleChars.add('-');
        possibleChars.add('.');
        possibleChars.add(' ');
        possibleChars.add('/');

        final char[] textAsArray = text.toCharArray();

        for (final char c : textAsArray) {
            if (!possibleChars.contains(c)) {
                return false;
            }
        }
        return true;
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
                result += "   ";
            }
            else {
                result += "   /   ";
            }
        }
        return result;
    }
}
