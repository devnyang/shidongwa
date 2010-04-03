package dalsong.playlist;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.AbstractListModel;

public class PlayListModel extends AbstractListModel{
//	LinkedList<Object> list = new LinkedList<Object>();
	ArrayList<Object> list = new ArrayList<Object>();
	
	
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}
	
	
	public int getSize() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	public Object remove(int arg0){
		return list.remove(arg0);
	}
	
	public void add(Object e){
		list.add(e);
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public boolean contains(Object o){
		return list.contains(o);
	}
	
	public void setFireContentsChanged(int index0, int index1){
		fireContentsChanged(this, index0, index1);
	}

}
