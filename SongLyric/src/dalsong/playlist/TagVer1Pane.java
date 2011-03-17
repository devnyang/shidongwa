package dalsong.playlist;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.id3.ID3v1;


import dalsong.player.DalSongPlayer;
import dalsong.util.Utility;

import java.io.File;

public class TagVer1Pane extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabelTrackNo = null;
	private JTextField jTextFieldTrackNo = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelArtist = null;
	private JLabel jLabelAlbum = null;
	private JLabel jLabelYear = null;
	private JLabel jLabelGenre = null;
	private JLabel jLabelComment = null;
	private JTextField jTextFieldTitle = null;
	private JTextField jTextFieldArtist = null;
	private JTextField jTextFieldAlbum = null;
	private JTextField jTextFieldYear = null;
	private JTextField jTextFieldGenre = null;
	private JTextField jTextFieldComment = null;
	private JLabel jLabelVer = null;

	private String strTrackNo;
	private String strTitle;
	private String strArtist;
	private String strAlbum;
	private String strYear;
	private String strGenre;
	private String strComment;
	
	/**
	 * This is the default constructor
	 */
	public TagVer1Pane(File f) {
		super();
		
		try {
			MP3File mp3file = new MP3File(f);
			ID3v1 tag = mp3file.getID3v1Tag();
	
			strTrackNo = Utility.toKorean(tag.getTrackNumberOnAlbum());
			strTitle = Utility.toKorean(tag.getTitle()); 
			strArtist = Utility.toKorean(tag.getArtist());
			strAlbum = Utility.toKorean(tag.getAlbum());;
			strYear = Utility.toKorean(tag.getYear());
			strGenre = (String)TagConstant.genreIdToString.get(new Long(tag.getGenre()));
			strComment = Utility.toKorean(tag.getComment());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabelVer = new JLabel();
		jLabelVer.setBounds(new Rectangle(7, 7, 38, 18));
		jLabelVer.setText("ID3v1");
		jLabelComment = new JLabel();
		jLabelComment.setBounds(new Rectangle(15, 134, 60, 18));
		jLabelComment.setText(DalSongPlayer.TAG_COMMENT);
		jLabelGenre = new JLabel();
		jLabelGenre.setBounds(new Rectangle(120, 113, 35, 18));
		jLabelGenre.setText(DalSongPlayer.TAG_GENRE);
		jLabelYear = new JLabel();
		jLabelYear.setBounds(new Rectangle(15, 113, 38, 18));
		jLabelYear.setText(DalSongPlayer.TAG_YEAR);
		jLabelAlbum = new JLabel();
		jLabelAlbum.setBounds(new Rectangle(15, 90, 38, 18));
		jLabelAlbum.setText(DalSongPlayer.TAG_ALBUM);
		jLabelArtist = new JLabel();
		jLabelArtist.setBounds(new Rectangle(15, 67, 38, 18));
		jLabelArtist.setText(DalSongPlayer.TAG_ARTIST);
		jLabelTitle = new JLabel();
		jLabelTitle.setBounds(new Rectangle(15, 44, 38, 18));
		jLabelTitle.setText(DalSongPlayer.TAG_TITLE);
		jLabelTrackNo = new JLabel();
		jLabelTrackNo.setBounds(new Rectangle(146, 12, 42, 20));
		jLabelTrackNo.setText(DalSongPlayer.TAG_TRACK);
		this.setSize(245, 161);
		this.setLayout(null);
		this.add(jLabelTrackNo, null);
		this.add(getJTextFieldTrackNo(), null);
		this.add(jLabelTitle, null);
		this.add(jLabelArtist, null);
		this.add(jLabelAlbum, null);
		this.add(jLabelYear, null);
		this.add(jLabelGenre, null);
		this.add(jLabelComment, null);
		this.add(getJTextFieldTitle(), null);
		this.add(getJTextFieldArtist(), null);
		this.add(getJTextFieldAlbum(), null);
		this.add(getJTextFieldYear(), null);
		this.add(getJTextFieldGenre(), null);
		this.add(getJTextFieldComment(), null);
		this.add(jLabelVer, null);
	}

	/**
	 * This method initializes jTextFieldTrackNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTrackNo() {
		if (jTextFieldTrackNo == null) {
			jTextFieldTrackNo = new JTextField();
			jTextFieldTrackNo.setText(strTrackNo);
			jTextFieldTrackNo.moveCaretPosition(0);
			jTextFieldTrackNo.setBounds(new Rectangle(185, 12, 49, 20));
		}
		return jTextFieldTrackNo;
	}

	/**
	 * This method initializes jTextFieldTitle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTitle() {
		if (jTextFieldTitle == null) {
			jTextFieldTitle = new JTextField();
			jTextFieldTitle.setText(strTitle);
			jTextFieldTitle.moveCaretPosition(0);
			jTextFieldTitle.setBounds(new Rectangle(74, 44, 160, 18));
		}
		return jTextFieldTitle;
	}

	/**
	 * This method initializes jTextFieldArtist	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldArtist() {
		if (jTextFieldArtist == null) {
			jTextFieldArtist = new JTextField();
			jTextFieldArtist.setText(strArtist);
			jTextFieldArtist.moveCaretPosition(0);
			jTextFieldArtist.setBounds(new Rectangle(74, 67, 160, 18));
		}
		return jTextFieldArtist;
	}

	/**
	 * This method initializes jTextFieldAlbum	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldAlbum() {
		if (jTextFieldAlbum == null) {
			jTextFieldAlbum = new JTextField();
			jTextFieldAlbum.setText(strAlbum);
			jTextFieldAlbum.moveCaretPosition(0);
			jTextFieldAlbum.setBounds(new Rectangle(74, 91, 160, 18));
		}
		return jTextFieldAlbum;
	}

	/**
	 * This method initializes jTextFieldYear	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldYear() {
		if (jTextFieldYear == null) {
			jTextFieldYear = new JTextField();
			jTextFieldYear.setText(strYear);
			jTextFieldYear.moveCaretPosition(0);
			jTextFieldYear.setBounds(new Rectangle(74, 113, 40, 18));
		}
		return jTextFieldYear;
	}

	/**
	 * This method initializes jTextFieldGenre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldGenre() {
		if (jTextFieldGenre == null) {
			jTextFieldGenre = new JTextField();
			jTextFieldGenre.setText(strGenre);
			jTextFieldGenre.moveCaretPosition(0);
			jTextFieldGenre.setBounds(new Rectangle(157, 113, 77, 18));
		}
		return jTextFieldGenre;
	}

	/**
	 * This method initializes jTextFieldExpl	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldComment() {
		if (jTextFieldComment == null) {
			jTextFieldComment = new JTextField();
			jTextFieldComment.setText(strComment);
			jTextFieldComment.moveCaretPosition(0);
			jTextFieldComment.setBounds(new Rectangle(74, 136, 160, 18));
		}
		return jTextFieldComment;
	}

}  //  @jve:decl-index=0:visual-constraint="159,44"
