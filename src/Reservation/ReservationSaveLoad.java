package Reservation;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity class to interact with the data for Reservation
 */
public class ReservationSaveLoad {	
	/**
	 * Saves the reservation details into an external txt file
	 * @param res_control ReservationController that keeps the ArrayList of reservation objects
	 * @return True or False on whether the reservations have been saved successfully
	 */
	public boolean saveReservation(ReservationController res_control) {
		String filepath = "src/Reservationsave.txt";
		ArrayList<Reservation> reservations = res_control.getReservation();
		try {
			FileWriter fw=new FileWriter(filepath,false);
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);

			for (int i = 0; i < reservations.size(); i++) {
				pw.println(reservations.get(i).getName() + "," + reservations.get(i).getNumber() + "," + reservations.get(i).getBookingtime() + "," + reservations.get(i).gettableid() + "," + reservations.get(i).getPax() + "," + reservations.get(i).getDay() + "," + reservations.get(i).getDate());
				pw.flush();
			}
			pw.close();
			return true;
		}
		catch(Exception e) {
			System.out.println("Reservation not saved properly!");
			return false;
		}
	}

	/**
	 * Loads the reservation details that are stored in the external text file into the application whenever the it restarts so it will be updated with where it left off before it was exited
	 * @param res_control ReservationController that keeps the ArrayList of reservation objects
	 * @param res_list Reservation_list that contains the 3D array of table availabilities
	 */
	public void loadReservation(ReservationController res_control, Reservation_list res_list) {
		String name;
		int number, time, table_id, pax, index, date;
		String filepath = "src/Reservationsave.txt";
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			line = br.readLine();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyMMdd");
			LocalDateTime now = LocalDateTime.now();
			int hour = Integer.parseInt(dtf.format(now));
			int current_date = Integer.parseInt(dtf1.format(now));
			while(line != null) {
				String[] row=line.split(",");
				name = row[0];
				number = Integer.parseInt(row[1]);
				time = Integer.parseInt(row[2]);
				table_id = Integer.parseInt(row[3]);
				pax = Integer.parseInt(row[4]);
				date = Integer.parseInt(row[6]);
				index = res_control.indexing(time);
				if ((date == current_date) && (hour - time >= 1))
					line = br.readLine();
				else {
					res_control.createReservation(name, number, time, table_id, pax, index, date, res_list, true);
					line = br.readLine();
				}
			}
			br.close();
		}
		catch (Exception e) {
			if (line == null)
				System.out.println("Error loading reservations!");
		}		
	}
}