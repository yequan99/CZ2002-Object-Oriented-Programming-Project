package Restaurant;
import java.util.*;
import java.io.IOException;
import Reservation.*;
import Menu.*;
import Invoice.*;

/**
 * The restaurant class. Runs the main app itself.
 */
public class Restaurant{
	Scanner sc = new Scanner(System.in);
	/**
	 *Restaurant name*/
	private String Restaurantname;
	/**
	 * HashMap that maps Employee ID to each Staff object {@link Staff}
	 */
	private HashMap<Integer,Staff> Staff_to_id;
	/**
	 * Array of tables in the restaurant {@link Tables}
	 */
	private Tables[] tables;
	/**
	 * Menu Interface to edit the menu or get the menu {@link MenuInterface}
	 */
	private MenuInterface menu;
	/**
	 * Order Interface to edit or change the order {@link OrderInterface}
	 */
	private OrderInterface orders;

	/**
	 * Phone number of the restaurant
	 */
	private int Phonenumber;

	/**
	 * Address of the restaurant
	 */
	private String Address;

	/**
	 * Reservation Interface to edit or change the reservations
	 */
	private ReservationInterface reservationInterface;

	/**
	 * Main function for user to interact with the app
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		Restaurant restaurant= new Restaurant();
		Scanner sc = new Scanner(System.in);
		int choice;
		boolean end = false;
		System.out.println("Select option:");
		do{
			System.out.println("(1) Create/Update/Remove menu item");
			System.out.println("(2) Create/Update/Remove promotion");
			System.out.println("(3) Create order");
			System.out.println("(4) View order");
			System.out.println("(5) Add/Remove order item/s to/from order");
			System.out.println("(6) Create reservation booking");
			System.out.println("(7) Check/Remove reservation booking");
			System.out.println("(8) Check table availability");
			System.out.println("(9) Print order invoice");
			System.out.println("(10) Generate/ Print sale revenue report by period");
			System.out.println("(11) End application");
			choice = sc.nextInt();

			switch(choice){
			case 1:
				restaurant.CreateUpdateRemove_menu_item();
				break;
			case 2:
				restaurant.CreateUpdateRemove_promotion();
				break;
			case 3:
				restaurant.CreateOrder();
				break;
			case 4:
				restaurant.ViewOrder();
				break;
			case 5:
				restaurant.AddremoveitemOrder();
				break;
			case 6:
				restaurant.CreateReservation();
				break;
			case 7:
				restaurant.CheckRemoveReservation();
				break;
			case 8:
				restaurant.CheckTableAvailability();
				break;          
			case 9:
				restaurant.Printorderinvoice();
				break;
			case 10:
				restaurant.Printsalereport();
				break;

			case 11:
				System.out.println("System is terminating...");
				end = true;
				break;
			}
			if (end)
				break;
		} while (choice > 0 || choice < 12);
	}

	/**
	 * Constructor to initiate the restaurant
	 */
	public Restaurant(){
		this.Restaurantname="OOP FOOD";
		this.Phonenumber=94587623;
		this.Address= "\t17 Rockland Lane\n" +
				"\tWaxhaw, NC 28173";

		this.Staff_to_id=new HashMap<Integer,Staff>();
		Staff temp;
		temp=new Staff("John", 'M', 1, "Waiter");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Mary", 'F', 2, "Waiter");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Catherine", 'F', 3, "Waiter");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Bob", 'M', 8, "Chef");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Jack", 'M', 9, "Chef");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Jessie", 'F', 4, "Waiter");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Lily", 'F', 10, "Chef");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Jason", 'M', 5, "Cashier");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Mason", 'M', 6, "Cashier");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);
		temp=new Staff("Larry", 'M', 7, "Waiter");
		this.Staff_to_id.put(temp.getEmployeeID(), temp);

		this.tables = new Tables[20];
		Tables temptable=new Tables(1,2);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(2,2);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(3,4);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(4,4);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(5,6);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(6,6);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(7,8);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(8,8);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(9,10);
		tables[temptable.getTableID()]=temptable;
		temptable=new Tables(10,10);
		tables[temptable.getTableID()]=temptable;

		this.menu=new MenuInterface();
		this.reservationInterface=new ReservationInterface();
		this.orders=new OrderInterface(10,this.Restaurantname,this.Phonenumber,this.Address,this.reservationInterface, this.tables);

	}

	/**
	 * User to interact with the menu and edit accordingly. {@link MenuInterface}
	 */
	public void CreateUpdateRemove_menu_item(){
		while(true) {
			System.out.println("(1) Create menu item");
			System.out.println("(2) Update menu item");
			System.out.println("(3) Remove menu item");
			System.out.println("(4) View menu item");
			System.out.println("(5) Go back to main menu");

			int select = sc.nextInt();
			switch(select) {
			case 1:
				System.out.println("What is the name of the item to be added:");
				sc.nextLine();
				String name=sc.nextLine();
				if (this.menu.add_alacarte_item(name))
					System.out.println("Item added");
				break;
			case 2:
				System.out.println("What is the type of the item to be updated:");
				System.out.println("(1) Main Course");
				System.out.println("(2) Dessert");
				System.out.println("(3) Drink");
				select=sc.nextInt();
				switch(select){
				case 1:
					this.menu.EditMaincourse();
					break;
				case 2:
					this.menu.EditDesserts();
					break;
				case 3:
					this.menu.EditDrinks();;
					break;
				default:
					break;
				}
				break;
			case 3:
				System.out.println("What is the type of the item to be remove:");
				System.out.println("(1) Main Course");
				System.out.println("(2) Dessert");
				System.out.println("(3) Drink");
				int type=sc.nextInt()-1;
				this.menu.removeitem(type);
				break;
			case 4:
				this.menu.printsubmenu(0);
				this.menu.printsubmenu(1);
				this.menu.printsubmenu(2);
				this.menu.printsubmenu(3);
				break;

			case 5:
				return;
			default:
				System.out.println("Invalid input, please re-enter!");

			}
		}
	}

	/**
	 * User to interact with the menu for promotional set and edit accordingly. {@link MenuInterface}
	 */
	public void CreateUpdateRemove_promotion(){
		while (true){
			System.out.println("(1) Create promotional set");
			System.out.println("(2) Update promotional set");
			System.out.println("(3) Remove promotional set");
			System.out.println("(4) View promotional set");
			System.out.println("(5) Go back to main menu");
			int choice=sc.nextInt();
			switch(choice){
			case 1:
				System.out.println("What is the name of the promotional set?");
				sc.nextLine();
				String name =sc.nextLine();
				this.menu.addpromo(name);
				break;
			case 2:
				this.menu.EditPromo();
				break;
			case 3:
				this.menu.removeitem(3);
				break;
			case 4:
				this.menu.printsubmenu(3);
				break;
			case 5:
				return;

			}
		}
	}

	/**
	 * User to create order using the order interface {@link OrderInterface}
	 */
	public void CreateOrder(){
		System.out.println("Who is serving customer?");
		for (int i=1;i<8;i++){
			System.out.println("("+i+") "+this.Staff_to_id.get(i).getName());
		}
		int Employee_id=sc.nextInt();
		this.orders.createorder(Staff_to_id.get(Employee_id));
	}

	/**
	 * User to view order using order interface {@link OrderInterface}
	 */
	public void ViewOrder(){
		System.out.println("Which table number?");
		int Table_num=sc.nextInt();
		this.orders.vieworder(Table_num);
	}

	/**
	 * User to edit order using the order interface {@link OrderInterface}
	 */
	public void AddremoveitemOrder(){
		System.out.println("Which table are you serving?");
		int Table_num=sc.nextInt();
		while(true){
			System.out.println("What do you want to do?");
			System.out.println("(1) Add item into order");
			System.out.println("(2) Remove item from order");
			System.out.println("(3) Back to main menu");
			int choice=sc.nextInt();
			switch(choice){
			case 1:
				this.orders.addtoOrder(Table_num, this.menu.getmenu());
				break;
			case 2:
				this.orders.removefromOrder(Table_num);
				break;
			case 3:
				return;
			}
		}

	}

	/**
	 * User to create a reservation using the reservation interface {@link ReservationInterface}
	 */
	public void CreateReservation(){
		this.reservationInterface.create_Reservations();
	}

	/**
	 * User to check or remove reservations using the reservation interface {@link ReservationInterface}
	 */
	public void CheckRemoveReservation(){
		this.reservationInterface.check_remove_Reservations();
	}

	/**
	 * User to check tables availability for their reservations using the reservation interface {@link ReservationInterface}
	 */
	public void CheckTableAvailability(){
		this.reservationInterface.checkTableAvailability();
	}

	/**
	 * User to Print the order invoice using order interface {@link OrderInterface}
	 */
	public void Printorderinvoice() throws IOException {
		System.out.println("Which table is paying?");
		int Table_num=sc.nextInt();
		this.orders.PayOrder(Table_num);
	}

	/**
	 * User to print sales report through the order interface {@link OrderInterface}
	 */
	public void Printsalereport() throws IOException {

		this.orders.PrintSalesReport();
	}
}