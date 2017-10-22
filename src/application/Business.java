package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Business 
{
	private SimpleIntegerProperty business_id;
	private SimpleStringProperty product_name;
	private SimpleStringProperty purchase_date;
	private SimpleStringProperty product_status;
	public Business(Integer business_id, String product_name,String purchase_date, String product_status) 
	{
		super();
		this.business_id = new SimpleIntegerProperty(business_id);
		this.product_name = new SimpleStringProperty(product_name);
		this.purchase_date = new SimpleStringProperty(purchase_date);
		this.product_status = new SimpleStringProperty(product_status);
	}
	public Integer getBusiness_id() 
	{
		return business_id.get();
	}
	public String getProduct_name() 
	{
		return product_name.get();
	}
	public String getPurchase_date() 
	{
		return purchase_date.get();
	}
	public String getProduct_status() 
	{
		return product_status.get();
	}
}
