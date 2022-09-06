package Invoice;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Entity Class that deals with the data of the invoices
 */
public class InvoiceToTxt
{
	/*
	 * Filepath of where the text file will be stored at
	 */
	private final String FILEPATH = "./Invoice_data/";
	/*
	 * ID of the invocie
	 */
	private long invoice_id;
	/*
	 * Date in string format
	 */
	private String date_str;
	/*
	 * Invoice class
	 */
	private Invoice invoice;

	/**
	 * Constructor of InvoiceToTxt
	 */
	public InvoiceToTxt(){}

	/**
	 * Constructor of InvoiceToTxt with parameters
	 * @param invoice Invoice that needs to be saved into text
	 */
	public InvoiceToTxt(Invoice invoice){
		this.invoice = invoice;
		this.date_str = getStringDate();
		//this.invoice_id=invoice.getinvoiceid();
	}

	/**
	 * Gets the date of the invoice
	 * @return Date of the invoice in String type
	 */
	public String getStringDate(){
		LocalDateTime currentDateTime = invoice.getTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return currentDateTime.format(formatter);
	}

	/**
	 * Gets the date of the invoice (overloading)
	 * @param currentDate DateTime of the input date
	 * @return Date of the invoice in String type
	 */
	public String getStringDate(LocalDateTime currentDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return currentDate.format(formatter);
	}//overloading

	/**
	 * Getting an invoice ID that exists
	 */
	private void GetValidInvoiceId() {
		File f;
		invoice_id = 0;
		do {
			invoice_id += 1;
			f = new File(FILEPATH+date_str+"Invoice"+ invoice_id + ".txt");
		} while(f.exists());
	}

	/**
	 * Getting an invoice ID that exists
	 * @param r_date Date of the invoice
	 * @return Invoice ID
	 */
	private long GetValidInvoiceId(String r_date) {
		File f;
		invoice_id = 0;
		do {
			invoice_id += 1;
			f = new File(FILEPATH+r_date+"Invoice"+ invoice_id + ".txt");
		} while(f.exists());
		return invoice_id - 1;
	}

	/**
	 * Creating Invoice
	 * @throws IOException Unable to create invoice into txt
	 */
	public void CreateInvoice()  throws IOException {
		SaleReportController SRC = new SaleReportController();
		this.GetValidInvoiceId();
		try{
			File f = new File(FILEPATH+date_str+"Invoice"+invoice_id+".txt");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(this.invoice);
			os.close();
			SRC.closeday(date_str);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reading invoice 
	 * @param r_date Date of the invoice
	 * @param r_id Invoice ID
	 * @return Invoice that is being called
	 * @throws IOException Unable to save invoice to txt
	 */
	public Invoice ReadInvoice(String r_date, String r_id) throws IOException {//returns null if invoice not found
		try{
			String f = FILEPATH + r_date + "Invoice" + r_id + ".txt"; //"YYYY-MM-DDInvoice1.txt"
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
			Invoice i = (Invoice)is.readObject();
			is.close();
			return i;
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
		System.out.println("Error Reading Invoice");
		return null;
	}

	/**
	 * Looks for last invoice_id on a particular date, Initialize invoice array, Reads Invoice into array
	 * @param r_date Date of invoice
	 * @return Array of invoices
	 */
	public Invoice[] ReadInvoiceDay(String r_date) { //Looks for last invoice_id on a particular date, Initialize invoice array, Reads Invoice into array
		int numInvoice = (int)this.GetValidInvoiceId(r_date);
		Invoice[] invoiceDay = new Invoice[numInvoice];
		for (int i = 0; i < numInvoice; i += 1){
			try{
				invoiceDay[i] = ReadInvoice(r_date, String.valueOf(i+1));
			}
			catch(IOException e) {
				e.printStackTrace();
				System.out.println("Error Reading Invoice Dated:"+ r_date+"\nInvoice ID: "+ String.valueOf(i+1));
				return null;
			}
		}
		return invoiceDay;
	}

	/**
	 * Prints the invoice out in the form of a receipt
	 * @param invoice Invoice that is to be printed
	 * @throws IOException Unable to find the invoice
	 */
	public void PrintReceipt(Invoice invoice) throws IOException {
		String f = "PrintedReceipt.txt";
		String restaurantName = invoice.getRestaurantName();
		String address = invoice.getAddress(); //Need to take in address
		String phoneNumber = invoice.getPhoneNumber();
		String serverName=invoice.getServer();
		String itemHeader;
		String tableNo=invoice.getTableNum();

		LocalDateTime dateTime = invoice.getTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String dateReceipt= dateTime.format(formatter);
		String subTotal = invoice.getSubtotal();
		String serviceCharge = String.valueOf( invoice.getServiceCharge() );
		String gst = invoice.getGST();
		String totalCost= String.valueOf( invoice.getTotalPrice() );
		String membershipDiscount = String.valueOf( invoice.getMembershipDiscount() );
		try{
			PrintWriter writer = new PrintWriter(f);
			writer.println(String.format("\t\t"+ restaurantName + "\n"));
			writer.println("****************************************\n");
			writer.println(address);
			writer.println("\tPhone: " + phoneNumber +"\n");
			writer.println("Server: " + serverName);
			writer.println("Table: " + tableNo);
			writer.println("Date: " + dateReceipt+"\n");
			writer.println("----------------------------------------");
			itemHeader = String.format("%-21s %5s %10s\n", "Item", "Qty", "Price");
			writer.println(itemHeader);
			invoice.final_items.forEach(
					(Fooditems,v) -> writer.println(String.format("%-21s %5s %10s\n",Fooditems.getname() , v, Fooditems.getprice())));
			if(invoice.getMembership()){
				itemHeader = String.format("%28s %8s\n", "Membership Discount:", membershipDiscount);
				writer.println(itemHeader);
			}
			writer.println("");
			writer.println("----------------------------------------");
			itemHeader = String.format("%28s %8s\n", "Subtotal:", subTotal);
			writer.println(itemHeader);
			itemHeader = String.format("%28s %8s\n", "Service Charge:", serviceCharge);
			writer.println(itemHeader);
			itemHeader = String.format("%28s %8s\n", "GST:", gst);
			writer.println(itemHeader);
			writer.println("----------------------------------------");
			itemHeader = String.format("%28s %8s\n", "Total Cost:", totalCost);
			writer.println(itemHeader);
			writer.println("========================================\n");
			writer.println("****************************************");
			writer.println("**** Thank you for dining with us! *****");
			writer.println("****************************************");
			writer.flush();
			writer.close();
			System.out.println("Receipt printed");      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}
}