package Invoice;

import java.io.*;
import java.util.*;
import Menu.Food_items.*; 
import java.time.LocalDateTime;

/**
 * The Controller Class that deals with the actual functions of the Sales Report
 */
public class SaleReportController implements Serializable{
	/**
	 * Filepath to where the Sales Report will be stored at
	 */
	private final String FILEPATH = "./SalesReport_data/";
	/**
	 * HashMap of Item count
	 */
	private HashMap<String,Integer> Item_count = new HashMap<String,Integer>();
	/**
	 * Array of invoices for the day
	 */
	Invoice[] invoiceDay;//
	//private HashMap<Invoice,Integer> Invoice_list = new HashMap<Invoice,Integer>();
	/**
	 * Current DateTime
	 */
	private LocalDateTime currentDay = LocalDateTime.now();
	/**
	 * Revenue for the day
	 */
	double Revenue_day;
	/**
	 * Date stored in String format
	 */
	String date;
	/**
	 * Initialise InvoiceToTxt
	 */
	private InvoiceToTxt iTT = new InvoiceToTxt();
	/**
	 * Revenue over a period of time
	 */
	private double periodRevenue;
	/**
	 * Start period
	 */
	private LocalDateTime startPeriod;
	/**
	 * End period
	 */
	private LocalDateTime endPeriod;

	/**
	 * Constructor of the SaleReportController
	 */
	public SaleReportController(){
		this.Revenue_day=0;
	}

