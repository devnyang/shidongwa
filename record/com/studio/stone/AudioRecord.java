package com.studio.stone;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;

import org.code4fun.AudioPlayer;



public class AudioRecord extends JFrame implements ActionListener, LineListener{
      private final Timer			updater				= new Timer(1000, this);

	  public static final JFileChooser fc = new JFileChooser( "." );
	  
	  private 	  AudioPlayer	clip = new AudioPlayer(this);
	  private final JProgressBar 	progressBar			= new JProgressBar();
	  private final JLabel 		statusLabel 		= new JLabel("Welcome to Little Recorder...");
	    
	  AudioFormat audioFormat;
	  TargetDataLine targetDataLine;

	  final JButton captureBtn = new JButton("Record");
	  final JButton stopBtn = new JButton("Stop Record");
	  final JButton playBtn = new JButton("Play");
	  final JButton stopPlayBtn = new JButton("Stop Play");
	  
	  final JPanel btnPanel = new JPanel();
	  final ButtonGroup btnGroup = new ButtonGroup();
	/*  final JRadioButton aifcBtn =
	                        new JRadioButton("AIFC");*/
/*	  final JRadioButton aiffBtn =
	                        new JRadioButton("AIFF");
	  final JRadioButton auBtn =//selected at startup
	                     new JRadioButton("AU");*/
	/*  final JRadioButton sndBtn =
	                         new JRadioButton("SND");*/
/*	  final JRadioButton waveBtn =
	                        new JRadioButton("WAVE",true);*/
	  
	  final JButton saveBtn = new JButton("SAVE");

