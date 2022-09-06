package Restaurant;
import java.io.Serializable;

/**
 * Represent a Staff
 */
public class Staff implements Serializable{
	/**
	 * Name of staff
	 */
	private String name;
	/**
	 * Gender of Staff
	 */
	private char gender;
	/**
	 * Employee id of Staff
	 */
	private int employeeID;
	/**
	 * Job title of Employee
	 */
	private String jobtitle;

	/**
	 * 
	 * @param Name Name of staff
	 * @param Gender Gender of Staff
	 * @param EID Employee id of Staff
	 * @param j_title Job title of Employee
	 */
	public Staff(String Name, char Gender, int EID, String j_title){
		name = Name;
		gender = Gender;
		employeeID = EID;
		jobtitle = j_title;
	}

	/**
	 * Get the name of Staff
	 * 
	 * @return Name of Staff
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Get the gender of the Staff
	 * 
	 * @return Gender of Staff
	 */
	public char getGender(){
		return this.gender;
	}

	/**
	 * Get Employee ID
	 * 
	 * @return Employee ID
	 */
	public int getEmployeeID(){
		return this.employeeID;
	}

	/**
	 * Get Job title of the Staff
	 *
	 * @return Job Title of Staff
	 */
	public String getJobTitle(){
		return this.jobtitle;
	}
}