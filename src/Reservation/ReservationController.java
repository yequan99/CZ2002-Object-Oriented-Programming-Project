package Reservation;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Controller class that deals with the actual functions of the menu
 */
public class ReservationController {
	/**
	 * ArrayList of Reservation object
	 */
	ArrayList<Reservation> reservations;
	/**
	 * Constructor for Reservation SaveLoad
	 */
	ReservationSaveLoad res_saveload = new ReservationSaveLoad();

	/**
	 * Constructor of Reservation Controller
	 */
	ReservationController(){
		this.reservations = new ArrayList<Reservation>();
	}

	/**
	 * Transferring the ArrayList of Reservation objects to other functions that calls this method
	 * 
	 * @return An ArrayList of Reservation objects
	 */
	public ArrayList<Reservation> getReservation(){
		return this.reservations;
	}

	/**
	 * Gets the number of reservations that are on hand
	 * 
	 * @return Size of the ArrayList of Reservations
	 */
	public int get_num_reservations(){
		return this.reservations.size();
	}

	/**
	 * Get the reservation details
	 * @param name Name of the customer who booked the reservation
	 * @param phone_number Phone number of the customer who booked the reservation
	 */
	public void getReservationDetails(String name, int phone_number){
		int found = 0;
		for(int i = 0; i < this.reservations.size(); i++){
			if (this.reservations.get(i).getNumber() == phone_number && this.reservations.get(i).getName().equals(name)){
				found = 1;
				System.out.println("Reservation is booked under the name of: " + this.reservations.get(i).getName());
				System.out.println("Reservation Date: " + this.reservations.get(i).getDate());
				System.out.println("Reservation time: " + this.reservations.get(i).getBookingtime());
				System.out.println("Number of Pax: " + this.reservations.get(i).getPax());
				System.out.println("Phone number: " + this.reservations.get(i).getNumber());
				System.out.println("Table booked (ID): " + this.reservations.get(i).gettableid());
				break;
			} 
		}
		if (found == 0){
			System.out.println("Reservation not found!");
		}
	}

	/**
	 * Creates the reservation according to the user input given that the reservation meets the various requirements
	 * such as having to do the reservation in advance, only being able to reserve not more than 7 days in advance and 
	 * also that the number of pax does not exceed the seating capacity of the table
	 * @param Name Name of the customer who booked the reservation
	 * @param HandphoneNumber Phone number of the customer who booked the reservation
	 * @param BookingTime Booking time of the reservation
	 * @param Table_id Table number that is being booked for the reservation
	 * @param No_of_pax Number of people 
	 * @param start The timing index for reservation list 3d array
	 * @param Date Date of the reservation
	 * @param res_list Reservation_list class
	 * @param loading Boolean value to determine if the application is creating due to user inputs or if it is loading from the saved data
	 * @return True or False value on whether the Reservation has been booked
	 */
	public boolean createReservation(String Name, int HandphoneNumber, int BookingTime, int Table_id, int No_of_pax, int start, int Date, Reservation_list res_list, boolean loading){
		int Day = dayOfWeek(Date);
		if (!checkTiming(Date, BookingTime)) {
			System.out.println("Reservations must be done prior to the reservation timing!");
			return false;
		}
		else if (!checkDate(Date, BookingTime)) {
			System.out.println("Reservations can only be done 7 days in advance!");
			return false;
		}
		else if (!checkCap(No_of_pax, Table_id)) {
			System.out.println("Number of pax exceeds the seating capacity of the table!");
			return false;
		}
		else if (res_list.checkAvail(Day, start, Table_id)){
			Reservation res = new Reservation(Name, HandphoneNumber, BookingTime, Table_id, No_of_pax, Day, Date);
			res_list.setReservation(Day, start, Table_id);
			this.reservations.add(res);
			res_saveload.saveReservation(this);
			return true;
		}
		else {
			if (loading == false)
				System.out.println("Booking Error!");
			return false;
		}
	}

