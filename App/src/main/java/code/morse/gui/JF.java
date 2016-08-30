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
import morse.code.audio.MorseCodePlayer;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;

public class JF extends JFrame{

    private final JPanel     contentPane;
    private final Codec      coder       = new Codec();
    private final JTextArea  outputText = new JTextArea();
    private final JTextArea  inputText = new JTextArea();
    MorseCodePlayer          player      = new MorseCodePlayer();
    
    private Task task;
    Thread playerThread;
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
        
        JPanel panel = new JPanel();
       
        
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "T\u0142umacz", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("C", Font.PLAIN, 14));
        panel.repaint();
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(7)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        			.addContainerGap())
        );
                
                        final JButton btnEncode = new JButton("Przet\u0142umacz");
                        btnEncode.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
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
                
                JButton btnSave = new JButton("Zapisz");
                Font textFieldFont = new Font("Courier New", Font.PLAIN, 20);
                
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                
                JScrollPane scrollPane_1 = new JScrollPane();
                GroupLayout gl_panel = new GroupLayout(panel);
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
                			.addGap(61))
                );
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
                						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))))
                			.addGap(35))
                );
                Font buttonFont = new Font("C", Font.PLAIN, 16);
                btnEncode.setFont(buttonFont);
                btnPlay.setFont(buttonFont);
                btnSave.setFont(buttonFont);       
                btnStop.setFont(buttonFont);
                
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
        outputText.setText(JF.toString(coder.encode(inputText.getText())));
        contentPane.repaint();
    }

    static String toString(final List<Signal> signals) {
        String result = "";

        for (final Signal s : signals) {
            if (Signal.DASH == s) {
                result += "-";
            }
            else if (Signal.DOT == s){
                result += ".";
            }
            else
            {
            	result += " | ";
            }
        }

        return result;
    }
}
