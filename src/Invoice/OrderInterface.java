package Invoice;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import Menu.*;
import Menu.Food_items.*;
import Reservation.ReservationInterface;
import Restaurant.*;
import java.io.*;

import Reservation.Customer;

/**
 * The boundary class that will interact with the user input and do various functions for the order.
 */
public class OrderInterface{
	/**
	 * Array of Tables from the restaurant
	 */
	private Tables[] tables;

	/**
	 * Used to check the reservation before creating an order
	 */
	private ReservationInterface resinterface;
	/**
	 * HashMap to map the Order to a Table
	 */
	private HashMap<Integer,OrderController> Order_to_table;
	/**
	 * Initialising an invoice
	 */
	private Invoice invoicetoprint;
	/**
	 * Initialising a SaleReportController
	 */
	private SaleReportController salereport;
	/**
	 * Time of payment
	 */
	private int TimeofPayment; 
	/**
	 * Instantiating Scanner class
	 */
	Scanner sc = new Scanner(System.in);
	/**
	 * Invoice ID
	 */
	private long Invoice_ID;
	/**
	 * Restaurant name
	 */
	private String Restaurantname;
	/**
	 * Restaurant address
	 */
	private String Restaurantaddress;
	/**
	 * Restaurant phone number
	 */
	private String Res_PhoneNumber;
	/**
	 * Initialise InvoiceToTxt
	 */
	private InvoiceToTxt iTT;

	/**
	 * Constructor of OrderInterface
	 *
	 * @param Numoftables Number of tables
	 * @param Restaurantname Name of the restaurant
	 * @param phone Phone number of the restaurant
	 * @param address Address of the restaurant
	 * @param resinterface Reservation Interface from restaurant {@link ReservationInterface}
	 * @param tables Array of tables from Restaurant {@link Tables}
	 */
	public OrderInterface(int Numoftables, String Restaurantname, Integer phone, String address, ReservationInterface resinterface,Tables[] tables){
		this.tables=tables;
		this.resinterface=resinterface;
		this.Order_to_table=new HashMap<Integer,OrderController>();
		this.salereport=new SaleReportController();
		this.Invoice_ID=0;
		this.Restaurantname= Restaurantname;
		this.Restaurantaddress=address;
		this.Res_PhoneNumber=phone.toString();
	}

	/**
	 * Get the restaurant phone number
	 * @return Phone number of the restaurant
	 */
	public String getRes_PhoneNumber() {
		return Res_PhoneNumber;
	}

	/**
	 * Gets the address of the restaurant
	 * @return Address of the restaurant
	 */
	public String getRestaurantaddress() {
		return Restaurantaddress;
	}

	/**
	 * Gets the name of the restaurant
	 * @return Name of the restaurant
	 */
	public String GetRestaurantname(){
		return this.Restaurantname;
	}

	/**
	 * Creating an order
	 * @param Name Name of the Staff that created the order
	 */
	public void createorder(Staff Name){
		int Table_num;
		LocalDateTime currenttime=LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		String str_date=currenttime.format(dateFormat);
		int YYMMDD=(Integer.parseInt(str_date))%1000000;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
		String str_time=currenttime.format(timeFormat);
		int time=Integer.parseInt(str_time);
		while(true){
			System.out.println("What table is the customer seated at?");
			Table_num=sc.nextInt();
			if (resinterface.checkTableAvailabilitycurrent(Table_num,YYMMDD,time)&&this.tables[Table_num].getAvail()){ 
				break;
			}
			System.out.println("Table is occupied!");
			System.out.println("Do you still want to create order? Y/N");
			String choice=sc.next();
			if (choice.equals("N")){
				System.out.println("Order not created.");
				return;
			}
			else{
				break;
			}
		}
		OrderController order=new OrderController(Table_num, Name);
		this.Order_to_table.put(Table_num, order);
		this.tables[Table_num].setAvail(false);
	}

