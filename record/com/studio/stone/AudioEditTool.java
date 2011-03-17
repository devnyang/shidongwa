package com.studio.stone;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioEditTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		copyWaveFile("junk.wav","to.wav");
	}
	
	public static void copyWaveFile(String in, String out){
		
		AudioInputStream	audioInputStream = null;
		File	soundFile = new File(in);

		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			/*
			 *	In case of an exception, we dump the exception
			 *	including the stack trace to the console output.
			 *	Then, we exit the program.
			 */
			e.printStackTrace();
			System.exit(1);
		}
		
		File	outputFile = new File(out);
		try
		{
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}



	}

}
