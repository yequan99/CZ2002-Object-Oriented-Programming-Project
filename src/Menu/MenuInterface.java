package Menu;

import Menu.Food_items.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The boundary class that will interact with the user input and do various functions for the menu.
 */
public class MenuInterface {
	/**
	 * Calls in Menu
	 */
	private MenuController menu;

	/**
	 * Constructor for the interface
	 */
	public MenuInterface(){
		this.menu=new MenuController();
	}

	/**
	 * Get the menu controller
	 *
	 * @return The menu controller.
	 */
	public MenuController getmenu(){
		return menu;
	}

	/**
	 * Saves the Menu. {@link MenuController#SaveMenu()}
	 */
	public void SaveMenu() {
		this.menu.SaveMenu();
	}

	/**
	 * Prints the submenu based on the food type
	 * @param type 0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 */
	public void printsubmenu(int type){
		this.menu.printsubmenu(type);
	}

	/**
	 * Ask inputs from the user and removes a food item/promotional set from the menu
	 *
	 * @param type 0: Main Course, 1: Desserts, 2: Drinks, 3: Promotional Sets
	 */
	public void removeitem(int type){
		Scanner scan = new Scanner(System.in);
		System.out.println("Which item would you want to remove?");
		this.printsubmenu(type);
		int index=scan.nextInt()-1;
		this.menu.removeMenuItem(type, index);
		this.menu.SaveMenu();
		return;
	}

	/**
	 * Add alacarte food items to the menu.
	 *
	 * @param name Name of the food that will be added
	 * @return Whether the item has successfully been added
	 */
	public boolean add_alacarte_item(String name){
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose the food type that will be added to the menu: ");
		System.out.println("1. Main Course");
		System.out.println("2. Dessert");
		System.out.println("3. Drink");
		int Type=scan.nextInt();
		Type--;
		if(Type<0||Type>2){
			return false;
		}
		//collect information about the item
		System.out.println("Price of item: ");
		double price=scan.nextDouble();
		boolean check=true;
		String description=" ";
		while(check){
			System.out.println("Description of item: ");
			scan.nextLine();
			description=scan.nextLine();
			while(check){
				System.out.println("Confirm? Y/N");
				System.out.println(description);
				String temp=scan.next();
				if (temp.equals("Y")){
					check=false;
					break;
				}
				else if(temp.equals("N")){
					while(true){
						System.out.println("Do you still want to add the menu item? Y/N");
						String temp1=scan.next();
						if (temp1.equals("Y")){
							break;
						}
						else if(temp1.equals("N")){
							return false;
						}
					}
					continue;
				}
			}
		}
		this.menu.addindividualitems(Type, name, price, description);
		this.menu.SaveMenu();
		return true;

	}

	/**
	 * Add a promotional set to the menu
	 *
	 * @param name The name of the promotional set
	 * @return Whether the promotional set is successfully added
	 */
	public boolean addpromo(String name){
		ArrayList<Maincourse> maincoursepromo=new ArrayList<Maincourse>();
		ArrayList<Desserts> dessertpromo=new ArrayList<Desserts>();
		ArrayList<Drinks> drinkpromo=new ArrayList<Drinks>();
		Scanner scan = new Scanner(System.in);
		while(true){
			System.out.println("Choose the food type that will be added to this promotion: ");
			System.out.println("1. Main Course");
			System.out.println("2. Dessert");
			System.out.println("3. Drink");
			System.out.println("Enter -1 to go back and Enter 4 to finalize promotion.");
			int choice=scan.nextInt();
			if (choice==-1){
				System.out.println("Promotional set not created.");
				return false;
			}
			if (choice==4){
				break;
			}
			switch(choice){
			case 1:
				System.out.println("Which Maincourse do you want to add to this promotion?");
				this.menu.printsubmenu(--choice);
				int main_choice=scan.nextInt();
				main_choice--;
				maincoursepromo.add(this.menu.getmainlist().get(main_choice));
				break;
			case 2:
				System.out.println("Which Dessert do you want to add to this promotion?");
				this.menu.printsubmenu(--choice);
				int dessert_choice=scan.nextInt();
				dessert_choice--;
				dessertpromo.add(this.menu.getdessertlist().get(dessert_choice));
				break;
			case 3:
				System.out.println("Which Drink do you want to add to this promotion?");
				this.menu.printsubmenu(--choice);
				int drink_choice=scan.nextInt();
				drink_choice--;
				drinkpromo.add(this.menu.getdrinklist().get(drink_choice));
				break; 
			}
		}
		System.out.println("Price of promotion: ");
		double price=scan.nextDouble();
		if (this.menu.addpromo(name, maincoursepromo, dessertpromo, drinkpromo, price)){
			return true;
		}
		return false;
	}

	/**
	 * User will input what Main Course will be updated. Program will then update the menu accordingly.
	 * {@link Menu.Food_items.Maincourse} and {@link MenuController#getmainlist()}
	 */
	public void EditMaincourse(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Which Main Course to update: ");
		for (int i = 0; i < this.menu.getmainlist().size(); i++){
			System.out.println("("+ (i+1) + ") " + this.menu.getmainlist().get(i).getname());
		}
		int item = scan.nextInt();
		System.out.println("What would you like to edit?");
		System.out.println("(1) Name of main course");
		System.out.println("(2) Price of main course");
		System.out.println("(3) Description of main course");
		int choice = scan.nextInt();
		switch (choice){
		case 1:
			System.out.println("What name would you want to change to?");
			scan.nextLine();
			String name = scan.nextLine();
			this.menu.getmainlist().get(item-1).changeName(name);
			this.menu.SaveMenu();
			break;
		case 2:
			System.out.println("What price would you want to change to?");
			double price = scan.nextDouble();
			this.menu.getmainlist().get(item-1).changePrice(price);
			this.menu.SaveMenu();
			break;
		case 3:
			System.out.println("What description would you want to change to?");
			scan.nextLine();
			String description = scan.nextLine();
			this.menu.getmainlist().get(item-1).changeDescription(description);
			this.menu.SaveMenu();
			break;
		}
	}


