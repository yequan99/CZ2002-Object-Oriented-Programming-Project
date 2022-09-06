package Reservation;

/**
 * Reservation Object
 */
public class Reservation {
	/**
	 * Name of the customer
	 */
	private String name;
	/**
	 * Phone number of the customer
	 */
	private int handphoneNumber;
	/**
	 * Booking time of the reservation
	 */
	private int bookingTime;
	/**
	 * Table number that is being reserved
	 */
	private int table_id;
	/**
	 * Number of pax under the reservation
	 */
	private int no_of_pax;
	/**
	 * Integer value of the day of the week
	 */
	private int day;
	/**
	 * Date of the reservation
	 */
	private int date;

	/**
	 * Constructor of the Reservation Class
	 * @param Name Name of the customer doing the reservation
	 * @param HandphoneNumber Phone number of the customer doing the reservation
	 * @param BookingTime Booking timing of the reservation
	 * @param Table_id Table number that is being reserved
	 * @param No_of_pax Number of people dining 
	 * @param Day Integer value of the day of the week
	 * @param Date Date of the reservation
	 */
	public Reservation(String Name, int HandphoneNumber, int BookingTime, int Table_id, int No_of_pax, int Day, int Date) {
		name = Name;
		handphoneNumber = HandphoneNumber;
		bookingTime = BookingTime;
		table_id = Table_id;
		no_of_pax = No_of_pax;
		day = Day;
		date = Date;
	}
	/**
	 * Gets the booking timing of the reservation
	 * @return Booking timing
	 */
	public int getBookingtime(){
		return this.bookingTime;
	}

	/**
	 * Gets the integer value of the day of the week (1-Monday, 7-Sunday)
	 * @return Integer value of the day of the week
	 */
	public int getDay(){
		return this.day;
	}

	/**
	 * Gets the table number that is being reserved
	 * @return Table number
	 */
	public int gettableid(){
		return this.table_id;
	}

	/**
	 * Gets the name of the customer that the reservation is booked under
	 * @return Name of the customer
	 */
	public String getName(){
		return this.name;
	}  

	/**
	 * Gets the number of people dining 
	 * @return Number of people dining
	 */
	public int getPax(){
		return this.no_of_pax;
	}

	/**
	 * Gets the phone number of the customer who did the reservation
	 * @return Phone number of the customer
	 */
	public int getNumber(){
		return this.handphoneNumber;
	}

	/**
	 * Gets the date of the reservation
	 * @return Date of the reservation
	 */
	public int getDate() {
		return this.date;
	}
}