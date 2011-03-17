package com.studio.stone;

import java.awt.Container;

import javax.swing.JApplet;

public class RecordApplet extends JApplet {

	public void init(){
		Container cp = getContentPane();
		cp.add(new AudioRecord().getRootPane());
	}
}
