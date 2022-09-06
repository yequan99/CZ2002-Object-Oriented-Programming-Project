package Menu.Food_items;

/**
 * Represents the drink
 */
public class Drinks extends Fooditems{
	/**
	 * Description of the drink
	 */
	private String d_descript;

	/**
	 * Constructor of drink
	 * 
	 * @param f Name of the drink
	 * @param price Price of the drink
	 * @param description Description of the drink
	 */
	public Drinks(String f, double price,String description){
		super(price,f,false);
		this.d_descript=description;
	}

	/**
	 * Get description of the drink
	 * 
	 * @return Description of the drink
	 */
	public String getdescript(){
		return this.d_descript;
	}

	/**
	 * Get name of the drink
	 * 
	 * @return Name of the drink
	 */
	public String getname(){
		return super.getname();
	}

	/**
	 * Get price of the drink
	 * 
	 * @return Price of the drink
	 */
	public double getprice(){
		return super.getprice();
	}

	/**
	 * Update the name of the drink
	 * 
	 * @param n New name of the drink
	 */
	public void changeName(String n){
		super.changename(n);
	}

	/**
	 * Update the price of the drink
	 * 
	 * @param p New price of the drink
	 */
	public void changePrice(double p){
		super.changeprice(p);
	}

	/**
	 * Update the description of the dirnk
	 * 
	 * @param d New description of the drink
	 */
	public void changeDescription(String d){
		this.d_descript = d;
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