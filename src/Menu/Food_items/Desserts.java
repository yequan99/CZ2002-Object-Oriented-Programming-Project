package Menu.Food_items;

/**
 * Represents the dessert
 */
public class Desserts extends Fooditems{
	/**
	 * Description of the dessert
	 */
	private String d_descript;
	/**
	 * Constructor of dessert
	 * 
	 * @param f Name of the dessert
	 * @param price Price of the dessert
	 * @param description Description of the dessert
	 */
	public Desserts(String f, double price,String description){
		super(price,f,false);
		this.d_descript=description;
	}

	/**
	 * Get the description of the dessert
	 * 
	 * @return Description of the dessert
	 */
	public String getdescript(){
		return this.d_descript;
	}

	/**
	 * Get the name of the dessert
	 * 
	 * @return Name of the dessert
	 */
	public String getname(){
		return super.getname();
	}

	/**
	 * Get the price of the dessert
	 * 
	 * @return Price of the dessert
	 */
	public double getprice(){
		return super.getprice();
	}

	/**
	 * Update the name of the dessert
	 * 
	 * @param n New name of the dessert
	 */
	public void changeName(String n){
		super.changename(n);
	}

	/**
	 * Update the price of the dessert
	 * @param p New price of the dessert
	 */
	public void changePrice(double p){
		super.changeprice(p);
	}

	/**
	 * Update the description of the dessert
	 * 
	 * @param d New description of the dessert
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