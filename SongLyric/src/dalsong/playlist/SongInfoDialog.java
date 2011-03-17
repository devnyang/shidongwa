package dalsong.playlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import dalsong.player.DalSongPlayer;

import java.awt.Rectangle;


public class SongInfoDialog extends JDialog{
	private JPanel jContentPane;
	private JTabbedPane jInfoPane;
	private File mp3File;
	private JFrame owner;
	private JButton jButtonOK = null;
	
	public SongInfoDialog(File file, JFrame owner){
		super();
		
		this.owner = owner;
		this.mp3File = file;
		
		initialize();

	}
	
	private void initialize() {
		this.setTitle(DalSongPlayer.POPUP_MENU_INFORMATION);
		this.setContentPane(getJContentPane());
		this.setSize(new Dimension(530, 415));
		this.setLocationRelativeTo(owner);
		this.setVisible(true);

	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JTextField pathField = new JTextField(mp3File.getPath());
			pathField.moveCaretPosition(0);
			pathField.setBounds(10, 8, 500, 20);
			pathField.setEditable(false);
			
			jContentPane.add(pathField);
			jContentPane.add(getInfoPane());
			jContentPane.add(getJButtonOK(), null);
			
			
		}
		return jContentPane;
	}
	


	private JTabbedPane getInfoPane() {
		if(jInfoPane == null){
			jInfoPane = new JTabbedPane();
			jInfoPane.setBounds(10, 30, 500, 320);
			
			JPanel tempPane = new JPanel();
			tempPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			tempPane.setLayout(new GridLayout());
			
			tempPane.add(new TagVer1Pane(mp3File));
			tempPane.add(new TagVer2Pane(mp3File));
			
			jInfoPane.addTab("Mp3 Tag", null, tempPane, null);
		}
		return jInfoPane;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(444, 355, 66, 24));
			jButtonOK.setText("Ok");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonOK;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
