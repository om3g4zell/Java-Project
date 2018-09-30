package models;

/**
 * This is the Boss's class
 * @author Louis Babuchon
 *
 */
public class Boss extends Person {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The boss is a singleton then we instanciate him here
	 */
	private static Boss boss = new Boss("Louis", "Babuchon");
	
	/**
	 * The constructor initiate the attributes
	 * @param firstName : the first name of the boss
	 * @param lastName : the last name of the boss
	 */
	private Boss(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	/**
	 * Singleton Getter
	 * @return Boss : the Boss Singleton
	 */
	public static Boss getInstance() {
		return boss;
	}
}
