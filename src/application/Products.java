package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products 
{
	private SimpleIntegerProperty show_price;
	private SimpleIntegerProperty product_qty;;
	private SimpleStringProperty product_category;
	private SimpleStringProperty product_status;
	private SimpleIntegerProperty product_id;
	private SimpleStringProperty product_name;
	private SimpleIntegerProperty actual_price;
	public Products(Integer product_id, String product_name, Integer actual_price, Integer show_price,
			Integer product_qty, String product_category, String product_status) 
	{
		super();
		this.product_id = new SimpleIntegerProperty(product_id);
		this.product_name = new SimpleStringProperty(product_name);
		this.actual_price = new SimpleIntegerProperty(actual_price);
		this.show_price = new SimpleIntegerProperty(show_price);
		this.product_qty = new SimpleIntegerProperty(product_qty);
		this.product_category = new SimpleStringProperty(product_category);
		this.product_status = new SimpleStringProperty(product_status);
	}
	
	public Integer getProduct_id()  
	{
		return product_id.get();
	}
	public String getProduct_name() 
	{
		return product_name.get();
	}
	public Integer getActual_price() 
	{
		return actual_price.get();
	}
	public Integer getShow_price() 
	{
		return show_price.get();
	}
	public Integer getProduct_qty() 
	{
		return product_qty.get();
	}
	public String getProduct_category() 
	{
		return product_category.get();
	}
	public String getProduct_status() 
	{
		return product_status.get();
	}
}