	/**
	 * Adds food items to the order
	 * @param Table_num Table number from where the order belongs to
	 * @param menu Menu
	 */
	public void addtoOrder(int Table_num,MenuController menu){
		int choice = 0;
		if(Order_to_table.get(Table_num) == null) {
			System.out.println("Error, create Order First!");
			return;
		}
		while(choice!=5){
			while(choice < 1 || choice > 5){
				System.out.println("What course would you like to add to order:");
				System.out.println("(1) Main Course");
				System.out.println("(2) Desserts");
				System.out.println("(3) Drinks");
				System.out.println("(4) Promotional Sets");
				System.out.println("(5) Do something else");
				choice = sc.nextInt();
			}
			if (choice != 5){
				switch(choice){
				case 1:
					menu.printsubmenu(0);
					System.out.println("Which main course to add to order?");
					//System.out.println(menu.getmainlist().size()); //debug
					int main = sc.nextInt();
					if(main <= menu.getmainlist().size() && main >= 1) {
						Maincourse MainFood=menu.getmainlist().get(main-1);
						System.out.println("How many would you like to order?");
						int number_main=sc.nextInt();
						this.Order_to_table.get(Table_num).addtoOrder(MainFood,number_main);
					}
					else {
						System.out.println("Invalid item number entered!");
					}
					break;
				case 2:
					menu.printsubmenu(1);
					System.out.println("Which dessert to add to order?");
					int dessert = sc.nextInt();
					if(dessert <= menu.getdessertlist().size() && dessert >= 1) {
						Desserts DessertFood=menu.getdessertlist().get(dessert-1);
						System.out.println("How many would you like to order?");
						int number_dessert=sc.nextInt();
						this.Order_to_table.get(Table_num).addtoOrder(DessertFood,number_dessert);
					}
					else {
						System.out.println("Invalid item number entered!");
					}
					break;
				case 3:
					menu.printsubmenu(2);
					System.out.println("Which drink to add to order?");
					int drink = sc.nextInt();
					if(drink <= menu.getdrinklist().size() && drink >= 1) {
						Drinks DrinkFood=menu.getdrinklist().get(drink-1);
						System.out.println("How many would you like to order?");
						int number_drink=sc.nextInt();
						this.Order_to_table.get(Table_num).addtoOrder(DrinkFood,number_drink);
					}
					else {
						System.out.println("Invalid item number entered!");
					}
					break;
				case 4:
					menu.printsubmenu(3);
					System.out.println("Which promotional set to add to order?");
					int promo = sc.nextInt();
					if(promo <= menu.getpromolist().size() && promo >= 1) {
						PromotionalSets PromoFood=menu.getpromolist().get(promo-1);
						System.out.println("How many would you like to order?");
						int number_promo=sc.nextInt();
						this.Order_to_table.get(Table_num).addtoOrder(PromoFood,number_promo);
					}
					else {
						System.out.println("Invalid item number entered!");
					}
					break;
				}
				break;
			}
		}
	}

	/**
	 * Removes food items from the order created
	 * @param Table_num Table number that the order belongs to
	 */  
	public void removefromOrder(int Table_num){
		this.Order_to_table.get(Table_num).printallitems();
		System.out.println("What do you want to remove?");
		int choice = sc.nextInt();
		int i=0;
		Fooditems itemname= null;
		for (Fooditems name: this.Order_to_table.get(Table_num).getOrderList().keySet()) {
			itemname = name;
			i++;
			if(i==choice){
				break;
			}
		}
		if(itemname==null){
			System.out.println("Invalid index");
			return;
		}
		int value=this.Order_to_table.get(Table_num).getOrderList().get(itemname);
		System.out.println("How many do you want to remove? Max amount to remove: "+value);
		int removenumberofitems = sc.nextInt();
		this.Order_to_table.get(Table_num).removefromOrder(itemname,removenumberofitems,value);
	}

	/**
	 * View the details of the order being created
	 * @param Table_num Table Number that the order belongs to
	 */
	public void vieworder(int Table_num){
		this.Order_to_table.get(Table_num).printallitems();
	}

