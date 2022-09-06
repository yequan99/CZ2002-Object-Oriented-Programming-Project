package Menu.Food_items;

/**
 * Represent the main course
 */
public class Maincourse extends Fooditems{
	/**
	 * Description of the main course
	 */
	private String m_descript;

	/**
	 * Constructor for the main course
	 *
	 * @param f Name of the main course
	 * @param price Price of the main course
	 * @param description Description of the description
	 */
	public Maincourse(String f, double price, String description){
		super(price,f,false);
		this.m_descript=description;
	}

	/**
	 * Get description of main course
	 * 
	 * @return Description of main course
	 */
	public String getdescript(){
		return this.m_descript;
	}

	/**
	 * Get name of main course
	 * @return Name of the main course
	 */
	public String getname(){
		return super.getname();
	}

	/**
	 * Get price of main course
	 * 
	 * @return price of the main course
	 */
	public double getprice(){
		return super.getprice();
	}

	/**
	 * Update the name of the main course
	 * 
	 * @param n Name of the main course
	 */
	public void changeName(String n){
		super.changename(n);
	}

	/**
	 * Update the price of the main course
	 * 
	 * @param p Price of the main course
	 */
	public void changePrice(double p){
		super.changeprice(p);
	}

	/**
	 * Update the description of the main course
	 * 
	 * @param d Description of the main course
	 */
	public void changeDescription(String d){
		this.m_descript = d;
	}
	
	/**
	 * Checks for promotional set
	 * 
	 * @return True or False on whether item is on promotional set
	 */
	public boolean ispromoset(){
		return super.ispromoset;
	}
}