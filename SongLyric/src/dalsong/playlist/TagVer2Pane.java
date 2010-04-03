package dalsong.playlist;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.io.File;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;

import dalsong.player.DalSongPlayer;


public class TagVer2Pane extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabelTrackNo = null;
	private JTextField jTextFieldTrackNo = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelArtist = null;
	private JLabel jLabelAlbum = null;
	private JLabel jLabelYear = null;
	private JLabel jLabelGenre = null;
	private JLabel jLabelExpl = null;
	private JTextField jTextFieldTitle = null;
	private JTextField jTextFieldArtist = null;
	private JTextField jTextFieldAlbum = null;
	private JTextField jTextFieldYear = null;
	private JTextField jTextFieldGenre = null;
	private JTextField jTextFieldExpl = null;
	private JLabel jLabelVer = null;
	private JLabel jLabelComp = null;
	private JLabel jLabelOriginalArtist = null;
	private JLabel jLabelCopyRight = null;
	private JLabel jLabelIntAddr = null;
	private JLabel jLabelIncoding = null;
	private JTextField jTextFieldComp = null;
	private JTextField jTextFieldOriginalArtist = null;
	private JTextField jTextFieldCopyRight = null;
	private JTextField jTextFieldIntAddr = null;
	private JTextField jTextFieldIncoding = null;
	
	private String strTrackNo;
	private String strTitle;
	private String strArtist;
	private String strAlbum;
	private String strYear;
	private String strGenre;
	private String strComment;
	private String strAnchorComposer;
	private String strCopyright;
	private String strIntAddr;
	private String strIncoding;

	
	/**
	 * This is the default constructor
	 */
	public TagVer2Pane(File f) {
		super();
		
		try {
			MP3File mp3file = new MP3File(f);
			AbstractID3v2 tag = mp3file.getID3v2Tag();
	
			strTrackNo = tag.getTrackNumberOnAlbum();
			strTitle = tag.getSongTitle();
			strArtist = tag.getLeadArtist();
			strAlbum = tag.getAlbumTitle();
			strYear = tag.getYearReleased();
			strGenre = tag.getSongGenre();
			strComment = tag.getSongComment();
			strAnchorComposer = tag.getAuthorComposer();

			
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
		jLabelIncoding = new JLabel();
		jLabelIncoding.setBounds(new Rectangle(15, 261, 120, 18));
		jLabelIncoding.setText(DalSongPlayer.TAG_ENCODED_BY);
		jLabelIntAddr = new JLabel();
		jLabelIntAddr.setBounds(new Rectangle(15, 241, 72, 18));
		jLabelIntAddr.setText(DalSongPlayer.TAG_URL);
		jLabelCopyRight = new JLabel();
		jLabelCopyRight.setBounds(new Rectangle(15, 221, 70, 18));
		jLabelCopyRight.setText(DalSongPlayer.TAG_COPYRIGHT);
		jLabelOriginalArtist = new JLabel();
		jLabelOriginalArtist.setBounds(new Rectangle(15, 200, 100, 18));
		jLabelOriginalArtist.setText(DalSongPlayer.TAG_ORIGINAL_ARTIST);
		jLabelComp = new JLabel();
		jLabelComp.setBounds(new Rectangle(15, 180, 60, 18));
		jLabelComp.setText(DalSongPlayer.TAG_COMPOSER);
		jLabelVer = new JLabel();
		jLabelVer.setBounds(new Rectangle(7, 7, 38, 18));
		jLabelVer.setText("ID3v2");
		jLabelExpl = new JLabel();
		jLabelExpl.setBounds(new Rectangle(15, 136, 60, 18));
		jLabelExpl.setText(DalSongPlayer.TAG_COMMENT);
		jLabelGenre = new JLabel();
		jLabelGenre.setBounds(new Rectangle(120, 113, 40, 18));
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
		this.setSize(245, 307);
		this.setLayout(null);
		this.add(jLabelTrackNo, null);
		this.add(getJTextFieldTrackNo(), null);
		this.add(jLabelTitle, null);
		this.add(jLabelArtist, null);
		this.add(jLabelAlbum, null);
		this.add(jLabelYear, null);
		this.add(jLabelGenre, null);
		this.add(jLabelExpl, null);
		this.add(getJTextFieldTitle(), null);
		this.add(getJTextFieldArtist(), null);
		this.add(getJTextFieldAlbum(), null);
		this.add(getJTextFieldYear(), null);
		this.add(getJTextFieldGenre(), null);
		this.add(getJTextFieldExpl(), null);
		this.add(jLabelVer, null);
		this.add(jLabelComp, null);
		this.add(jLabelOriginalArtist, null);
		this.add(jLabelCopyRight, null);
		this.add(jLabelIntAddr, null);
		this.add(jLabelIncoding, null);
		this.add(getJTextFieldComp(), null);
		this.add(getJTextFieldOriginalArtist(), null);
		this.add(getJTextFieldCopyRight(), null);
		this.add(getJTextFieldIntAddr(), null);
		this.add(getJTextFieldIncoding(), null);
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
	private JTextField getJTextFieldExpl() {
		if (jTextFieldExpl == null) {
			jTextFieldExpl = new JTextField();
			jTextFieldExpl.setText(strComment);
			jTextFieldExpl.moveCaretPosition(0);
			jTextFieldExpl.setBounds(new Rectangle(74, 136, 160, 42));
		}
		return jTextFieldExpl;
	}

	/**
	 * This method initializes jTextFieldComp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldComp() {
		if (jTextFieldComp == null) {
			jTextFieldComp = new JTextField();
			jTextFieldComp.moveCaretPosition(0);
			jTextFieldComp.setBounds(new Rectangle(118, 180, 116, 18));
		}
		return jTextFieldComp;
	}

	/**
	 * This method initializes jTextFieldOriginalArtist	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldOriginalArtist() {
		if (jTextFieldOriginalArtist == null) {
			jTextFieldOriginalArtist = new JTextField();
			jTextFieldOriginalArtist.moveCaretPosition(0);
			jTextFieldOriginalArtist.setBounds(new Rectangle(118, 200, 116, 18));
		}
		return jTextFieldOriginalArtist;
	}

	/**
	 * This method initializes jTextFieldCopyRight	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldCopyRight() {
		if (jTextFieldCopyRight == null) {
			jTextFieldCopyRight = new JTextField();
			jTextFieldCopyRight.moveCaretPosition(0);
			jTextFieldCopyRight.setBounds(new Rectangle(118, 221, 116, 18));
		}
		return jTextFieldCopyRight;
	}

	/**
	 * This method initializes jTextFieldIntAddr	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldIntAddr() {
		if (jTextFieldIntAddr == null) {
			jTextFieldIntAddr = new JTextField();
			jTextFieldIntAddr.moveCaretPosition(0);
			jTextFieldIntAddr.setBounds(new Rectangle(118, 241, 116, 18));
		}
		return jTextFieldIntAddr;
	}

	/**
	 * This method initializes jTextFieldIncoding	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldIncoding() {
		if (jTextFieldIncoding == null) {
			jTextFieldIncoding = new JTextField();
			jTextFieldIncoding.moveCaretPosition(0);
			jTextFieldIncoding.setBounds(new Rectangle(118, 261, 116, 18));
		}
		return jTextFieldIncoding;
	}

}  //  @jve:decl-index=0:visual-constraint="159,44"