	/**
	 * Removes reservation through searching for the name and phone number of the customer who booked the reservation
	 * @param Name Name of the customer who booked the reservation
	 * @param HandphoneNumber Phone number of the customer who booked the reservation
	 * @param res_list 3D array that stores the availability of the tables on different days and timing
	 * @return True or False value on whether the reservation has been removed
	 */
	public boolean removeReservation(String Name, int HandphoneNumber, Reservation_list res_list){
		for (int i = 0; i < this.reservations.size(); i++){
			if (this.reservations.get(i).getNumber() == HandphoneNumber && this.reservations.get(i).getName().equals(Name)){
				int day = this.reservations.get(i).getDay();
				int time = this.reservations.get(i).getBookingtime();
				int tableid = this.reservations.get(i).gettableid();
				int start = indexing(time);
				if (!res_list.checkAvail(day, start, tableid)) {
					res_list.deleteRes(day, start, tableid);
					this.reservations.remove(i);
					res_saveload.saveReservation(this);
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * Updates the list of reservations according to real time by removing reservations that are 1 min past its reservation timing
	 * @param res_list 3D array that stores the availability of the tables on different days and timing
	 */
	public void updateReservation(Reservation_list res_list) {
		res_saveload.loadReservation(this, res_list);
		int i = 0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyMMdd");
		LocalDateTime now = LocalDateTime.now();
		int hour = Integer.parseInt(dtf.format(now));
		int date = Integer.parseInt(dtf1.format(now));
		do {
			if (this.reservations.size() != 0) {
				if (this.reservations.get(i).getDate() == date) {
					int time = this.reservations.get(i).getBookingtime();
					if(hour - time >= 15) {
						int num = this.reservations.get(i).getNumber();
						String name = this.reservations.get(i).getName();
						if (removeReservation(name, num, res_list))
							i--;
						System.out.println("Reservation for " + name + " at " + time + "hrs has been removed as it has passed 15 minute(s) of the reservation timing!");
					}
				}
				i++;
			}
		}while (i < this.reservations.size());
	}

	/**
	 * Check that reservation can only be done prior to the reservation timing
	 * @param Date Booking date
	 * @param Time Booking timing
	 * @return True or False value on whether the reservation timing is earlier than real time
	 */
	public boolean checkTiming(int Date, int Time) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyMMdd");
		LocalDateTime now = LocalDateTime.now();
		int hour = Integer.parseInt(dtf.format(now));
		int date = Integer.parseInt(dtf1.format(now));
		if (Date == date) {
			if (Time - hour < 0.1)
				return false;
		}
		else if (Date - date < 0)
			return false;
		else
			return true;
		return true;
	}

	/**
	 * Checking that reservation can only be done if the number of people dining is less than or equal to the seating capacity of the table
	 * @param Pax Number of people dining in
	 * @param TableID Table number that is being booked
	 * @return True or False value on whether the reservation exceeds the seating capacity of the selected table
	 */
	public boolean checkCap(int Pax, int TableID) {
		if ((TableID == 1 || TableID == 2) && Pax > 2) 
			return false;
		else if ((TableID == 3 || TableID == 4) && Pax > 4) 
			return false;
		else if ((TableID == 5 || TableID == 6) && Pax > 6) 
			return false;
		else if ((TableID == 7 || TableID == 8) && Pax > 8) 
			return false;
		else if ((TableID == 9 || TableID == 10) && Pax > 10) 
			return false;
		else
			return true;			
	}

	/**
	 * Check that reservation can only be done up to 1 week in advance
	 * @param Date Booking date
	 * @param Time Booking time
	 * @return True or False value on whether the reservation date is not more than 1 week in advance
	 */
	public boolean checkDate(int Date, int Time) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyMMdd");
		LocalDateTime now = LocalDateTime.now();
		int currentdate = Integer.parseInt(dtf1.format(now));
		int hour = Integer.parseInt(dtf.format(now));
		if (Date - currentdate > 7)
			return false;
		else if ((Date - currentdate == 7) && (Time - hour> 0))
			return false;
		else
			return true;
	}

	/**
	 * Getting the integer value of the day of the week (1 - Monday, 7 - Sunday)
	 * @param Date Booking date
	 * @return Integer value of the day of week
	 */
	public int dayOfWeek(int Date) {
		int day;
		int year = Date % 100;
		int left = (Date - year) / 100;
		int month = left % 100;
		left = (left - month) / 100;
		if (left < 10)
			day = left % 10;
		else day = left;
		year = 2000 + year;

		LocalDate date = LocalDate.of(year, month, day);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		int dayOfWeekIntValue = dayOfWeek.getValue() - 1;

		return dayOfWeekIntValue;
	}

	/**
	 * Getting the index for time for the 3D array of Reservation_list
	 * @param Time Booking timing
	 * @return Integer value of the timing index to access the 3D array of Reservation_list
	 */
	public int indexing(int Time) {
		int min = Time % 100;
		int hour = (Time - min) / 100;
		return hour;
	}
}