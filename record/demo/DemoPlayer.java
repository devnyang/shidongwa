package demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import org.code4fun.AudioPlayer;

/**
 * This is a dimonstrative use of the AudioPlayer class.
 *
 * @author Code4Fun Team <a href="http://code4fun.org">http://code4fun.org</a>
 */
public class DemoPlayer extends JFrame implements ActionListener, LineListener {

    private static final long serialVersionUID 		= 1L;

    private final JButton 		play  				= new JButton("play");
    private final JButton 		stop  				= new JButton("stop");
    private final JButton 		mute  				= new JButton("mute");
    private final JButton 		loop  				= new JButton("loop is off");
    private final JButton 		load  				= new JButton("load");
    private final JProgressBar 	progressBar			= new JProgressBar();
    private final JLabel 		statusLabel 		= new JLabel("Welcome to DemoPlayer, click load to start...");
    private 	  AudioPlayer	clip    			= new AudioPlayer(this);
    private 	  String 		filePath     		= null;
    private final Timer			updater				= new Timer(1000, this);


    /**
     * Default constructor.
     */
    public DemoPlayer() {

        super(":.: DemoPlayer :.:");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main 	= new JPanel(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout());
        JPanel progress = new JPanel(new GridLayout(1,1));
        JPanel status   = new JPanel(new FlowLayout(FlowLayout.LEFT));

        play.setEnabled(false);
        stop.setEnabled(false);
        mute.setEnabled(false);
        loop.setEnabled(false);
        play.addActionListener(this);

        stop.addActionListener(this);
        mute.addActionListener(this);
        loop.addActionListener(this);
        loop.setEnabled(false);
        load.addActionListener(this);

        // control panel setup
        controls.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        controls.add(play);
        controls.add(stop);
        controls.add(mute);
        controls.add(loop);
        controls.add(load);

        // progressbar panel setup
        progressBar.setBorderPainted(false);
        progressBar.setString("");
        progressBar.setIndeterminate(true);
        progress.add(progressBar);

        // status panel setup
        status.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        status.add(statusLabel);

        main.add(controls, BorderLayout.PAGE_START);
        main.add(status, BorderLayout.PAGE_END);
        main.add(progress, BorderLayout.CENTER);
        this.setContentPane(main);

        // calculate frame size by its subcomponents
        pack();
    }

    /**
     * Implements inerithed method ActionListener::actionPerformed().
     */
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        if (source == play) {

                if (clip.init(filePath)) {

                	// initializes progress bar
                	progressBar.setIndeterminate(false);
                	progressBar.setMaximum((int)clip.getLength());
                	progressBar.setValue(0);
                	progressBar.setStringPainted(true);
                	// play
                    clip.play();
                }
                else  JOptionPane.showMessageDialog(this, clip.error);
        }

        else if (source == stop) {

            clip.stop();
        }

        else if (source == loop) {

            if (clip.loopStatus()) {
                clip.setLoop(false);
                loop.setText("loop is off");
            }
            else {
                clip.setLoop(true);
                loop.setText("loop is on");
            }
        }

        else if (source == mute) {

            clip.mute();
        }

        else if (source == load) {

        	// Create a JFileChooser Object
            final JFileChooser fc = new JFileChooser();
            // Open JFileChooser Dialog
            int returnVal = fc.showOpenDialog(this);

            // Check if user opens file
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File file = fc.getSelectedFile();
                filePath = file.getAbsolutePath();
                play.setEnabled(true);
            }
        }

        else if (source == updater) {

        	if (clip.isPlaying()) {

        		int clipValue = (int)clip.getPosition();
        		progressBar.setValue(clipValue);
        		progressBar.setString(clipValue + "sec");
        	}
        	else progressBar.setValue(0);
        }

        else { /* do nothing */ }
    }

    /**
     * Implement inherited methods LineListener::update().
     */
    public void update(LineEvent le) {

    	if (le.getType()== LineEvent.Type.CLOSE) {

    		updater.stop();
    		statusLabel.setText("Stopped");
    		progressBar.setString("");
            progressBar.setIndeterminate(true);
    	}

    	if (le.getType()== LineEvent.Type.START) {

    		progressBar.setIndeterminate(false);
    		progressBar.setString("");
    		updater.start();
    		stop.setEnabled(true);
    		mute.setEnabled(true);
    		loop.setEnabled(true);
    		statusLabel.setText("Playing " + clip.getFileName() + " [ " + clip.getAudioFormat() + ", time: " + clip.getLength() + "sec ]");
    	}
    }

    /**
     * Main.
     */
    public static void main(String[] args) {

        DemoPlayer dp = new DemoPlayer();
        dp.setVisible(true);
    }

}   // end class