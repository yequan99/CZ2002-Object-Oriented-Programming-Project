package Invoice;

import java.util.*;
import Menu.Food_items.Fooditems;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller class for orders that will deal with the logic of an order. Represents an order.
 */
public class Invoice implements Serializable{
	/**
	 * Table number for the order
	 */
	private int table_num;
	/**
	 * Time of the order
	 */
	private LocalDateTime time;
	//private long Invoice_ID;
	/**
	 * The staff that is serving the order
	 */
	private String Server;
	/**
	 * The subtotal price of the order before GST and Service Charge
	 */
	private double subtotal_price;
	/**
	 * The total price of the order before GST and Service Charge
	 */
	private double total_price;
	/**
	 * The Service Charge rate
	 */
	private double serviceCharge =1.1;
	/**
	 * The GST rate
	 */
	private double gstTax = 1.07;
	/**
	 * The name of restaurant
	 */
	private String Restauarantname;
	/**
	 * The address of restaurant
	 */
	private String address;
	/**
	 * The phone number of restaurant
	 */
	private String phoneNumber;
	/**
	 * Hashmap of the Food items mapped to how many of the Food items are ordered
	 */
	HashMap<Fooditems,Integer> final_items;
	/**
	 * Membership of customers
	 */
	private boolean hasMembership = false;
	/**
	 * Membership Discount
	 */
	private double membershipDiscount = 0;

	/**
	 * Constructor of Invoice
	 * @param orders Order
	 * @param RestaurantName Name of restaurant
	 * @param Address Address of restaurant
	 * @param PhoneNumber Phone number of the restaurant
	 * @param HasMembership True or False value on whether customer is a member
	 */
	public Invoice(OrderController orders,String RestaurantName,String Address ,String PhoneNumber, boolean HasMembership){
		this.table_num= orders.getTableNum();
		this.Server=orders.getStaff();
		this.final_items=orders.getOrderList();
		this.time = orders.gettime();
		this.Restauarantname=RestaurantName;
		this.address=Address;
		this.phoneNumber=PhoneNumber;
		this.hasMembership = HasMembership;
		this.subtotal_price= orders.getprice();
		if(hasMembership){
			this.membershipDiscount = 0.1*subtotal_price;
			this.subtotal_price -= this.membershipDiscount;
		}
		this.serviceCharge=0.1*subtotal_price;
		this.gstTax=0.07*subtotal_price;
		this.total_price = subtotal_price + gstTax + serviceCharge;
	}

	/**
	 * Check if customer is a member of the restaurant
	 * @return True or False
	 */
	public boolean getMembership(){
		return hasMembership;
	}

	/**
	 * Gets the membership discount
	 * @return Membership Discount
	 */
	public double getMembershipDiscount(){
		return membershipDiscount;
	}

	/**
	 * Gets the address of the restaurant
	 * @return Address of the restaurant
	 */
	public String getAddress(){
		return address;
	}

	/**
	 * Gets the phone number of the restaurant
	 * @return Phone number of the restaurant
	 */
	public String getPhoneNumber(){
		return phoneNumber;
	}

	/**
	 * Gets the name of the staff that served
	 * @return Name of staff
	 */
	public String getServer(){
		return Server;
	}

	/**
	 * Gets the Table number
	 * @return Table number
	 */
	public String getTableNum(){
		return String.valueOf(this.table_num);
	}

	/**
	 * Gets the subtotal price
	 * @return Subtotal price
	 */
	public String getSubtotal(){
		return String.format("%.2f",this.subtotal_price);
	}

	/**
	 * gets the GST
	 * @return GST
	 */
	public String getGST(){
		return String.format("%.2f",this.gstTax);
	}

	/**
	 * Gets the Service Charge
	 * @return Service charge
	 */
	public double getServiceCharge(){
		return this.serviceCharge;
	}

	/**
	 * Gets the name of the restaurant
	 * @return Restaurant name
	 */
	public String getRestaurantName(){
		return this.Restauarantname;
	}

	/**
	 * Gets the total price of the meal
	 * @return Total price
	 */
	public double getTotalPrice(){
		return this.total_price;
	}

	/**
	 * Gets the date of the invoice
	 * @return Date of the invoice
	 */
	public String getDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return time.format(formatter);
	}
	
	/**
	 * Get the ordered items
	 * @return Hashmap of ordered items
	 */
	public HashMap<Fooditems,Integer> getorder_items(){
		return final_items;
	}

	/**
	 * Printing the invoice
	 */
	public void PrintInvoice(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String dateReceipt= time.format(formatter);
		String itemHeader;
		String gst = String.format("%.2f", gstTax);
		String subTotal = String.format("%.2f",subtotal_price);
		String serviceChargeStr =  String.format("%.2f",serviceCharge);
		String totalCost = String.format("%.2f",total_price);
		//
		System.out.println(String.format("\t\t"+ Restauarantname + "\n"));
		System.out.println("****************************************\n");
		System.out.println(address);
		System.out.println("\tPhone: " + phoneNumber +"\n");
		System.out.println("Server: " + Server);
		System.out.println("Table: " + table_num);
		System.out.println("Date: " + dateReceipt+"\n");
		System.out.println("----------------------------------------");
		itemHeader = String.format("%-21s %5s %10s\n", "Item", "Qty", "Price");
		System.out.println(itemHeader);
		final_items.forEach(
				(Fooditems,v) -> System.out.println(String.format("%-21s %5s %10s\n",Fooditems.getname() , v, Fooditems.getprice()) )
				);
		if(this.hasMembership){
			itemHeader = String.format("%28s %8s\n", "Membership Discount:", membershipDiscount);
			System.out.println(itemHeader);
		}
		System.out.println("");
		System.out.println("----------------------------------------");
		itemHeader = String.format("%28s %8s\n", "Subtotal:", subTotal);
		System.out.println(itemHeader);
		itemHeader = String.format("%28s %8s\n", "Service Charge:", serviceChargeStr);
		System.out.println(itemHeader);
		itemHeader = String.format("%28s %8s\n", "GST:", gst);
		System.out.println(itemHeader);
		System.out.println("----------------------------------------");
		itemHeader = String.format("%28s %8s\n", "Total Cost:", totalCost);
		System.out.println(itemHeader);
		System.out.println("========================================\n");
		System.out.println("****************************************");
		System.out.println("**** Thank you for dining with us! *****");
		System.out.println("****************************************");
	}

	/**
	 * Get the time of the invoice
	 * @return Time of the invoice
	 */
	public LocalDateTime getTime(){
		return this.time;
	}
}