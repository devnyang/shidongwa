package dalsong.mp3info;

public interface VbrInfoFrame {
	
	public int getFrameCount();
	public boolean isValid();
	public boolean isVbr();
	public int getFileSize();
}