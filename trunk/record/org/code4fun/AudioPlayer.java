package org.code4fun;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * <p>
 * AudioPlayer is an open source Java class that manages WAV, AIFF and AU audio
 * files playback.<br>
 * It provides basic functionality like play, stop, mute and loop.<br>
 * Implements many useful methods to retrieve song position, total time,
 * remaining time, song audio format (sample frequency, resolution, channels).
 * </p>
 * <p>
 * It's written using the JavaSound, a standard package included in J2SE since
 * version 1.3, so you don't need to distribute any extra
 * package like JMF (JavaMediaFramework) with your application.<br>
 * JavaSound provides the lowest level of audio support on the Java platform and
 * AudioPlayer represent higher-level user interfaces built on top of
 * Java Sound.<br>
 * The Java Sound engine can render 8 or 16 bit audio data, in mono or stereo,
 * with sample rates from 8KHz to 48KHz.
 * </p>
 * <p>
 * AudioPlayer can play file of "any" size with low memory usage.
 * Each AudioPlayer object run as separated thread so your application main flow
 * will not freeze during playback.
 * </p>
 * <p>
 * If you experience slow response in mute, stop or play actions or "crackle"
 * during playback try to setup "internalBuffer" parameter: a bigger value give
 * you smooth playing but slower reaction, a little value otherwise give you
 * high perfomance but worse audio quality.
 * </p>
 * AudioPlayer is licensed under the terms of the
 * <a href="http://www.gnu.org/licenses/gpl.html">GNU General Public License</a>.
 *
 * @author	Code4Fun Team <a href="http://code4fun.org">http://code4fun.org</a>
 * @version 1.0 , 2005/08/17
 */
public class AudioPlayer implements Runnable {

	private Thread 				runner 			 	= null;

	private File 				audioFile		 	= null;

	private AudioInputStream	audioInputStream 	= null;
	private SourceDataLine 		sourceDataLine   	= null;
	private AudioFormat 		audioFormat      	= null;
	private LineListener 		lineListener     	= null;

	private boolean 			stop 			 	= false;
	private boolean 			loop			 	= false;
	private BooleanControl 		mute;

	private int 				externalBuffer 	 	= 10000;
	private int 				internalBuffer   	= 8192;

	/**
	 * String that handle errors occurred during initialization.<br>
	 * If init() method fail this variable will be filled with a report.
	 */
	public String error			= "";

    /**
     * Default class constructor.
     */
    public AudioPlayer() { }

    /**
     * Class constructor specifying {@link LineListener} object.
     */
    public AudioPlayer(LineListener ll) {

        lineListener = ll;
    }

    /**
     * Sets line buffer size.
     *
     * @param size buffer size in byte.
     */
    public void setInternalBuffer(int size) {

    	if (size > 0) this.internalBuffer = size;
    }

    /**
     * Gets line buffer size.
     *
     * @return line buffer size in byte.
     */
    public int getInternalBuffer() {

    	return this.internalBuffer;
    }

    /**
     * Gets external buffer size.
     *
     * @return external buffer size in byte.
     */
    public int getExternalBuffer() {

    	return this.externalBuffer;
    }

    /**
     * Gets current loaded file name.
     *
     * @return 	the name of the file or directory denoted by the path if
     * 			audioFile is initialized, null otherwise.
     */
    public String getFileName() {

    	if (this.audioFile != null)	return this.audioFile.getName();
    	else return null;
    }

    /**
     * Gets absolute audio file path.
     *
     * @return 	a String representing the current audio file absolute path if
     * 			audioFile is initialized, null otherwise.
     */
    public String getFilePath() {

        if (this.audioFile != null) return audioFile.getAbsolutePath();
        else return null;
    }

    /**
     * Gets sound file number of audio channels.
     *
     * @return	'stereo' String for two channel, 'mono' for one channel.<br>
     *         	If audioFile is not initialized return null.
     */
    private String getChannels() {

    	if (this.audioFile != null) {

    		if (sourceDataLine.getFormat().getChannels() == 1) return "mono";
	    	if (sourceDataLine.getFormat().getChannels() == 2) return "stereo";
	    	return "unknown";
    	}
    	else return null;
    }

    /**
     * Gets sound file number of bits per sample.
     *
     * @return	the number of bits in each sample of the sound file.<br>
     * 		   	If audioFile is not initialized return 0.
     */
    private int getResolution() {

    	if (this.audioFile != null) {

    		return sourceDataLine.getFormat().getSampleSizeInBits();
    	}
    	else return 0;
    }

