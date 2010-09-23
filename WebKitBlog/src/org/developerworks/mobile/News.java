package org.developerworks.mobile;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class News {
	private String title;
	private List<Item> entries;
	// constructors, getters/setters omitted for brevity
	public News() {}

	public News(String title, List<Item> entries) {
		this.title = title;
		this.entries = entries;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the entries
	 */
	public List<Item> getEntries() {
		return entries;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<Item> entries) {
		this.entries = entries;
	}
	
}
