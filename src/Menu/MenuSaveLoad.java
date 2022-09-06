package Menu;

import Menu.Food_items.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Entity class to interact with the data for menu
 */
public class MenuSaveLoad{
	/**
	 * Saves the menu
	 *
	 * @param menu The menu that needs to be saved
	 * @return Whether the menu is successfully saved
	 */
	public boolean SaveMenu(MenuController menu){
		String filepath="src/Menu.txt";
		ArrayList<Maincourse> maincoursemenu=menu.getmainlist();
		ArrayList<Desserts> dessertmenu=menu.getdessertlist();
		ArrayList<Drinks> drinkmenu=menu.getdrinklist();

		try{
			FileWriter fw=new FileWriter(filepath,false);
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);
			//Run through all the things to save the menu

			for (int i=0;i<maincoursemenu.size();i++){

				pw.println(0+","+maincoursemenu.get(i).getname()+","+maincoursemenu.get(i).getprice()+","+maincoursemenu.get(i).getdescript());
				pw.flush();

			}
			for (int i=0;i<dessertmenu.size();i++){

				pw.println(1+","+dessertmenu.get(i).getname()+","+dessertmenu.get(i).getprice()+","+dessertmenu.get(i).getdescript());
				pw.flush();

			}
			for (int i=0;i<drinkmenu.size();i++){

				if(i==drinkmenu.size()-1) {
					pw.print(2+","+drinkmenu.get(i).getname()+","+drinkmenu.get(i).getprice()+","+drinkmenu.get(i).getdescript());
					break;
				}
				pw.println(2+","+drinkmenu.get(i).getname()+","+drinkmenu.get(i).getprice()+","+drinkmenu.get(i).getdescript());
				pw.flush();

			}
			pw.close();
			System.out.println("Saved Properly");
			return true;

		}catch(Exception e){
			System.out.println("Not Saved Properly");
			return false;
		}
	}

	/**
	 * Load the menu from a txt file. Only used for alacarte items.
	 *
	 * @param Loadto The menu controller that needs to be load onto
	 */
	public void LoadMenu(MenuController Loadto){
		//read File
		int type;
		String name;
		double price;
		String description;
		String file= "src/Menu.txt";
		String line="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			//Look for menu type
			//for each line
			line=br.readLine();
			while(line!=null) {
				String[] row=line.split(",");
				type=Integer.parseInt(row[0]);
				name=row[1];
				price=Double.parseDouble(row[2]);
				description=row[3];
				Loadto.addindividualitems(type, name, price, description);
				line=br.readLine();
			}
		} catch(Exception e){
			if (line == null)
				System.out.println("Error Loading Menu");
		}
	}
}