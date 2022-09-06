package Reservation;
import java.util.Scanner;

/**
 * The boundary class that will interact with the user inputs and  do various functions for the menu
 */
public class ReservationInterface {
	/**
	 * Reservation Controller
	 */
	private ReservationController res_control;
	/**
	 * Constructing a reservation list
	 */
	Reservation_list res_list = new Reservation_list();

	/**
	 * Constructor for the interface
	 */
	public ReservationInterface() {
		this.res_control = new ReservationController();
	}

	/**
	 * Creating reservations 
	 */
	public void create_Reservations() {
		int table_id, date, time, pax;
		res_control.updateReservation(res_list);
		Scanner sc = new Scanner(System.in);
		System.out.println("Name:");
		String name = sc.nextLine();
		System.out.println("Phone Number:");

		int num = sc.nextInt();
		System.out.println("Number of pax:");
		do{
			pax = sc.nextInt();
			if (pax < 1 || pax > 10) {
				System.out.println("Capacity allowed is only from 1-10!");
				System.out.println("Number of pax:");
			}
		}while (pax < 1 || pax > 10);
		System.out.println("Which date to book (YYMMDD):");
		do {
			date = sc.nextInt();
			if (((int)Math.log10(date)+1) != 6) {
				System.out.println("Input a correct date format!");
				System.out.println("Which date to book (YYMMDD):");
			}
		}while(((int)Math.log10(date)+1) != 6);
		System.out.println("What time for the reservation (24-hr format - strictly by the hour):");
		do {
			time = sc.nextInt();
			if(time < 0000 || time > 2400) {
				System.out.println("Invalid time format!");
				System.out.println("What timing would you like to check (24-hrs format - strictly by the hour):");
			}
		}while (time < 0000 || time > 2400);
		int index = res_control.indexing(time);

		System.out.println("Searching for empty tables...");
		res_list.Display_Available_Table(res_control.dayOfWeek(date) ,index);

		System.out.println("Which table would you like to book?");
		do {
			table_id = sc.nextInt();
			if (table_id < 1 || table_id > 10) {
				System.out.println("Table ID is only from 1-10!");
				System.out.println("Which table would you like to book?");
			}
		}while(table_id < 1 || table_id > 10);

		if(res_control.createReservation(name, num, time, table_id, pax, index, date, res_list, false))
			System.out.println("Reservation booked!");
	}

	/**
	 * Has the function of checking reservation details and also removing existing reservations that are stored
	 */
	public void check_remove_Reservations() {
		int option;
		res_control.updateReservation(res_list);
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to:");
		System.out.println("(1) Check Reservation");
		System.out.println("(2) Remove Reservation:");
		do {
			option = sc.nextInt();
			if (option < 1 || option > 2)
				System.out.println("Invalid input! Choose option 1 or 2!");
		}while (option < 1 || option > 2);
		System.out.println("Name of the person who booked:");
		sc.nextLine();
		String Name = sc.nextLine();
		System.out.println("Phone number of the person who booked:");

		int phone = sc.nextInt();
		if (option == 1)
			res_control.getReservationDetails(Name, phone);
		else if (option == 2) {
			if (res_control.removeReservation(Name, phone, res_list))
				System.out.println("Reservation removed!");
			else
				System.out.println("No reservations to begin with!");
		}
		else
			System.out.println("Invalid input!");
	}

	/**
	 * Checking for free tables according to the date and time given by the user input
	 */
	public void checkTableAvailability() {
		int date1, time1;
		res_control.updateReservation(res_list);
		Scanner sc = new Scanner(System.in);
		System.out.println("Which date would you like to check (YYMMDD):");
		do {
			date1 = sc.nextInt();
			if (((int)Math.log10(date1)+1) != 6) {
				System.out.println("Input a correct date format!");
				System.out.println("Which date would you like to check (YYMMDD):");
			}
		}while(((int)Math.log10(date1)+1) != 6);
		System.out.println("What timing would you like to check (24-hrs format - strictly by the hour):");
		do {
			time1 = sc.nextInt();
			if((((int)Math.log10(time1)+1) != 4) || (time1 < 0000 || time1 > 2400)) {
				System.out.println("Invalid time format!");
				System.out.println("What timing would you like to check (24-hrs format - strictly by the hour):");
			}
		}while ((((int)Math.log10(time1)+1) != 4) || (time1 < 0000 || time1 > 2400));
		int day_int_value = res_control.dayOfWeek(date1);
		int index1 = res_control.indexing(time1);
		res_list.Display_Available_Table(day_int_value, index1);
	}

	/**
	 * Check if the table is available. Only used for developers.
	 * 
	 * @param Table_num Table number to check
	 * @param YYMMDD The current date
	 * @param time The current time
	 * @return Whether the table is available based off the reservations
	 */
	public boolean checkTableAvailabilitycurrent(int Table_num,int YYMMDD,int time) {
		return res_list.checkAvail(res_control.dayOfWeek(YYMMDD),res_control.indexing(time),Table_num);
	}
}