	  protected void initSaveDialog(){
			fc.setDialogTitle("Choose output file ..");
			fc.setCurrentDirectory(new File("."));
			fc.setAcceptAllFileFilterUsed(false);
			
			
		    fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
		        public boolean accept(File f) { //设定可用的文件的后缀名
		          if(f.getName().endsWith(".wav") || f.isDirectory()){
		            return true;
		          }
		          return false;
		        }
		        public String getDescription() {
		          return "WAVE(*.wav)";
		        }
		      });
		    
		    fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
		        public boolean accept(File f) { //设定可用的文件的后缀名
		          if(f.getName().endsWith(".mp3") || f.isDirectory()){
		            return true;
		          }
		          return false;
		        }
		        public String getDescription() {
		          return "MP3(*.mp3)";
		        }
		      });		  
	  }
	  
	  public static void main( String args[]){
	    new AudioRecord();
	  }//end main

	  public AudioRecord(){//constructor
		  
	    setResizable(false);
		  
        JPanel main 	= new JPanel(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout());
        JPanel progress = new JPanel(new GridLayout(1,1));
        JPanel status   = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // control panel setup
        controls.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        controls.add(captureBtn);
        controls.add(stopBtn);
        controls.add(playBtn);
        controls.add(stopPlayBtn);
        controls.add(saveBtn);
        
	    captureBtn.setEnabled(true);
	    stopBtn.setEnabled(false);
	    playBtn.setEnabled(false);
	    saveBtn.setEnabled(false);
	    stopPlayBtn.setEnabled(false);

        // progressbar panel setup
        progressBar.setBorderPainted(false);
        progressBar.setString("");
//        progressBar.setIndeterminate(true);
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
        
	    initSaveDialog();
	    //Register anonymous listeners
	    captureBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                                  ActionEvent e){
	          captureBtn.setEnabled(false);
	          stopBtn.setEnabled(true);
	          saveBtn.setEnabled(false);
	          //Capture input data from the
	          // microphone until the Stop button is
	          // clicked.
	          captureAudio();
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()

	    stopBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                                  ActionEvent e){
	          captureBtn.setEnabled(true);
	          stopBtn.setEnabled(false);
	          playBtn.setEnabled(true);
	          saveBtn.setEnabled(true);
	          //Terminate the capturing of input data
	          // from the microphone.
	          targetDataLine.stop();
	          targetDataLine.close();
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()

	    playBtn.addActionListener(
	  	      new ActionListener(){
	  	        public void actionPerformed(ActionEvent e){
	  	        	
	  	        	playBtn.setEnabled(false);
	  	        	stopPlayBtn.setEnabled(true);
	  	        	
	  	        	try{
	  	        		
	  	                if (clip.init("junk.wav")) {

	  	                	// initializes progress bar
/*	  	                	progressBar.setIndeterminate(false);
	  	                	progressBar.setMaximum((int)clip.getLength());
	  	                	progressBar.setValue(0);
	  	                	progressBar.setStringPainted(true);*/
	  	                	// play
	  	                    clip.play();
	  	                }else  JOptionPane.showMessageDialog(AudioRecord.this, clip.error);
	  	                
/*	  	        		Thread t = new AePlayWave("junk.wav");
	  	        		t.start();
	  	        		t.join();*/
	  	        		
		  	  			
/*		  	        	File	soundFile = new File("junk.wav");
		  	        	AudioInputStream	audioInputStream = null;
	  	        		audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	
		  	  			
		  	  			Clip soundClip = null;
		  	  			soundClip = AudioSystem.getClip();
		  	  			soundClip.open(audioInputStream);
						
			  	  		if (soundClip != null) {
			  	          soundClip.setFramePosition(0);
			  	          soundClip.start();
			  	  		}*/

//						playBtn.setEnabled(true);
						
	  	        	}catch(Exception ex){
	  	        		ex.printStackTrace();
	  	        	}
		  	  	}		  	  		
	  	     }
	    );
	    
	    stopPlayBtn.addActionListener(
		  	      new ActionListener(){
		  	        public void actionPerformed(ActionEvent e){
		  	        	clip.stop();
		  	        }
		  	      }
	    );
	    
	    saveBtn.addActionListener(
	    	      new ActionListener(){
	    	        public void actionPerformed(ActionEvent e){
	    	        	try {
	    	        		
							File saveFile = AudioRecord.showFC();
							
							String inFile = null;
/*						    if(aiffBtn.isSelected()){
							      inFile = "junk.aif";
							}else if(auBtn.isSelected()){
							      inFile = "junk.au";
							}else if(waveBtn.isSelected()){*/
							inFile = "junk.wav";
//							}//end if
						    if(saveFile != null){
						    	if(saveFile.getName().endsWith("mp3")){
						    		Mp3Encoder.writeFile(inFile, saveFile.getName());						    	
						    	}else{
						    		AudioEditTool.copyWaveFile("junk.wav", saveFile.getName());
						    	}	
						    }
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    	        }//end actionPerformed
	    	      }//end ActionListener
	    );//end addActionListener()
	    
	    
	    setTitle("Little Recorder Tool");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
//	    setSize(300,120);
	    setVisible(true);
	  }//end constructor

	  //This method captures audio input from a
	  // microphone and saves it in an audio file.
	  private void captureAudio(){
	    try{
	      //Get things set up for capture
	      audioFormat = getAudioFormat();
	      DataLine.Info dataLineInfo =
	                          new DataLine.Info(
	                            TargetDataLine.class,
	                            audioFormat);
	      targetDataLine = (TargetDataLine)
	               AudioSystem.getLine(dataLineInfo);

	      //Create a thread to capture the microphone
	      // data into an audio file and start the
	      // thread running.  It will run until the
	      // Stop button is clicked.  This method
	      // will return after starting the thread.
	      new CaptureThread().start();
	    }catch (Exception e) {
	      e.printStackTrace();
	      System.exit(0);
	    }//end catch
	  }//end captureAudio method

	  //This method creates and returns an
	  // AudioFormat object for a given set of format
	  // parameters.  If these parameters don't work
	  // well for you, try some of the other
	  // allowable parameter values, which are shown
	  // in comments following the declarations.
	  private AudioFormat getAudioFormat(){
	    float sampleRate = 8000.0F;
	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;
	    //8,16
	    int channels = 1;
	    //1,2
	    boolean signed = true;
	    //true,false
	    boolean bigEndian = false;
	    //true,false
	    return new AudioFormat(sampleRate,
	                           sampleSizeInBits,
	                           channels,
	                           signed,
	                           bigEndian);
	  }//end getAudioFormat
//	=============================================//

//	Inner class to capture data from microphone
//	 and write it to an output audio file.
	class CaptureThread extends Thread{
	  public void run(){
	    AudioFileFormat.Type fileType = null;
	    File audioFile = null;

	    //Set the file type and the file extension
	    // based on the selected radio button.
	/*    if(aifcBtn.isSelected()){
	      fileType = AudioFileFormat.Type.AIFC;
	      audioFile = new File("junk.aifc");*/
//	    }else 
/*	    if(aiffBtn.isSelected()){
	      fileType = AudioFileFormat.Type.AIFF;
	      audioFile = new File("junk.aif");
	    }else if(auBtn.isSelected()){
	      fileType = AudioFileFormat.Type.AU;
	      audioFile = new File("junk.au");
*/	/*    }else if(sndBtn.isSelected()){
	      fileType = AudioFileFormat.Type.SND;
	      audioFile = new File("junk.snd");*/
//	    }else 
//	    if(waveBtn.isSelected()){
	      fileType = AudioFileFormat.Type.WAVE;
	      audioFile = new File("junk.wav");
//	    }//end if

	    try{
	      targetDataLine.open(audioFormat);
	      targetDataLine.start();
	      AudioSystem.write(
	            new AudioInputStream(targetDataLine),
	            fileType,
	            audioFile);
	    }catch (Exception e){
	      e.printStackTrace();
	    }//end catch

	  }//end run
	}//end inner class CaptureThread
//	=============================================//


		public static File showFC() throws IOException
		{		    
			fc.showSaveDialog(null);


			  File file = fc.getSelectedFile(); 
			  String path = file.getAbsolutePath(); 
			 
			  //String extension = getExtensionForFilter(fc.getFileFilter()); 
			  FileFilter ff = fc.getFileFilter();
			  
			  String nameDesc = ff.getDescription();
			  String extName = "wav";
			  if(nameDesc.indexOf("WAV") == -1 ){
				  extName = "mp3";
			  }
			  

			 file = new File(path + "." + extName); 

			  
			return file;
		}
		
	public void update(LineEvent le) {

		 if (le.getType()== LineEvent.Type.CLOSE) {
	    		updater.stop();
	    		statusLabel.setText("Stopped");
	    		progressBar.setString("");
  	        	playBtn.setEnabled(true);
  	        	stopPlayBtn.setEnabled(false);
//	            progressBar.setIndeterminate(true);
		 }

		 if (le.getType()== LineEvent.Type.START) {
			 progressBar.setIndeterminate(false);
	    		progressBar.setString("");
	    		updater.start();
	    		stopPlayBtn.setEnabled(true);
	    		statusLabel.setText("Playing " + clip.getFileName() + " [ " + clip.getAudioFormat() + ", time: " + clip.getLength() + "sec ]");
		 }
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
		
}
