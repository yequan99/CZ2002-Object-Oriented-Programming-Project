package Reservation;

/**
 * Entity class to interact with the data for Reservation
 */
public class Reservation_list {
	/**
	 * Initialising a 3D array
	 */
	private int[][][] reserve = new int[7][24][10]; 

	/**
	 * Constructor of the 3D reservation list
	 */
	public Reservation_list()  {
		for(int i = 0; i < 7; i++)  {
			for(int j = 0; j < 24; j++) {
				for(int k = 0; k < 10; k++) {
					this.reserve[i][j][k] = 0;
				}
			}
		}
	}

	/**
	 * Gets the 3D reservation list
	 * @return 3D reservation list
	 */
	public int[][][] getReservationList(){
		return this.reserve;
	}

	/**
	 * Reserving the table in the 3D array
	 * @param day Integer value of the day of the week
	 * @param hour Index of the timing of the reservation
	 * @param tableid Table number of the table being reserved
	 */
	public void setReservation(int day, int hour, int tableid){
		reserve[day - 1][hour - 1][tableid - 1] = 1;
	}

	/**
	 * Remove reservation of the table in the 3D array
	 * @param day Integer value of the day of the week
	 * @param hour Index of the timing of the reservation
	 * @param tableid Table number of the table being reserved
	 */
	public void deleteRes(int day, int hour, int tableid){
		reserve[day - 1][hour - 1][tableid - 1] = 0;
	}

	/**
	 * Checks if the table is being reserved via the 3D array
	 * @param day Integer value of the day of the week
	 * @param hour Index of the timing of the reservation
	 * @param tableid Table number of the table being reserved
	 * @return True or False on whether the table is being reserved
	 */
	public boolean checkAvail(int day, int hour, int tableid) {
		if(reserve[day - 1][hour - 1][tableid - 1] != 0)
			return false;
		return true;
	}

	/**
	 * Printing out the tables that are not reserved at the time and date
	 * @param day Integer value of the day of the week
	 * @param hour  Index of the timing of the reservation
	 */
	public void Display_Available_Table(int day, int hour){
		int empty = 0;
		int cap;
		for (int i = 0; i < 10; i++){
			if(reserve[day - 1][hour - 1][i] == 0){
				empty += 1;
				if(i == 0 || i == 1)
					cap = 2;
				else if(i == 2 || i == 3)
					cap = 4;
				else if(i == 4 || i == 5)
					cap = 6;
				else if(i == 6 || i == 7)
					cap = 8;
				else if(i == 8 || i == 9)
					cap = 10;
				else
					cap = 0;
				System.out.println("Table "+(i+1)+" is available!" + "(" + cap + " pax)");
			}
		}
		if (empty == 0)
			System.out.println("All tables are filled!");
	}
}