	/**
	 * User will input what Dessert will be updated. Program will then update the menu accordingly.
	 * {@link Menu.Food_items.Desserts} and {@link MenuController#getdessertlist()}
	 */
	public void EditDesserts(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Which dessert to update:");
		for (int i = 0; i < this.menu.getdessertlist().size(); i++){
			System.out.println("("+ (i+1) + ") " + this.menu.getdessertlist().get(i).getname());
		}
		int item = scan.nextInt();
		System.out.println("What would you like to edit?");
		System.out.println("(1) Name of dessert");
		System.out.println("(2) Price of dessert");
		System.out.println("(3) Description of dessert");
		int choice = scan.nextInt();
		switch (choice){
		case 1:
			System.out.println("What name would you want to change to?");
			scan.nextLine();
			String name = scan.nextLine();
			this.menu.getdessertlist().get(item-1).changeName(name);
			this.menu.SaveMenu();
			break;
		case 2:
			System.out.println("What price would you want to change to?");
			double price = scan.nextDouble();
			this.menu.getdessertlist().get(item-1).changePrice(price);
			this.menu.SaveMenu();
			break;
		case 3:
			System.out.println("What description would you want to change to?");
			scan.nextLine();
			String description = scan.nextLine();
			this.menu.getdessertlist().get(item-1).changeDescription(description);
			this.menu.SaveMenu();
			break;
		}
	}


	/**
	 * User will input what Drinks will be updated. Program will then update the menu accordingly.
	 * {@link Menu.Food_items.Drinks} and {@link MenuController#getdrinklist()}
	 */
	public void EditDrinks(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Which drink to update:");
		for (int i = 0; i < this.menu.getdrinklist().size(); i++){
			System.out.println("("+ (i+1) + ") " + this.menu.getdrinklist().get(i).getname());
		}
		int item = scan.nextInt();
		System.out.println("What would you like to edit?");
		System.out.println("(1) Name of drink");
		System.out.println("(2) Price of drink");
		System.out.println("(3) Description of drink");
		int choice = scan.nextInt();
		switch (choice){
		case 1:
			System.out.println("What name would you want to change to?");
			scan.nextLine();
			String name = scan.nextLine();
			this.menu.getdrinklist().get(item-1).changeName(name);
			this.menu.SaveMenu();
			break;
		case 2:
			System.out.println("What price would you want to change to?");
			int price = scan.nextInt();
			this.menu.getdrinklist().get(item-1).changePrice(price);
			this.menu.SaveMenu();
			break;
		case 3:
			System.out.println("What description would you want to change to?");
			scan.nextLine();
			String description = scan.nextLine();
			this.menu.getdrinklist().get(item-1).changeDescription(description);
			this.menu.SaveMenu();
			break;
		}
	}

	/**
	 * User will input what Promotional Set will be updated. Program will then update the menu accordingly.
	 * {@link Menu.Food_items.PromotionalSets} and {@link MenuController#getpromolist()}
	 * */
	public void EditPromo(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Which promotional set to update?");
		int i;
		for (i=0; i < this.menu.getpromolist().size(); i++){
			System.out.println("("+ (i+1) + ") " + this.menu.getpromolist().get(i).getname());
		}
		int item = scan.nextInt();
		System.out.println("What would you like to edit?");
		System.out.println("(1) Name of promotional set");
		System.out.println("(2) Price of promotional set");
		System.out.println("(3) Remove Items in promotional set");
		System.out.println("(4) Add Items in promotional set");
		int choice = scan.nextInt();
		switch (choice){
		case 1:
			System.out.println("What name would you want to change to?");
			scan.nextLine();
			String name = scan.nextLine();
			this.menu.getpromolist().get(item-1).changeName(name);
			break;
		case 2:
			System.out.println("What price would you want to change to?");
			int price = scan.nextInt();
			this.menu.getpromolist().get(item-1).changePrice(price);
			break;
		case 3:
			System.out.println("What item would you want to remove?");
			this.menu.getpromolist().get(item-1).removefrompromo();
			break;
		case 4:
			System.out.println("What type item would you want to add?");
			System.out.println("(1) Main Course");
			System.out.println("(2) Dessert");
			System.out.println("(3) Drink");
			int type = scan.nextInt()-1;
			if (type<0||type>2){
				System.out.println("Nothing is added.");
				break;
			}
			this.menu.printsubmenu(type);
			int index = scan.nextInt()-1;
			if(type==0){
				this.menu.getpromolist().get(item-1).addmaincoursetopromo(this.menu.getmainlist().get(index));
			}
			else if(type==1){
				this.menu.getpromolist().get(item-1).adddesserttopromo(this.menu.getdessertlist().get(index));
			}
			else if(type==2){
				this.menu.getpromolist().get(item-1).adddrinktopromo(this.menu.getdrinklist().get(index));
			}
			break;
		}
	}
}