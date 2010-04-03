package dalsong.player;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;


import javax.swing.JTextArea;

public class AboutDialog2 extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;

	private ImageIcon imgLogo = new ImageIcon("./res/moon_logo.png");
	private JFrame owner;
	private JTextArea jTextArea = null;
	
	private int languageMode;
	/**
	 * @param owner
	 */
	public AboutDialog2(JFrame owner) {
		super(owner);
		this.owner = owner;
		initialize();
	}
	
	public AboutDialog2(JFrame owner, int mode) {
		super(owner);
		this.owner = owner;
		languageMode = mode;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle(DalSongPlayer.ABOUT_DLG_ACTION_COMMAND);
		this.setLocationRelativeTo(owner);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(Color.BLACK);
			jPanel.setPreferredSize(new Dimension(360, 190));
			JLabel logoLb = new JLabel();
			logoLb.setIcon(imgLogo);
			logoLb.setBounds(193, 6, 179, 163);
			jPanel.add(logoLb);
			
			if(languageMode == DalSongPlayer.ENGLISH_MODE)
				jPanel.add(getJTextArea_eng(), null);
			else
				jPanel.add(getJTextArea_kor(), null);
			
			/*
			JLabel blogAddr = new JLabel("http://blog.naver.com/drub5");
			blogAddr.setForeground(Color.GRAY);
			blogAddr.setBackground(Color.BLACK);
			blogAddr.setBounds(17, 145, 200, 20);
			jPanel.add(blogAddr);
			*/
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_eng() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(16, 15, 185, 169));
			jTextArea.setEditable(false);
			jTextArea.setBackground(null);
			jTextArea.setForeground(Color.GRAY);
			String text = "DalSong \n" +
			"Version 0.45\n\n" +
			"DalSong is a multi-platform\n" +
			"Java mp3 player\n" +
			"that automatically provides\n" +
			"synchronized lyrics.\n\n" +
			"Author : Hee-Sung, Choi\n" +
			"http://blog.naver.com/drub5";

			jTextArea.setText(text);
		}
		return jTextArea;
	}
	
	private JTextArea getJTextArea_kor() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(16, 15, 185, 169));
			jTextArea.setEditable(false);
			jTextArea.setBackground(null);
			jTextArea.setForeground(Color.GRAY);
			String text = "달쏭 \n" +
			"Version 0.45\n\n" +
			"달쏭은 자바로 구현한 \n" + 
			"가사지원 MP3 플레이어입니다.\n\n" +
			"만든이 : 최희성\n" +
			"http://blog.naver.com/drub5";
			
			jTextArea.setText(text);
		}
		return jTextArea;
	}

}  //  @jve:decl-index=0:visual-constraint="193,76"