	/**
	 * Payment is made and the invoice is being printed. Table will become unoccupied
	 * @param Table_num Table Number that the invoice belongs to
	 * @throws IOException Inability to print the invoice
	 */
	public void PayOrder(int Table_num) throws IOException {

		if(tables[Table_num].getAvail()==true){
			System.out.println("No customer at this table");
			return;
		}
		OrderController order=this.Order_to_table.get(Table_num);
		Customer customer=new Customer();
		boolean ismember;
		while(true){
			System.out.println("Is customer Member? Y/N");
			String choice=sc.next();
			if(choice.equals("Y")){
				int phonenumber;
				System.out.println("Customer phone number: ");
				phonenumber=sc.nextInt();
				ismember=customer.CheckMembership(phonenumber);
				if(!ismember){
					System.out.println("Wrong phone number.");
					continue;
				}
				break;
			}
			else if(choice.equals("N")){
				ismember=false;
				break;
			}
		}
		this.invoicetoprint= new Invoice(order,this.Restaurantname,this.Restaurantaddress ,this.Res_PhoneNumber, ismember);
		this.invoicetoprint.PrintInvoice();
		System.out.println("Paid? Y/N");
		String choice=sc.next();
		if(choice.equals("Y")){
			this.tables[Table_num].setAvail(true);
			this.Order_to_table.replace(Table_num,null);
			//To change to function that gives text
			iTT = new InvoiceToTxt(this.invoicetoprint); 
			iTT.CreateInvoice(); //print to Object.txt
			iTT.PrintReceipt(this.invoicetoprint); //print to Readable.txt file
			return;
		}
		else if (choice.equals("N")){
			return;
		}
		else{
			System.out.println("Failed to print invoice, please try again.");
		}
		return;
	}

	/**
	 * Printing of the sales report
	 * @throws IOException Unable to print the sales report
	 */
	public void PrintSalesReport() throws IOException {
		LocalDateTime startDateLocalDateTime = null;
		LocalDateTime endDateLocalDateTime = null;
		String startDate;
		String endDate;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		System.out.println("Print SalesReport by day or period?\n1)Day\n2)period");
		char choice =sc.next().charAt(0);
		switch(choice){
		case '1':
			System.out.println("What is the date for SalesReport? Please follow this format 'yyyy-MM-dd'");
			startDate=sc.next();
			try {
				startDateLocalDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
				if (!startDate.equals(formatter.format(startDateLocalDateTime))) {
					startDateLocalDateTime = null;
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format!");
			}
			if (startDateLocalDateTime == null) {
				return;
			}
			SalesReport newReportDay = salereport.ReadSalesReportDay(startDate); //reads salesReport Object from specified start date
			if(newReportDay != null) {
				salereport.PrintSalesReportDay(newReportDay, startDateLocalDateTime);
			}
			else {
				System.out.println("Could not find SalesReport Dated: " + startDate);
			}
			break;

		case '2':
			System.out.println("What is the start date for SalesReport? Please follow this format 'yyyy-MM-dd' ");
			startDate =sc.next();
			try {
				startDateLocalDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
				if (!startDate.equals(formatter.format(startDateLocalDateTime))) {
					startDateLocalDateTime = null;
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid Start date format!");
			}
			if (startDateLocalDateTime == null) {
				return;
			}
			System.out.println("What is the end date for SalesReport? Please follow this format 'yyyy-MM-dd'");
			endDate = sc.next();
			try {
				endDateLocalDateTime = LocalDate.parse(endDate, formatter).atStartOfDay();
				if (!endDate.equals(formatter.format(endDateLocalDateTime))) {
					endDateLocalDateTime = null;
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid End date format!");
			}
			if (endDateLocalDateTime == null) {
				return;
			}
			SalesReport newReport = salereport.SalesReportPeriod(startDateLocalDateTime,endDateLocalDateTime);
			salereport.PrintSalesReportPeriod(newReport);

			break;
		default:
			System.out.println("Invalid input");
		}
	}
}