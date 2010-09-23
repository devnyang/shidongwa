package org.developerworks.mobile;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Item {
	private String title;
	private String link;
	private String description;
	private Long id;
	// constructors, getters/setters omitted for brevity
	public Item() {}
	public Item(String title, String link, String description, Long id) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.id = id;
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
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
