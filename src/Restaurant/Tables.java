package Restaurant;

/**
 * Represents a table
 */
public class Tables{
	/**
	 * Table id
	 */
	private int table_id;
	/**
	 * Seating capacity of the table
	 */
	private int seating_capacity;
	/**
	 * Availability of the table
	 */
	private boolean availability;

	/**
	 * Constructor of a table
	 * 
	 * @param tableID Table id
	 * @param seating_cap Seating capacity of the table
	 */
	public Tables(int tableID, int seating_cap){
		this.table_id = tableID;
		this.seating_capacity = seating_cap;
		this.availability = true;
	}

	/**
	 * Get Table id
	 * 
	 * @return Table id
	 */
	public int getTableID(){
		return this.table_id;
	}

	/**
	 * Get seating capacity of the table
	 * 
	 * @return Seating capacity
	 */
	public int getCapacity(){
		return this.seating_capacity;
	}

	/**
	 * Update availability of the table
	 * 
	 * @param availability Whether the availability of the table is updated
	 */
	public void setAvail(boolean availability){
		this.availability = availability;
	}

	/**
	 * Get the availability of the table
	 * 
	 * @return Availability of the table
	 */
	public boolean getAvail() {
		return this.availability;
	}
}