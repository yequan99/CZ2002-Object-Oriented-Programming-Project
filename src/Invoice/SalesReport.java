package Invoice;

import java.io.*;
import java.util.*;

/**
 * Represents the Sales Report of the restaurant
 */
public class SalesReport implements Serializable{
	/**
	 * HashMap of Item count
	 */
	private HashMap<String,Integer> Item_count;
	/**
	 * Revenue of the restaurant
	 */
	private double revenue;

	/**
	 * Constructor of SalesReport
	 * @param Revenue Revenue of the restaurant
	 * @param Items_sold Hashmap linking the food items sold and the quantity sold for each item
	 */
	public SalesReport(double Revenue, HashMap<String,Integer> Items_sold){
		this.revenue = Revenue;
		this.Item_count=Items_sold;
	}

	/**
	 * Gets the item count
	 * @return Item count
	 */
	public HashMap<String,Integer> getItemCount(){
		return this.Item_count;
	}

	/**
	 * Gets the revenue of the restaurant
	 * @return Revenue of the restaurant
	 */
	public double getRevenue(){
		return this.revenue;
	}
}