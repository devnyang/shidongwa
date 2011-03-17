package dalsong.playlist;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.iharder.dnd.FileDrop;
import dalsong.player.DalSongPlayer;

public class PlayListManager extends JDialog implements ListSelectionListener {

	private ImageIcon imgAdd = new ImageIcon("./res/add.png");
	private ImageIcon imgDel = new ImageIcon("./res/del.png");
	private ImageIcon imgShuff = new ImageIcon("./res/shuffle.png");
	
	private static final long serialVersionUID = 1L;
	private DalSongPlayer owner = null;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JList jList = null;
	private JButton delButton = null;
	private JLabel fileNumberLabel = new JLabel("0 files ");
	private JPopupMenu jPopupMenu = null;
//	private DefaultListModel listModel;
	private PlayListModel listModel;
	
	private JFileChooser fileDlg = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("달쏭 파일(*.mp3) 또는  폴더", "mp3");
	
	private int currentSongIndex;
	/**
	 * @param owner
	 */
	public PlayListManager(DalSongPlayer owner) {
		super(owner);
		this.owner = owner;

		initialize();
		
		
		//set icon
		Image img = Toolkit.getDefaultToolkit().getImage("./res/moon.png");
		setIconImage( img );
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
//		this.setLocationRelativeTo(owner);
		this.setSize(300, 400);
//		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Point parentLocation = owner.getLocationOnScreen();

		Dimension frm = this.getSize();
		
		int xpos = (int) (parentLocation.x - frm.getWidth());
		int ypos = parentLocation.y;
		this.setLocation(xpos,ypos);
		
		initJPopupMenu();
		
		loadDefaultList();
	}

	public void refreashFileChooserDlg(){
		fileDlg = new JFileChooser();
	}
	
	private void loadDefaultList(){
		FileReader fr = null;
		BufferedReader br = null;
		
		
		try {
			fr = new FileReader( new File("default.lst") );
			br = new BufferedReader(fr);
			
		} catch(FileNotFoundException e) {
			e.printStackTrace(System.err);
			return;
//			System.exit(1);
		}
		
		try{
			int listSize = Integer.parseInt(br.readLine());
			
			for(int i=0; i < listSize; i++){
				listModel.add(new Mp3(new File(br.readLine())));
			}
			
			fileNumberLabel.setText(totalFileNumber() + " files ");
			
			fr.close();
			br.close();
		} catch(Exception e){
			e.printStackTrace(System.err);
		}
	}
	
	private int totalFileNumber(){
		return getJList().getModel().getSize();
	}
	