	/**
	 * Read the invoices for the day, combine price , add number of items sold to an Arraylist.
	 * @param c_date Date of the SalesReport
	 * @return SalesReport for the input date
	 */
	public SalesReport closeday(String c_date){
		Invoice invoice;
		if (c_date == null){
			invoiceDay = iTT.ReadInvoiceDay(iTT.getStringDate(currentDay));
		}
		else {
			invoiceDay = iTT.ReadInvoiceDay(c_date);
		}
		int count;
		Revenue_day = 0.0;
		for(int i=0;i<invoiceDay.length;i++){
			invoice = invoiceDay[i];
			for(Map.Entry<Fooditems,Integer> entry1: invoice.getorder_items().entrySet()){
				//if items in Item_count replace the value
				//if(Item_count==null){
				//   Item_count.put(entry1.getKey(),entry1.getValue());
				// }
				//else 
				if(Item_count.containsKey(entry1.getKey().getname())){
					count = entry1.getValue();
					count += Item_count.get(entry1.getKey().getname()); 
					Item_count.replace(entry1.getKey().getname(), count);
				}
				else{
					Item_count.put(entry1.getKey().getname(),entry1.getValue());
				}
			}
			//Debug System.out.println("invoice: "+i+" revenue: "+ invoiceDay[i].getTotalPrice());
			//Debug System.out.println("Revenue_day: "+ Revenue_day);
			Revenue_day += invoiceDay[i].getTotalPrice();
		}
		date = invoiceDay[0].getDate();
		SalesReport Report = new SalesReport (Revenue_day,Item_count);
		try{
			File f = new File(FILEPATH+date+"Report"+".txt");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(Report);
			os.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return Report;
	}

	/**
	 * Reading the SalesReport of the input date from the txt file
	 * @param r_date Date of SalesReport
	 * @return null
	 * @throws IOException In the event that the SalesReport txt file is unreadable or missing
	 */
	public SalesReport ReadSalesReportDay(String r_date) throws IOException {
		File file = new File(FILEPATH + r_date + "Report.txt");
		if (file.exists()){
			try{
				String f = FILEPATH + r_date+"Report.txt"; //"YYYY-MM-DDInvoice1.txt"
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
				SalesReport sr = (SalesReport)is.readObject();
				is.close();
				return sr;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("File not found on:\n"+r_date);
		}
		return null;
	}

	/**
	 * Gets the SalesReport over a given time frame
	 * @param startPeriod Starting period
	 * @param endPeriod Ending period
	 * @return SalesReport over a period of time
	 */
	public SalesReport SalesReportPeriod (LocalDateTime startPeriod, LocalDateTime endPeriod) {
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.periodRevenue = 0;
		int count = 0;
		HashMap<String,Integer> period_Item_count = new HashMap<String,Integer>();
		HashMap<String,Integer> c_Item_count;
		LocalDateTime c_date;
		String c_date_String;
		SalesReport c_SR = null;
		if (startPeriod.isBefore(endPeriod.plusDays(1)) == false){
			System.out.println("Error Generating SalesReport: StartDate after EndDate!");
			return null;
		}
		for (c_date = startPeriod; c_date.isBefore(endPeriod.plusDays(1)); c_date = c_date.plusDays(1)) { //idk if works need test
			try{ //try to read SalesReportDay Object
				c_date_String = iTT.getStringDate(c_date); //convert local date time to string date in correct format
				c_SR = ReadSalesReportDay(c_date_String);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println("Error Reading SalesReportDay dated: " + iTT.getStringDate(c_date));

			if (c_SR != null) {
				//update periodRevenue
				this.periodRevenue += c_SR.getRevenue();
				//System.out.println("Period Revenue"+ periodRevenue);
				//Iterate through HashMap for SalesReportDay, update HashMap period_Item_count
				c_Item_count = c_SR.getItemCount();
				for (Map.Entry<String,Integer> entry: c_Item_count.entrySet()) {
					//if the key already exists, update count value
					if(period_Item_count.containsKey(entry.getKey())){
						count = entry.getValue();
						count += period_Item_count.get(entry.getKey());
						period_Item_count.replace(entry.getKey(), count);
					}

					else{ //else initialize item in period_Item_count
						period_Item_count.put(entry.getKey(),entry.getValue());
					}
				}
			}
		}
		SalesReport SalesReportbyPeriod = new SalesReport(this.periodRevenue,period_Item_count);
		try{
			File f = new File(FILEPATH+iTT.getStringDate(startPeriod)+":"+iTT.getStringDate(endPeriod)+"ReportPeriod"+".txt");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(SalesReportbyPeriod);
			os.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return SalesReportbyPeriod;
	}

	/**
	 * Printing the SalesReport over a given time period
	 * @param salesReport SalesReports 
	 * @throws IOException In the event that the Sales Report is missing
	 */
	public void PrintSalesReportPeriod(SalesReport salesReport) throws IOException { 
		if (salesReport == null) return;

		String f = "PrintedSalesReportPeriod.txt";
		String startDate = iTT.getStringDate(this.startPeriod);
		String endDate = iTT.getStringDate(this.endPeriod);
		String totalSales = String.format("%.2f",salesReport.getRevenue());
		String itemHeader;
		try{
			PrintWriter writer = new PrintWriter(f);
			writer.println(String.format("\tSales Report\n"));
			writer.println("From: " + startDate);
			writer.println("To: " + endDate);
			writer.println("****************************************");
			writer.println("Ordered Items List");
			writer.println("****************************************");
			itemHeader = String.format("%-28s %8s\n", "Item", "Qty");
			writer.println(itemHeader);
			salesReport.getItemCount().forEach(
					(FoodName,v) -> writer.println(String.format("%-28s %8s\n", FoodName , v))
					);

			writer.println("----------------------------------------");  
			writer.println(String.format("%28s %8s\n", "Total Revenue: ",totalSales));
			writer.println("========================================\n");

			writer.flush();
			writer.close();
			System.out.println("Sales Report Period printed");      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Printing the SalesReport of the input date
	 * @param salesReport SalesReport
	 * @param startPeriod Starting DateTime of the SalesReport
	 * @throws IOException In the event that the Sales Report is missing
	 */
	public void PrintSalesReportDay(SalesReport salesReport, LocalDateTime startPeriod) throws IOException { //Outputs sales report for that day in readable.txt  format
		String f = "PrintedSalesReportDay.txt";
		String startDate = iTT.getStringDate(startPeriod);
		String totalSales = String.format("%.2f",salesReport.getRevenue());
		String itemHeader;
		try{
			PrintWriter writer = new PrintWriter(f);
			writer.println(String.format("\tSales Report\n"));
			writer.println("Date: " + startDate);
			writer.println("****************************************");
			writer.println("Ordered Items List");
			writer.println("****************************************");
			itemHeader = String.format("%-28s %8s\n", "Item", "Qty");
			writer.println(itemHeader);
			salesReport.getItemCount().forEach(
					(FoodName,v) -> writer.println(String.format("%-28s %8s\n",FoodName , v) )
					);

			writer.println("----------------------------------------");  
			writer.println(String.format("%28s %8s\n", "Total Revenue: ",totalSales));
			writer.println("========================================\n");

			writer.flush();
			writer.close();
			System.out.println("Sales Report Day printed");      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}