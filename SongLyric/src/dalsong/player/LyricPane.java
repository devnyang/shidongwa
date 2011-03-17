package dalsong.player;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.MouseInputAdapter;



public class LyricPane extends JScrollPane{

//	private Vector<String> strVector = new Vector<String>();
	private JLabel lyricLabel[];
	private JPanel jPane = new JPanel();
	private Integer[] splitTime;
	private int prevTime = -1;
	
	private Font lyricFont = new Font("", Font.PLAIN, 12);
	private Font lyricBoldFont = new Font("", Font.BOLD, 12);
	
	public LyricPane(){
		super();
		init();
	}
	
	private void init(){
		jPane.setLayout(new BoxLayout(jPane, BoxLayout.Y_AXIS));
		jPane.setBackground(Color.WHITE);
		
		setViewportView(jPane);
		mouseAction();
	}
	
	private void mouseAction(){
		MouseInputAdapter mia = new MouseInputAdapter() {

			int m_XDifference, m_YDifference;

			Container c;

			public void mouseDragged(MouseEvent e) {
//				c = LyricPane.this.getParent();
				c = jPane.getParent();
				if (c instanceof JViewport) {
					JViewport jv = (JViewport) c;
					Point p = jv.getViewPosition();
					int newX = p.x - (e.getX()-m_XDifference);
					int newY = p.y - (e.getY()-m_YDifference);
//					int maxX = LyricPane.this.getWidth() - jv.getWidth();
//					int maxY = LyricPane.this.getHeight() - jv.getHeight();
					int maxX = jPane.getWidth() - jv.getWidth();
					int maxY = jPane.getHeight() - jv.getHeight();
					if (newX < 0)
						newX = 0;
					if (newX > maxX)
						newX = maxX;
					if (newY < 0)
						newY = 0;
					if (newY > maxY)
						newY = maxY;
					jv.setViewPosition(new Point(newX, newY));
				}
			}

			public void mousePressed(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(
						Cursor.MOVE_CURSOR));
				m_XDifference = e.getX();
				m_YDifference = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(
						Cursor.DEFAULT_CURSOR));
			} 
			
	
		};
		jPane.addMouseMotionListener(mia);
		jPane.addMouseListener(mia);
	}
	
	public Font getFont(){
		return lyricFont;
	}
	
	public void setFont(Font f){
		lyricFont = f;
		
		if(f.isItalic())
			lyricBoldFont = new Font(f.getFontName(), Font.ITALIC | Font.BOLD, f.getSize());
		else
			lyricBoldFont = new Font(f.getFontName(), Font.BOLD, f.getSize());
		
		
		try{
			for(int i=0; i < lyricLabel.length; i++){
				lyricLabel[i].setFont(lyricFont);
			}
		}catch(Exception e){
		}
	}
	
	public void clean(){
		this.getVerticalScrollBar().setValue(0);
		prevTime = -1;
		try{
			for(int i=0; i<lyricLabel.length; i++){
				jPane.remove(lyricLabel[i]);
			}
		}catch(Exception e){
		}
	}
	
	public void setText(String[] str){
		lyricLabel = new JLabel[str.length];
		
		for(int i=0; i < str.length; i++){
			str[i] = str[i].replace("<br>", " ");
			lyricLabel[i] = new JLabel(str[i]);
			lyricLabel[i].setForeground(Color.BLACK);
			lyricLabel[i].setAlignmentX(CENTER_ALIGNMENT);
			lyricLabel[i].setFont(lyricFont);
//			lyricLabel[i].setBorder(BorderFactory.createEtchedBorder());
			lyricLabel[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
			jPane.add(lyricLabel[i]);
//			System.out.println("Asdfasdf");
		}
		
		jPane.setVisible(false);
		jPane.setVisible(true);
	}
	
	public void setTime(String[] time){
		splitTime = new Integer[time.length];
		
		for(int i=0; i < time.length; i++){
			splitTime[i] = Integer.parseInt(time[i]);
		}
			
	}
	
	public void setSyncChanged(){
		prevTime = -1;
	}
	
	public void setCurrentLyric(int time){
		
		if(prevTime == -1){
			prevTime = findSync(time);
		}
		
		
		try{
			if(time <= splitTime[prevTime]){
				return;
			} else {
				if(splitTime[prevTime] == 0){
					prevTime++;
					return;
				}

				prevTime++;
				setCurrentBold(prevTime-1);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			//
		}
		
		
	}
	
	private int findSync(int time){
		int tempIndex = 0;

		try{
			while(time > splitTime[tempIndex]){
				tempIndex++;
			}
		}catch(ArrayIndexOutOfBoundsException e){

		}
		
		
		return tempIndex;
	}
	
	private void setCurrentBold(int index){
		//index 0번째 요소가 아무것도 없기때문이다.(split으로 나누어서그렇다)
		index++;
		
		//전체를 초기화 해준다.
		for(int i=0; i < lyricLabel.length; i++){
			lyricLabel[i].setFont(lyricFont);	
		}
		
		lyricLabel[index].setFont(lyricBoldFont);
		
		setScrollCurrentPos();
//		System.out.println(lyricLabel[index].getText());
				
//		System.out.println(splitTime[index-1] + ", "+ splitTime[index]);
		try{
			if(splitTime[index-1].equals(splitTime[index])){
				lyricLabel[index+1].setFont(lyricBoldFont);
				prevTime++;


				if(splitTime[index-1].equals(splitTime[index+1])){
					lyricLabel[index+2].setFont(lyricBoldFont);
					prevTime++;
				}

			}
		}catch(ArrayIndexOutOfBoundsException e){
			//가사의 마지막줄에 가면 생기는 예외를 처리한다.
		}finally{
			repaint();
		}
	}

	private void setScrollCurrentPos(){

		int value = (int)( ((float)prevTime/(float)lyricLabel.length) * this.getVerticalScrollBar().getMaximum() );
	
		this.getVerticalScrollBar().setValue(value - (int)(this.getHeight()/2) );
	}
	
}