    /**
     * Gets sound file sample rate.
     *
     * @return	sound file sample rate in Hz, 0 if sample rate is not defined
     * 			for this audio file or audioFile is not initialized.
     */
    private float getSampleRate() {

    	if (this.audioFile != null && sourceDataLine.getFormat().getSampleRate() != AudioSystem.NOT_SPECIFIED) {

    		return sourceDataLine.getFormat().getSampleRate();
    	}
    	else return 0;
    }

    /**
     * Gets string representation of the sound file audio format.
     *
     * @return	a string with resolution in bit, sample rate in Hz and audio
     * 			channels (mono or stereo).
     */
    public String getAudioFormat() {

    	int res = getResolution();
    	float rate = getSampleRate();
    	String channels = getChannels();
    	String audioFormat = "";

    	if (res != 0) audioFormat += res + "bit";

    	if (rate != 0) {

    		if (res !=0) audioFormat += " ";
    		audioFormat += rate + "Hz";
    	}

    	if (channels != null) {

    		if (res != 0 || rate != 0) audioFormat += " ";
    	 	audioFormat += channels;
    	}

    	return audioFormat;
    }

    /**
     * Gets file length in seconds.
     *
     * @return file length in seconds. If audioFile is not initialized return 0.
     */
    public long getLength() {

    	if (audioInputStream != null && audioFormat != null) {

	        return Math.round(audioInputStream.getFrameLength() / audioFormat.getFrameRate());
    	}
    	else return 0;
    }

    /*
     * Obtains the current position in the audio data, in seconds.
     *
     * @return 	the current position in the audio data, in seconds.
     * 			If audioFile is not initialized return -1.
     *
    public long getPosition() {

    	if (sourceDataLine != null) {

    		return sourceDataLine.getMicrosecondPosition() / 1000000;
    	}
    	else return -1;
    }
    */

    /**
     * Obtains the current position in the audio data, in seconds.
     *
     * @return 	the current position in the audio data, in seconds.
     * 			If stream is closed return -1.
     */
    public long getPosition() {

    	int availableBytes=-1;
    	try {
    		availableBytes =  audioInputStream.available();
    	} catch (IOException e) { return -1; }

    	float totalBytes = (audioInputStream.getFrameLength() * audioFormat.getFrameSize());
    	long currentBytes = (long)(totalBytes - availableBytes);
    	long frameLength = currentBytes/audioFormat.getFrameSize();

    	return (long)(frameLength/audioInputStream.getFormat().getFrameRate());
    }

    /**
     * Obtains the remaining seconds in the audio data.
     *
     * @return 	the remaining seconds in the audio data.
     * 			If audioFile is not correctly initialized return -1.
     */
    public long getRemaining() {

    	long length = getLength();
    	long pos	= getPosition();
    	if (length > 0 && pos != -1) return length - pos;
    	else return -1;
    }

	/**
	 * Initialization routine: check if audio file format is supported and asks
	 * the hardware for the needed resources to play it.<br>
	 * If all steps success the audio file will be ready to play,
	 * otherwise an exhaustive explanation of the occurred error will be stored
	 * in the public variable "error".
	 *
	 * @param 	f - audio file path
	 * @return	<code>true</code> if audio file is successfully loaded;
	 * 			<code>false</code> otherwise.
	 */
    public boolean init(String f) {

		// Check if thread is currently executing
        if (isPlaying()) {

            error = "A sound file is already playing for this AudioPlayer " +
            		"object, stop it first.";
            return false;
        }

        // Try to creates a new File instance by converting the given pathname
        try { audioFile = new File(f); }
        catch (NullPointerException npe) {

            error = "Null reference passed to init.";
            return false;
        }

        try {

            // Obtains an AudioInputStream from the provided File
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            /*
             * Obtains the audio format of the sound data in this audio
             * input stream
             */
            audioFormat = audioInputStream.getFormat();
        }
        catch (IOException e) {
        	// Failed or interrupted I/O operations
            error = "Failed or interrupted I/O operations.";
            return false;
        }
        catch (UnsupportedAudioFileException e) {
			/*
			 * File did not contain valid data of a recognized file type
			 * and format
			 */
            error = "Invalid audio file format.";
            return false;
        }

        /*
         * We have to construct an Info object that specifies the desired
         * properties for the line we want.
         * First, we have to say what kind of line we want.
         * The possibilities are: SourceDataLine (for playback),
         * and TargetDataLine (for recording).
         * Here, we ask for a SourceDataLine.
         * Then, we have to pass an AudioFormat object, so that
         * the Line knows which format the data passed to it will have.
         * Furthermore, we can give Java Sound a hint about how
         * big the internal buffer for the line should be.
         */
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, internalBuffer);

        try {

        	/*
        	 * Obtains a line that matches the description in the
        	 * specified Line.Info object
        	 */
        	sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);

