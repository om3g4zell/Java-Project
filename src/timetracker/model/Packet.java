package timetracker.model;

import java.io.Serializable;

/**
 * This class represent a packet its goal is to
 * be sent via tcp
 * @author Louis Babuchon
 *
 */
public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The header of the packet
	 */
	private String header;
	
	/**
	 * The content object of the packet
	 */
	private Object content;
	
	/**
	 * The constructor, initiates attributes
	 * @param header : the header of the packet
	 * @param o : the object of the packet
	 */
	public Packet(String header, Object o) {
		this.header = header;
		this.content = o;
	}
	
	/**
	 * The constructor, initiates attributes
	 * call the other constructor and put the content to null
	 * @param header : the header of the packet
	 */
	public Packet(String header) {
		this(header, null);
	}

	/**
	 * Get the header of the packet
	 * @return String : the header of the packet
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Get the object of the packet
	 * @return Object : the content of the packet
	 */
	public Object getObject() {
		return content;
	}
	
}