	public void saveList(){
		 try {
		      PrintStream out = new PrintStream(new FileOutputStream("default.lst"));
		      
		      
		      out.println(totalFileNumber());
		      
		      for(int i=0; i < getJList().getModel().getSize(); i++){
		    	  out.println( 	((Mp3)getJList().getModel().getElementAt(i)).getFile().getPath() );
		    	  listModel.setFireContentsChanged(0, listModel.getSize());
		    	  fileNumberLabel.setText(totalFileNumber() + " files ");
		      }
		      
		      out.flush();
		      out.close();

		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    }
		    
	
	}
	
	
	public void showThisList(){
		Point parentLocation = owner.getLocationOnScreen();
		int parentCenter_X = parentLocation.x + owner.getSize().width /2;

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = this.getSize();
		
		int screenCenter_X = screen.width /2;
		
		int xpos;
		
		if(screenCenter_X > parentCenter_X){
			xpos = parentLocation.x + owner.getWidth();	
		} else {
			xpos = (int) (parentLocation.x - frm.getWidth());
		}
		
					
		int ypos = parentLocation.y;
		this.setLocation(xpos,ypos);
		
		if(!isVisible())
			this.setVisible(true);
		
		owner.setVisible(true);
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
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel.setLayout(new BoxLayout(jPanel,
                    BoxLayout.LINE_AXIS));
			jPanel.setBorder(BorderFactory.createEtchedBorder());
			
			
			JButton addButton = new JButton("add");
			addButton.setIcon(imgAdd);
			addButton.setToolTipText("재생 파일 추가");
			addButton.setMargin(new Insets(1, 3, 1, 5));
		
	        AddListener hireListener = new AddListener(addButton);
	        addButton.setActionCommand("add");
	        addButton.addActionListener(hireListener);
	        addButton.setEnabled(true);

	        delButton = new JButton("del");
	        delButton.setIcon(imgDel);
	        delButton.setToolTipText("선택된 파일 삭제");
	        delButton.setMargin(new Insets(1, 3, 1, 5));
	        delButton.setActionCommand("del");
	        delButton.addActionListener(new DelListener());

	        JButton shuffButton = new JButton("shuffle");
	        shuffButton.setIcon(imgShuff);
	        shuffButton.setToolTipText("무작위 섞기");
	        shuffButton.setMargin(new Insets(1, 3, 1, 5));
	        shuffButton.setActionCommand("shuffle");
	        shuffButton.addActionListener(new ShuffleListener());
				        
			jPanel.add(addButton);
			jPanel.add(Box.createHorizontalStrut(5));
			jPanel.add(delButton);
			jPanel.add(Box.createHorizontalStrut(5));
			jPanel.add(new JSeparator(SwingConstants.VERTICAL));
			jPanel.add(fileNumberLabel);
			jPanel.add(shuffButton);
			
			
		}
		return jPanel;
	}

	private JScrollPane getJScrollPane(){
		JScrollPane jsp = new JScrollPane(getJList());

		//드래그앤 드롭을 지원하기 위한 리스너 등록
		new FileDrop( jsp, new FileDrop.Listener()
		{
			public void filesDropped( File[] files )
			{
				insertFiles(files);
				listModel.setFireContentsChanged(0, listModel.getSize());
				fileNumberLabel.setText(totalFileNumber() + " files ");
			}
		});
		
		
		return jsp;
	}
	
	//재귀적으로 디렉토리 구조를 탐색하여 서브디렉토리의 파일까지 다 가져온다.
	private void insertFiles(File[] files){
		for (int b=0; b < files.length; b++) // For Each Selection
		{
			if ( files[b].isDirectory() ) // If User Selected a Directory
			{
				File[] folderFiles = files[b].listFiles(); // Get contents of folder
				insertFiles(folderFiles);

			}
			else if ( files[b].isFile() && files[b].getName().toLowerCase().endsWith(".mp3")) // If User Selected File(s)
			{
				listModel.add(new Mp3(files[b]));
			}
			
		} 				

	}
	
	
	
	
	
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
//			listModel = new DefaultListModel();
			listModel = new PlayListModel();
	        jList = new JList(listModel);
