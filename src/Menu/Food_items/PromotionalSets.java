package Menu.Food_items;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represent a promotional set
 */
public class PromotionalSets extends Fooditems{

	/**
	 * List of main courses in the promotional set
	 */
	ArrayList<Maincourse> maincourse = new ArrayList<Maincourse>();
	/**
	 * List of desserts in the promotional set
	 */
	ArrayList<Desserts> dessert=new ArrayList<Desserts>();
	/**
	 * List of drinks in the promotional set
	 */
	ArrayList<Drinks> drink=new ArrayList<Drinks>();

	/**
	 * Constructor of promotional set
	 *
	 * @param name Name of the promotional set
	 * @param main ArrayList of the Main course{@link Maincourse} in the promotional set
	 * @param desserts ArrayList of the Dessert{@link Desserts} in the promotional set
	 * @param drinks ArrayList of the Drink{@link Drinks} in the promotional set
	 * @param price Price of the promotional set
	 */
	public PromotionalSets(String name, ArrayList<Maincourse> main,ArrayList<Desserts> desserts,ArrayList<Drinks> drinks, double price){

		super(price,name,true);
		this.maincourse=main;
		this.dessert=desserts;
		this.drink=drinks;
	}

	/**
	 * Find item in the promotional set
	 *
	 * @param Type 0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 * @param item The Food item
	 * @return Whether the item is inside of the promotional set
	 */
	public boolean finditem(int Type, Fooditems item){
		int index=-1;
		//0: main course ; 1:dessert ; 2:drinks;
		switch(Type){
		case 0:
			index=this.maincourse.indexOf(item);
			break;
		case 1:
			index=this.dessert.indexOf(item);
			break;
		case 2:
			index=this.drink.indexOf(item);
			break;
		}
		if (index>-1){
			return true;
		}
		else{
			return false;
		}

	}

	/**
	 * Get the name of the promotional set
	 *
	 * @return Name of promotional set
	 */
	public String getname(){
		return super.getname();
	}

	/**
	 * Get the price of the promotional set
	 *
	 * @return Price of promotional set
	 */
	public double getprice(){
		return super.getprice();
	}

	/**
	 * Update the name of the promotional set
	 *
	 * @param n The new name of the promotional set
	 */
	public void changeName(String n){
		super.changename(n);
	}

	/**
	 * Update the price of the promotional set
	 *
	 * @param p The price of the promotional set
	 */
	public void changePrice(double p){
		super.changeprice(p);
	}

	/**
	 * Get the ArrayList of Main Course {@link Maincourse}
	 *
	 * @return ArrayList of Main Course
	 */
	public ArrayList<Maincourse> getmainlist(){
		return maincourse;
	}

	/**
	 * Get the ArrayList of Dessert {@link Desserts}
	 *
	 * @return ArrayList of Dessert
	 */
	public ArrayList<Desserts> getdessertlist(){
		return dessert;
	}

	/**
	 * Get the ArrayList of Drink {@link Drinks}
	 *
	 * @return ArrayList of Drink
	 */
	public ArrayList<Drinks> getdrinklist(){
		return drink;
	}

	/**
	 * Print all the items in the promotional set
	 */
	public void printallitems(){
		int i=1;
		for(int j=0;j<this.maincourse.size();j++){
			System.out.println("("+i+") "+maincourse.get(j).getname());
			i++;
		}
		for(int j=0;j<this.dessert.size();j++){
			System.out.println("("+i+") "+dessert.get(j).getname());
			i++;
		}
		for(int j=0;j<this.drink.size();j++){
			System.out.println("("+i+") "+drink.get(j).getname());
			i++;
		}
	}

	/**
	 * Remove item from the promotional set
	 */
	public void removefrompromo(){
		//type=1 means remove an item. type=2 means add an item
		this.printallitems();
		Scanner scan = new Scanner(System.in);
		int index=scan.nextInt();
		if (index<=this.maincourse.size()){
			this.maincourse.remove(index-1);
		}
		else if((index-this.maincourse.size())<=this.dessert.size()){
			this.dessert.remove(index-1-this.maincourse.size());
		}
		else if((index-this.maincourse.size()-this.dessert.size())<=this.drink.size()){
			this.drink.remove(index-1-this.maincourse.size()-this.dessert.size());
		}
	}

	/**
	 * Add a main course into the promotional set
	 *
	 * @param item Main course that is to be added {@link Maincourse}
	 */
	public void addmaincoursetopromo(Maincourse item){
		this.maincourse.add(item);
	}

	/**
	 * Add a dessert into the promotional set
	 *
	 * @param item Dessert that is to be added {@link Desserts}
	 */
	public void adddesserttopromo(Desserts item){
		this.dessert.add(item);
	}

	/**
	 * Add a drink into the promotional set
	 *
	 * @param item Drink that is to be added {@link Drinks}
	 */
	public void adddrinktopromo(Drinks item){
		this.drink.add(item);
	}
	
	/**
	 * Checks for promotional set
	 * 
	 * @return True or False on whether item is on promotional set
	 */
	public boolean ispromoset(){
		return super.ispromoset;
	}
}