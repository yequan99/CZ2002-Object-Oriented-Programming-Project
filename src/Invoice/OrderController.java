package Invoice;

import java.util.*;
import java.time.LocalDateTime;
import Menu.Food_items.*;
import Restaurant.*;

/**
 * Controller class for orders that will deal with the logic of an order. Represents an order.
 */
public class OrderController{

	/**
	 * Table number for the order
	 */
	private int table_num;
	/**
	 * Time of the order
	 */
	private LocalDateTime time;
	/**
	 * The staff that is serving the order
	 */
	private Staff Servedby;
	/**
	 * The total price of the order before GST and Service Charge
	 */
	private double total_price;//total price before gst and discount and service charge
	/**
	 * Hashmap of the Food items mapped to how many of the Food items are ordered
	 */
	private HashMap<Fooditems,Integer> order_items;//order items
	Scanner sc =new Scanner(System.in);


	/**
	 * Constructor for order
	 * 
	 * @param Table_num Table number of the order
	 * @param Name The Staff serving the order {@link Staff}
	 */
	public OrderController(int Table_num, Staff Name){
		this.table_num = Table_num;
		this.Servedby = Name;
		this.time=LocalDateTime.now();
		this.order_items = new HashMap<Fooditems, Integer>();
		this.total_price=0;
	}

	/**
	 * Get Table number of the order
	 * 
	 * @return Table number of the order
	 */
	public int getTableNum(){
		return this.table_num;
	}

	/**
	 * Get the Name of the Staff serving the order
	 * 
	 * @return Name of the staff
	 */
	public String getStaff(){
		return this.Servedby.getName();
	}

	/**
	 * Get the total price of the order excluding GST and service charge
	 * 
	 * @return Total price of the order excluding GST and service charge
	 */
	public double getprice(){
		return this.total_price;
	}

	/**
	 * Get the time of the order
	 * 
	 * @return Datetime of the order 
	 */
	public LocalDateTime gettime(){
		return this.time;
	}
	// change time to integer to sort using treemap

	/**
	 * Get Hashmap of the Food items mapped to how many of the Food items are ordered
	 *
	 * @return Hashmap of the Food items mapped to how many of the Food items are ordered
	 */
	public HashMap<Fooditems, Integer> getOrderList(){
		return this.order_items;
	}

	/**
	 * Add a food item to an order
	 * 
	 * @param Food The food item that is supposed to be added
	 * @param number Quantity of the food item to be added
	 */
	public void addtoOrder(Fooditems Food,int number){

		if (this.order_items.containsKey(Food)){
			int count=this.order_items.get(Food) + number;
			this.order_items.replace(Food, count);
		}
		else{
			this.order_items.put(Food, number);
		}

		this.total_price+=Food.getprice()*number;
	}

	/**
	 * Print all the items in the order
	 */
	public void printallitems() {
		int i=1;
		for (Fooditems name: this.order_items.keySet()) {
			String itemname = name.getname();
			int value = order_items.get(name);
			System.out.println("("+i+") "+itemname + ": " + value);
			i++;
		}
	}

	/**
	 * Remove an item from the order
	 * 
	 * @param itemname Food item that should be removed
	 * @param removenumberofitems Quantity of the food items to be removed
	 * @param value Total Quantity of the Food item in the order
	 */
	public void removefromOrder(Fooditems itemname, int removenumberofitems, int value){
		if (value<removenumberofitems){
			System.out.println("Error!");
			return;
		}
		if (value==removenumberofitems){
			this.order_items.remove(itemname);
			this.total_price-=itemname.getprice()*removenumberofitems;
		}
		else{
			this.order_items.replace(itemname,value-removenumberofitems);
			this.total_price-=itemname.getprice()*removenumberofitems;
		}
		System.out.println("Removed successfully!");
		return;
	}
}