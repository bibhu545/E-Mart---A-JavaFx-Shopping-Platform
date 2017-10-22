package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CategoryMenu implements Initializable
{
	@FXML private TableView<Categories> cat_table;
	@FXML private TableColumn<Categories,Integer> category_id;
	@FXML private TableColumn<Categories,String> category;
	@FXML private TableColumn<Categories,String> cat_status;
	
	public ObservableList<Categories> list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		try 
		{
			getCategories();
		} 
		catch (Exception e2) 
		{
			e2.printStackTrace();
		}	
		
		category_id.setCellValueFactory(new PropertyValueFactory<Categories,Integer>("category_id"));
		category.setCellValueFactory(new PropertyValueFactory<Categories,String>("category"));
		cat_status.setCellValueFactory(new PropertyValueFactory<Categories,String>("cat_status"));
		
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String sql = "SELECT * FROM category ORDER BY category_id DESC";
		Statement st = null;
		
		try 
		{
			st = MakeConnection.con.createStatement();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				list.add(new Categories(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		cat_table.setItems(list);
	}
	
	
	public void refreshList(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("CategoryMenu.fxml").openStream());
		
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart - Admin:Category Area");
		primaryStage.show();
	}
	
	@FXML
	private TextField addcat;
	@FXML
	private Label add_cat_message;
	@FXML
	public void addCategory(ActionEvent event) throws Exception
	{
		String newcat = addcat.getText();
		String sql = "INSERT INTO category (category) VALUES('"+newcat+"')";
		Statement st = MakeConnection.con.createStatement();
		int result = st.executeUpdate(sql);
		if(result > 0)
		{
			addcat.setText("");
			add_cat_message.setTextFill(Color.FORESTGREEN);
			add_cat_message.setText("Category Added");
		}
		else
		{
			add_cat_message.setTextFill(Color.RED);
		}
	}
	
	
	
	@FXML
	private TextField delcat;
	@FXML
	private Label del_cat_message;
	@FXML
	public void deleteCategory(ActionEvent event) throws Exception
	{
		String catid = delcat.getText();
		if(CategoryMenu.tm.containsKey(Integer.parseInt(catid)))
		{
			String sql = "UPDATE category SET cat_status = 'deleted' WHERE category_id = "+catid;
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				delcat.setText("");
				del_cat_message.setTextFill(Color.RED);
				del_cat_message.setText("Category Deleted");
			}
			else
			{
				del_cat_message.setTextFill(Color.RED);
			}
		}
		else
		{
			del_cat_message.setText("Invalid Category Id");
		}
	}
	
	
	@FXML
	private TextField add_offer_id;
	@FXML
	private TextField offer_percent;
	@FXML
	private Label add_offer_message;

	public void addOffer(ActionEvent event) throws Exception
	{
		String cat_id = add_offer_id.getText();
		String off = offer_percent.getText();
		
		if(CategoryMenu.tm.containsKey(Integer.parseInt(cat_id)))
		{
			double d = 1-(Double.parseDouble(off)/100);
			String sql = "UPDATE products SET show_price = actual_price*"+d+" WHERE product_category = "+cat_id;
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				add_offer_id.setText("");
				offer_percent.setText("");
				add_offer_message.setTextFill(Color.GREEN);
				add_offer_message.setText("***Offer Applied***");
			}
			else
			{
				add_offer_message.setText("Database Error");
			}	
		}
		else
		{
			add_offer_message.setText("Invalid Category id");
		}
		
	}
	
	
	@FXML
	private TextField remove_offer_id;

	public void removeOffer(ActionEvent event) throws Exception
	{
		String cat_id = remove_offer_id.getText();
		
		if(CategoryMenu.tm.containsKey(Integer.parseInt(cat_id)))
		{
			String sql = "UPDATE products SET show_price = actual_price WHERE product_category = "+cat_id;
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				remove_offer_id.setText("");
				add_offer_message.setTextFill(Color.FORESTGREEN);
				add_offer_message.setText("Offer Removed");
			}
			else
			{
				add_offer_message.setText("Database Error");
			}	
		}
		else
		{
			add_offer_message.setText("Invalid Category id");
		}
		
	}
	
	
	static TreeMap<Integer, String> tm = new TreeMap<Integer,String>();
	
	
	@FXML	
	private Label offercategories;	
	@FXML	
	private Label delcategories;
	public void getCategories() throws Exception
	{
		
		String sql = "Select * from category where cat_status = 'active'";
		Statement st = MakeConnection.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
		{
			tm.put(rs.getInt(1), rs.getString(2));
		}
		delcategories.setText("Categories : "+tm.toString());
		offercategories.setText("Categories : "+tm.toString());
	}
	
	
	public void backToMenu(ActionEvent event) throws IOException
	{
		new ProductMenu().backToMenu(event);
	}
	public void logout(ActionEvent event) throws IOException
	{
		new ProductMenu().exit(event);
	}
}
