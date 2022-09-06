package Menu;

import Menu.Food_items.*;
import java.util.ArrayList;

/**
 * The Controller class that deals with the actual functions of the menu
 */
public class MenuController {
	/**
	 * ArrayList of maincourse
	 */
	private ArrayList<Maincourse> maincoursemenu;
	/**
	 * ArrayList of dessert
	 */
	private ArrayList<Desserts> dessertmenu;
	/**
	 * ArrayList of drinks
	 */
	private ArrayList<Drinks> drinkmenu;
	/**
	 * ArrayList of promotional sets
	 */
	private ArrayList<PromotionalSets> promomenu;
	/**
	 * Calls in MenuSaveLoad
	 */
	private MenuSaveLoad Menudata;

	/**
	 * Constructor for Menu Constructor
	 */
	MenuController(){
		this.maincoursemenu=new ArrayList<Maincourse>();
		this.dessertmenu=new ArrayList<Desserts>();
		this.drinkmenu=new ArrayList<Drinks>();
		this.promomenu=new ArrayList<PromotionalSets>();
		Menudata=new MenuSaveLoad();
		this.Menudata.LoadMenu(this);
	}

	/**
	 * Saves the Menu
	 *
	 * {@link MenuInterface#SaveMenu()}
	 *
	 */
	public void SaveMenu(){
		this.Menudata.SaveMenu(this);
	}

	/**
	 *Get the List of Main Courses in the Menu.
	 *
	 * @return An ArrayList for the Main Course from menu
	 */
	public ArrayList<Maincourse> getmainlist(){
		return this.maincoursemenu;
	}

	/**
	 * Get the List of Dessert in the Menu.
	 *
	 * @return An ArrayList for the Dessert Course from menu
	 */
	public ArrayList<Desserts> getdessertlist(){
		return this.dessertmenu;
	}

	/**
	 * Get the List of Drinks in the Menu.
	 *
	 * @return An ArrayList for the Dessert Course from menu
	 */
	public ArrayList<Drinks> getdrinklist(){
		return this.drinkmenu;
	}

	/**
	 * Get the List of Promotional Sets in the Menu.
	 *
	 * @return An ArrayList for the Promotional Sets from menu
	 */
	public ArrayList<PromotionalSets> getpromolist(){
		return this.promomenu;
	}

	/**
	 *Print the different list of items by their item type
	 *
	 *
	 * @param Type 0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 */
	public void printsubmenu(int Type){
		switch(Type){
		case 0:
			System.out.println("Main Courses:");
			for(int i=0;i<this.maincoursemenu.size();i++){
				Maincourse temp=this.maincoursemenu.get(i);
				System.out.println(i+1+": "+temp.getname());
				System.out.println(temp.getdescript());
				System.out.println("$"+temp.getprice());
			}
			return;
		case 1:
			System.out.println("Desserts:");
			for(int i=0;i<this.dessertmenu.size();i++){
				Desserts temp=this.dessertmenu.get(i);
				System.out.println(i+1+": "+temp.getname());
				System.out.println(temp.getdescript());
				System.out.println("$"+temp.getprice());
			}
			return;
		case 2:
			System.out.println("Drinks:");
			for(int i=0;i<this.drinkmenu.size();i++){
				Drinks temp=this.drinkmenu.get(i);
				System.out.println(i+1+": "+temp.getname());
				System.out.println(temp.getdescript());
				System.out.println("$"+temp.getprice());
			}
			return;
		case 3:
			System.out.println("Promotional Sets:");
			for(int i=0;i<this.promomenu.size();i++){
				PromotionalSets temp=this.promomenu.get(i);
				System.out.println(i+1+": "+temp.getname());
				System.out.println("$"+temp.getprice());
			}
			return;
		}
	}

	/**
	 *Adds an individual ala carte item in the menu.
	 *
	 * @param type 0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 * @param name The name of the item
	 * @param price The price of the item that one wants to set
	 * @param description The description of the item
	 * @return Shows if the item has successfully insert an item
	 */
	public boolean addindividualitems(int type, String name, double price, String description){
		switch(type){
		case 0:
			Maincourse new_main= new Maincourse(name, price, description);
			this.maincoursemenu.add(new_main);
			return true;
		case 1:
			Desserts new_dessert=new Desserts(name, price, description);
			this.dessertmenu.add(new_dessert);
			return true;
		case 2:
			Drinks new_drink=new Drinks(name, price, description);
			this.drinkmenu.add(new_drink);
			return true;
		}
		return false;
	}

	/**
	 *Adds a promotional set.
	 *
	 * @param name The name of the promotional sets
	 * @param maincoursepromo The ArrayList of Main Course in the promotional set
	 * @param dessertpromo The ArrayList of Dessert in the promotional set
	 * @param drinkpromo The ArrayList of Drink in the promotional set
	 * @param price The price of the promotional set
	 * @return True or False on whether the promo has been added
	 */
	public boolean addpromo(String name, ArrayList<Maincourse> maincoursepromo,ArrayList<Desserts> dessertpromo,ArrayList<Drinks> drinkpromo, double price){
		PromotionalSets new_promo = new PromotionalSets(name, maincoursepromo,dessertpromo,drinkpromo, price);
		this.promomenu.add(new_promo);
		return true;
	}

	/**
	 * Removes an item or promotional set from menu.
	 *
	 * @param Type  0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 * @param item_index The index of the item that user wants to remove in the ArrayList
	 * @return Whether the item is successfully removed
	 */
	public boolean removeMenuItem(int Type,int item_index){
		PromotionalSets temp;

		switch(Type){
		case 0:
			if(item_index>=this.maincoursemenu.size()){
				System.out.println("Failed to remove.");
				return false;
			}
			//remove from promotion
			for(int i=this.promomenu.size()-1;i>=0;i--){
				temp=this.promomenu.get(i);
				if(temp.finditem(Type,this.maincoursemenu.get(item_index))){
					this.promomenu.remove(i);
				}
			}
			//remove from the menu itself
			this.maincoursemenu.remove(item_index);
			return true;
		case 1:
			if(item_index>=this.dessertmenu.size()){
				System.out.println("Failed to remove.");
				return false;
			}
			for(int i=this.promomenu.size()-1;i>=0;i--){
				temp=this.promomenu.get(i);
				if(temp.finditem(Type,this.dessertmenu.get(item_index))){
					this.promomenu.remove(i);
				}
			}

			this.dessertmenu.remove(item_index);
			return true;

		case 2:
			if(item_index>=this.drinkmenu.size()){
				System.out.println("Failed to remove.");
				return false;
			}
			for(int i=this.promomenu.size()-1;i>=0;i--){
				temp=this.promomenu.get(i);
				if(temp.finditem(Type,this.drinkmenu.get(item_index))){
					this.promomenu.remove(i);
				}
			}

			this.drinkmenu.remove(item_index);
			return true;

		case 3:
			if(item_index>=this.promomenu.size()){
				System.out.println("Failed to remove.");
				return false;
			}
			this.promomenu.remove(item_index);
			return true;
		}
		return false;
	}
}