//	        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        jList.setFont(new Font("굴림", Font.LAYOUT_LEFT_TO_RIGHT, 11));
	        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        jList.addListSelectionListener(this);
	        	        	        
	        MouseListener mouseListener = new MouseAdapter() {
	        	public void mouseClicked(MouseEvent mouseEvent) {
	        		JList theList = (JList) mouseEvent.getSource();
	        		if(SwingUtilities.isLeftMouseButton(mouseEvent)){ 
	        			if (mouseEvent.getClickCount() == 2) {
	        				int index = theList.locationToIndex(mouseEvent.getPoint());
	        				if (index >= 0) {
	        					Mp3 o = (Mp3)theList.getModel().getElementAt(index);
//	        					System.out.println("Double-clicked on: " + o.getPath());
	        					owner.startMedia(o.getFile());
	        					currentSongIndex = theList.locationToIndex(mouseEvent.getPoint());
	        				}
	        			}
	        		} else if(SwingUtilities.isRightMouseButton(mouseEvent)){
	        			//int index = theList.locationToIndex(mouseEvent.getPoint());
	        			//theList.setSelectedIndex(index);
	        			jPopupMenu.show(theList, mouseEvent.getX(), mouseEvent.getY());
	        		} 
	        	}
	        };
	        jList.addMouseListener(mouseListener);
		}
		return jList;
	}
	
	public File getPrevSongFile(){
		currentSongIndex--;
		if(currentSongIndex >= getJList().getModel().getSize() || currentSongIndex < 0){
			currentSongIndex = 0;
		}
		getJList().setSelectedIndex(currentSongIndex);
		return ((Mp3)getJList().getModel().getElementAt(currentSongIndex)).getFile();	
	}
	
	public File getNextSongFile(){
		currentSongIndex++;
		if(currentSongIndex >= getJList().getModel().getSize() || currentSongIndex < 0){
			currentSongIndex = 0;
		}
		getJList().setSelectedIndex(currentSongIndex);
		return ((Mp3)getJList().getModel().getElementAt(currentSongIndex)).getFile();
		
	}
	
	public void initJPopupMenu() {

		jPopupMenu = new JPopupMenu();
		jPopupMenu.setSize(new Dimension(170, 183));

		JMenuItem m = new JMenuItem(DalSongPlayer.POPUP_MENU_DELETE); 
		m.addActionListener(new DelListener());
		jPopupMenu.add(m); 
		jPopupMenu.addSeparator();
		m = new JMenuItem(DalSongPlayer.POPUP_MENU_INFORMATION);
		m.addActionListener(new InfoListener());
		jPopupMenu.add(m);
	}
	
	public void insertItem(File f){
		int index = listModel.getSize();
		listModel.add(new Mp3(f));	
		listModel.setFireContentsChanged(index, index+1);
		fileNumberLabel.setText(totalFileNumber() + " files ");
	}

	class InfoListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			Mp3 temp = (Mp3) listModel.getElementAt(jList.getSelectedIndex());
			new SongInfoDialog(temp.getFile(), owner);
		}
		
	}
	
	class ShuffleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Vector<Mp3> v = new Vector<Mp3>();
        	Random r = new Random();
            int size;
        	
            while(!listModel.isEmpty()){
            	size = listModel.getSize();
            	            	
            	v.add((Mp3)listModel.remove(r.nextInt(size)));
            }
            
            while(!v.isEmpty()){
            	listModel.add(v.remove(0));
            }
            
            listModel.setFireContentsChanged(0, listModel.getSize());
            fileNumberLabel.setText(totalFileNumber() + " files ");
        }
    }

    
    class DelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
        	int i;
        	int index[] = jList.getSelectedIndices();
        	int index0 = index[0];
        	
        	for(i=index.length-1; i>=0 ; i--){
        		listModel.remove(index[i]);
        	}

    		int size = listModel.getSize();
    		
//        	if (size == 0) { //Nobody's left, disable firing.
//    			delButton.setEnabled(false);
//    		} 
        	if(index0 == size)
        		index0--;
        		
        	getJList().setSelectedIndex(index0);
        	
        	listModel.setFireContentsChanged(0, listModel.getSize());
        	fileNumberLabel.setText(totalFileNumber() + " files ");
        }
    }
    
    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

    	private void openMediaFile(){
    		fileDlg.setFileFilter(filter);
    		fileDlg.setMultiSelectionEnabled(true);
    		fileDlg.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );

    		int returnVal = fileDlg.showOpenDialog(null);

    		if (returnVal == JFileChooser.APPROVE_OPTION) //If Button Pressed
    		{
    			File files[] = fileDlg.getSelectedFiles(); // Get Users Selections
    			insertFiles(files); 
    			listModel.setFireContentsChanged(0, listModel.getSize());
    			fileNumberLabel.setText(totalFileNumber() + " files ");

    		} 

    	}

    	//Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
//            String name = employeeName.getText();
        	
        	openMediaFile();

        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
//            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
//            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
//            if (!handleEmptyTextField(e)) {
//                enableButton();
//            }
      }

        private void enableButton() {
//            if (!alreadyEnabled) {
//                button.setEnabled(true);
//            }
        }

    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
    /*	
//        if (e.getValueIsAdjusting() == false) {

//            if (jList.getSelectedIndex() == -1) {
    	System.out.println("test : " + totalFileNumber());
            if (totalFileNumber() < 1) {
            //No selection, disable fire button.
                delButton.setEnabled(false);

            } else {
            //Selection, enable the fire button.
                delButton.setEnabled(true);
            }
//        }
     */
    }

}

//리스트에 삽입되어질 클래스
class Mp3{
	private File f;

	public Mp3(File mp3File) {
		f = mp3File;
	}

	public String getName() {
		return f.getName();
	}
	
	public String getPath(){
		return f.getPath();
	}
	public String toString(){
		return f.getName();
	}
	
	public File getFile(){
		return f;
	}
	
}