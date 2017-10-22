package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Categories 
{
	private SimpleIntegerProperty category_id;
	private SimpleStringProperty category;
	private SimpleStringProperty cat_status;
	
	
	public Categories(Integer category_id, String category,String cat_status) 
	{
		super();
		this.category_id = new SimpleIntegerProperty(category_id);
		this.category = new SimpleStringProperty(category);
		this.cat_status = new SimpleStringProperty(cat_status);
	}

	public Integer getCategory_id() 
	{
		return category_id.get();
	}
	public String getCategory() 
	{
		return category.get();
	}
	public String getCat_status() 
	{
		return cat_status.get();
	}
}
