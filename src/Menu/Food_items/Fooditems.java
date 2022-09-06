package Menu.Food_items;
import java.io.Serializable;

/**
 * Represent Food items
 */
public class Fooditems implements Serializable{
	/**
	 * Price of the food item
	 */
	private double price;
	/**
	 * Name of the food item
	 */
	private String name;
	/**
	 * Boolean value to check if item is a promotional set
	 */
	protected boolean ispromoset;

	/**
	 * Default constructor for food items
	 */
	Fooditems(){
		this.price=0;
		this.name=null;
		this.ispromoset=false;
	}

	/**
	 * Constructor for the food items
	 * 
	 * @param price Price of the food item
	 * @param name Name of the food item
	 */
	Fooditems(double price,String name,boolean ispromoset){
		this.price=price;
		this.name=name;
		this.ispromoset=ispromoset;
	}

	/**
	 * Get the price of the food item
	 * 
	 * @return Price of the food item
	 */
	public double getprice(){
		return this.price;
	}

	/**
	 * Update the price of the food item
	 * 
	 * @param price New price of the food item
	 */
	public void changeprice(double price){
		this.price=price;
	}

	/**
	 * Get the name of the food item
	 * 
	 * @return Name of the food item
	 */
	public String getname(){
		return this.name;
	}

	/**
	 * Update the name of the food item
	 * 
	 * @param name New name of the food item
	 */
	public void changename(String name){
		this.name=name;
	}

	/**
	 * Check if the food item is a promotional set
	 * 
	 * @return True or False on whether is the food item a promo set
	 */
	public boolean ispromoset(){
		return this.ispromoset;
	}
}