            /*
             * Opens the line with the specified format
             * and suggested buffer size, causing the line to acquire any
             * required system resources and become operational
             */
            sourceDataLine.open(audioFormat, internalBuffer);
        }
        catch(LineUnavailableException e) {

            error = "Line is not available due to resource restrictions.";
            return false;
        }
        catch(IllegalArgumentException e) {

            error = "Selected mixer does not support any lines matching " +
            		"the description.";
            return false;
        }

        /*
         * Adds a listener to this line (if user choose one).
         * Whenever the line's status changes, the listener's update()
         * method is called with a LineEvent object that describes the change.
         */
        if (lineListener!=null) sourceDataLine.addLineListener(lineListener);

		// Gets mute control from our SourceDataLine
        try {
        	mute = (BooleanControl)sourceDataLine.getControl(BooleanControl.Type.MUTE);
        } catch (Exception e) {

            error = e.getMessage();
            return false;
        }

        // Initializing post-load status
        stop = false;

        // Initialization successfully completed
        return true;
    }

    /**
     * Open {@link SourceDataLine}, allows the line to engage in data I/O and
     * start the play routine.
     */
    public void run() {

        /*
         * Opens the line with the specified format and suggested buffer size,
         * causing the line to acquire any required system resources
         * and become operational
         */
        if (!sourceDataLine.isOpen()) {

           try {

        	   /*
                * Opens the line with the specified format
                * and suggested buffer size, causing the line to acquire any
                * required system resources and become operational
                */
        	   sourceDataLine.open(audioFormat, internalBuffer);

        	   rawplay(); // Start rawplay routine
           }
           catch (LineUnavailableException e) {

               error = "Line unavailable";
               abort();
           }
        }
        else {

           	rawplay(); // Start rawplay routine
        }
    }

    /**
     * Play routine: reads up to a specified maximum number of bytes of data
     * from audioInputStream and writes them to the mixer via the sourceDataLine
     * until the audioInputStream is empty or user stop playing.
     */
    private void rawplay() {

        // Allows the line to engage in data I/O
        sourceDataLine.start();

        // Ram external buffer
        byte tempBuffer[] = new byte[externalBuffer];
        int cnt;

        try {

        	while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1 && !stop) {

        		if(cnt > 0){ sourceDataLine.write(tempBuffer, 0, cnt); }

        	} //end while loop
        }
        catch (IOException e1) {

        	error = "Failed or interrupted I/O operations";
        	abort();
        }

        // Reset AudioInputStream to beginning
        try {

        	audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            // If loop mode is enabled and audio file is ended restart playing
            if (loop && !stop) rawplay();
            // Stop all I/O activity, flush line and free resources
            abort();
        }
        catch(UnsupportedAudioFileException e) { abort(); }
        catch(IOException e) { abort(); }

    } // end rawplay method

    /**
     * Causes this thread to begin execution.<br>
     * The Java Virtual Machine calls the run method.
     * If init method is never called or failed this method does nothing.<br>
     * If invoked while a thread is already running, this method does nothing.
     */
    public void play() {

        if (runner == null && audioFile != null) {

            runner = new Thread(this);
            runner.start();
        }
    }

    /**
     * Stop all I/O activity, flush {@link SourceDataLine} and free resources,
     * thread is terminated.
     */
    private void abort() {

        // stop I/O activity
        sourceDataLine.stop();
        // empty sourceDataLine buffer
        sourceDataLine.drain();
        // free allocated resources
        sourceDataLine.close();
        // make thread available for garbage collection
        runner = null;
    }

    /**
     * Check if audio file is playing.
     *
     * @return <code>true</code> if audio file is playing;
     *         <code>false</code> otherwise.
     */
    public boolean isPlaying() {

        if ( sourceDataLine!=null ) {
            return sourceDataLine.isRunning();
        } else return false;
    }

    /**
     * Stop playing.
     */
    public void stop() {

        if (this.isPlaying()) stop = true;
    }

    /**
     * Check if audio file is stopped.
     *
     * @return <code>true</code> if audio file is stopped;
     *         <code>false</code> otherwise.
     */
    public boolean isStopped() { return this.stop; }

    /**
     * Mute/Unmute the line.
     * This works like a two state button, every call the line switch in the
     * opposite status.
     */
    public void mute() {

        if ( mute!=null ) {
	        if (mute.getValue()) mute.setValue(false);
	        else mute.setValue(true);
        }
    }

    /**
     * Enables or disables loop mode.
     *
     * @param b <code>true</code> enable loop;
     * 			<code>false</code> disable loop.
     *
     */
    public void setLoop(boolean b) { this.loop = b; }

    /**
     * Gets current loop status.
     *
     * @return <code>true</code> if loop is enabled;
     * 		   <code>false</code> otherwise.
     */
    public boolean loopStatus() { return this.loop; }

}	// end class