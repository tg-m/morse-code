package code.morse.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import morse.code.Codec;
import morse.code.Signal;
import morse.code.audio.AudioMorseCoder;
import morse.code.audio.MorseCodePlayer;
import morse.code.io.WaveOut;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Font;
public class JF extends JFrame {

    private final JPanel          contentPane;
    private final JLabel          outputLabel       = new JLabel("");
    private final Codec           coder             = new Codec();
    private final JTextArea       outputText        = new JTextArea();
    private final JTextArea       inputText         = new JTextArea();

    private Task task;

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
    
    class Task extends SwingWorker<Void,Void>
    {

		@Override
		protected Void doInBackground() throws Exception {
			final List<Signal> message = coder.encode(inputText.getText());
    		try {
                player.play(message);
            }
            catch (InterruptedException | LineUnavailableException e1) {
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
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPane.repaint();
        setContentPane(contentPane);

        final JPanel panel = new JPanel();

        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "T\u0142umacz", TitledBorder.LEADING, TitledBorder.TOP,
                null, new Color(0, 0, 0)));

        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("C", Font.PLAIN, 12));
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
                       //     @Override
                       //     public void mouseClicked(final MouseEvent e) {
                       //         encodeAndPrint();
                       //     }
                       // });
                
                        JButton btnPlay = new JButton("Odtw\u00F3rz");
                        btnPlay.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
                        		encodeAndPrint();
                        		task = new Task();
                                //task.addPropertyChangeListener(this);
                                task.execute();
                        	}
                        });
                        
                        JButton btnStop = new JButton("Stop");
                        btnStop.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
                        		
                        		player.stop();
                        	}
                        });
                
                
//                inputText.addActionListener(new ActionListener() {
   //                 @Override
   //                 public void actionPerformed(final ActionEvent e) {
   //                     encodeAndPrint();
  //                  }
  //              });
       //         inputText.setColumns(10);
                
        final JButton btnSave = new JButton("Zapisz");
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                encodeAndPrint();
                final String fileName = "/tmp/file.wav";
                // TODO: Set-up a dialog (?) to chose an output file.
                waveOut.save(audioMorseCoder.getWave(message).array(), fileName);
            }
        });

                btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        encodeAndPrint();
                        final String fileName = "/tmp/file.wav";
                        // TODO: Set-up a dialog (?) to chose an output file.
                        waveOut.save(audioMorseCoder.getWave(message).array(), fileName);
                    }
                });
                inputText.setLineWrap(true);
        outputText.setEditable(false);
                outputText.setLineWrap(true);
        final Font textFieldFont = new Font("Courier New", Font.PLAIN, 20);
                inputText.setFont(textFieldFont);
                outputText.setFont(textFieldFont);
        final GroupLayout gl_panel = new GroupLayout(panel);
                gl_panel.setHorizontalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGap(760)
                			.addComponent(outputLabel))
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGap(56)
                			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                				.addComponent(outputText, Alignment.LEADING)
                				.addComponent(inputText, Alignment.LEADING)
                				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
                					.addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
                					.addGap(48)
                					.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
                					.addGap(53)
                					.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                					.addGap(62)
                                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))));
                gl_panel.setVerticalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGap(17)
                			.addComponent(outputLabel)
                			.addGap(15)
                			.addComponent(inputText, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                			.addGap(29)
                			.addComponent(outputText, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                			.addGap(35)
                			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                				.addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                				.addGroup(gl_panel.createSequentialGroup()
                					.addGap(3)
                					.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                				.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))));
                panel.setLayout(gl_panel);
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
                try {
                    player.play(message);
                }
                catch (InterruptedException | LineUnavailableException e1) {
                    e1.printStackTrace();
                }
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
                final String fileName = "/tmp/file.wav";
                // TODO: Set-up a dialog (?) to chose an output file.
                waveOut.save(audioMorseCoder.getWave(message).array(), fileName);
            }
        });

        inputText.setLineWrap(true);
        outputText.setEditable(false);
        outputText.setLineWrap(true);
        final Font textFieldFont = new Font("Courier New", Font.PLAIN, 20);
        inputText.setFont(textFieldFont);
        outputText.setFont(textFieldFont);
        final GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(760)
                                .addComponent(outputLabel))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(56)
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(outputText, Alignment.LEADING)
                                        .addComponent(inputText, Alignment.LEADING)
                                        .addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
                                                .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
                                                .addGap(48)
                                                .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
                                                .addGap(53)
                                                .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                                .addGap(62)
                                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))));
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(17)
                                .addComponent(outputLabel)
                                .addGap(15)
                                .addComponent(inputText, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                                .addGap(29)
                                .addComponent(outputText, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                .addGap(35)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(3)
                                                .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))));
        panel.setLayout(gl_panel);
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
                //task.addPropertyChangeListener(this);
                task.execute();
            }
        });

        final JButton btnStop = new JButton("Stop");

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
                final String fileName = "/tmp/file.wav";
                // TODO: Set-up a dialog (?) to chose an output file.
                waveOut.save(audioMorseCoder.getWave(message).array(), fileName);
            }
        });

        inputText.setLineWrap(true);
        outputText.setEditable(false);
        outputText.setLineWrap(true);
        final Font textFieldFont = new Font("Courier New", Font.PLAIN, 20);
        inputText.setFont(textFieldFont);
        outputText.setFont(textFieldFont);
        final GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(760)
                                .addComponent(outputLabel))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(56)
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(outputText, Alignment.LEADING)
                                        .addComponent(inputText, Alignment.LEADING)
                                        .addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
                                                .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
                                                .addGap(48)
                                                .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
                                                .addGap(53)
                                                .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                                .addGap(62)
                                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))));
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(17)
                                .addComponent(outputLabel)
                                .addGap(15)
                                .addComponent(inputText, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                                .addGap(29)
                                .addComponent(outputText, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                .addGap(35)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(3)
                                                .addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))));
        panel.setLayout(gl_panel);
        contentPane.setLayout(gl_contentPane);

    }

    void encodeAndPrint() {
        message = coder.encode(inputText.getText());
        outputText.setText(JF.toString(message));
        contentPane.repaint();
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
