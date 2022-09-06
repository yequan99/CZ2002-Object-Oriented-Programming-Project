package Reservation;
import java.util.*;

/**
 * Entity class that records the attributes of a customer
 */
public class Customer{
	//hashmap of <phonenumber, membershipStatus>
	/**
	 * A hashmap that maps users to whether they have membership
	 */
	private Map<Integer, Boolean> member_list = new HashMap<>();

	/**
	 * Stores the customers who are members via their phone number
	 */
	public Customer(){
		// customer handphone number is the membership ID(will always be a member)
		member_list.put(98765432, true);
		member_list.put(96385274, true);
		member_list.put(87654321, true);
	}

	/**
	 * Check if the customer have membership
	 *
	 * @param customer_id Customer id
	 * @return Whether the user has a membership
	 */
	public boolean CheckMembership(int customer_id){
		//check if handphone number in list
		if (member_list.containsKey(customer_id)) {
			return member_list.get(customer_id);
		}
		else {
			return false;
		}
	}

	/**
	 * Adds a member into the list of customers
	 *
	 * @param customer_id Customer id
	 * @return  Whether the member has been successfully added.
	 */
	public boolean AddMember(int customer_id) {
		if (!member_list.containsKey(customer_id)) {
			member_list.put(customer_id, true);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Delete a member from the list
	 * 
	 * @param customer_id Customer id
	 * @return Whether the member has been successfully deleted.
	 */
	public boolean DelMember(int customer_id) {
		if (member_list.containsKey(customer_id)) {
			member_list.remove(customer_id);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Display all the members recorded.
	 */
	public void DisplayMembership () {
		for (Map.Entry<Integer,Boolean> pairEntry: member_list.entrySet()) {
			System.out.println("Customer ID: "+ pairEntry.getKey() + "\t Membership Status : " + pairEntry.getValue());
		}
	}
}