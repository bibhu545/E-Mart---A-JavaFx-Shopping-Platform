package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mysql.jdbc.StringUtils;

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

public class UserArea implements Initializable 
{
	@FXML
	private TextField userid;	
	@FXML
	private TextField username;	
	@FXML
	private TextField useremail;	
	@FXML
	private TextField userpass;
	@FXML
	private Label profilemessage;
	
	@FXML private TableView<Products> table;
	@FXML private TableColumn<Products,Integer> product_id;
	@FXML private TableColumn<Products,String> product_name;
	@FXML private TableColumn<Products,Integer> actual_price;
	@FXML private TableColumn<Products,Integer> show_price;
	@FXML private TableColumn<Products,Integer> product_qty;
	@FXML private TableColumn<Products,String> product_category;
	@FXML private TableColumn<Products,String> product_status;
	public ObservableList<Products> list = FXCollections.observableArrayList();
	
	
	@FXML private TableView<Business> bus_table;
	@FXML private TableColumn<Business,Integer> business_id;
	@FXML private TableColumn<Business,String> product_name_bus;
	@FXML private TableColumn<Business,String> purchase_date;
	@FXML private TableColumn<Business,String> product_status_bus;
	public ObservableList<Business> bus_list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		
		product_id.setCellValueFactory(new PropertyValueFactory<Products,Integer>("product_id"));
		product_name.setCellValueFactory(new PropertyValueFactory<Products,String>("product_name"));
		actual_price.setCellValueFactory(new PropertyValueFactory<Products,Integer>("actual_price"));
		show_price.setCellValueFactory(new PropertyValueFactory<Products,Integer>("show_price"));
		product_qty.setCellValueFactory(new PropertyValueFactory<Products,Integer>("product_qty"));
		product_category.setCellValueFactory(new PropertyValueFactory<Products,String>("product_category"));
		product_status.setCellValueFactory(new PropertyValueFactory<Products,String>("product_status"));
		
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String sql = "SELECT * FROM products INNER JOIN category on products.product_category = category.category_id WHERE product_status = 'active' ORDER BY product_id DESC";
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
				int act = rs.getInt(3);
				int show = rs.getInt(4);
				String str;
				if(act > show)
				{
					str = "On Offer";
				}
				else
				{
					str = "No Offer";
				}
				list.add(new Products(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(9), str));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		table.setItems(list);
		
		
		sql = "SELECT * FROM users WHERE user_id = "+UserLogin.user_login_id;
		st = null;
		ResultSet rs = null;
		try 
		{
			st = MakeConnection.con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				userid.setText(String.valueOf(rs.getInt(1)));
				username.setText(rs.getString(2));
				useremail.setText(rs.getString(4));
				userpass.setText(rs.getString(3));
				profilemessage.setText("");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		business_id.setCellValueFactory(new PropertyValueFactory<Business,Integer>("business_id"));
		product_name_bus.setCellValueFactory(new PropertyValueFactory<Business,String>("product_name"));
		purchase_date.setCellValueFactory(new PropertyValueFactory<Business,String>("purchase_date"));
		product_status_bus.setCellValueFactory(new PropertyValueFactory<Business,String>("product_status"));
		
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		sql = "SELECT * FROM business inner join products on business.bus_product_id = products.product_id WHERE bus_user_id = "+UserLogin.user_login_id;
		
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
			rs = null;
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				bus_list.add(new Business(rs.getInt(1), rs.getString(6),rs.getString(4),rs.getString(11)));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		bus_table.setItems(bus_list);
		
	}
	
	public void editProfile(ActionEvent event)
	{
		username.setDisable(false);
		useremail.setDisable(false);
		userpass.setDisable(false);
	}
	
	public void updateProfile(ActionEvent event) throws Exception
	{
		String name = username.getText();
		String email = useremail.getText();
		String pass = userpass.getText();
		
		if(name.isEmpty())
		{
			profilemessage.setText("Name can not be empty");
		}
		else if(email.isEmpty())
		{
			profilemessage.setText("Email can not be empty");
		}
		else if(pass.isEmpty())
		{
			profilemessage.setText("Password can not be empty");
		}
		else if(pass.length() < 4)
		{
			profilemessage.setText("Password should contain at least 4 chars");
		}
		else if(useremail.isDisabled() || username.isDisable())
		{
			profilemessage.setText("Nothing Changed");
		}
		else
		{
			String sql = "UPDATE users SET user_name = '"+name+"',user_email = '"+email+"',user_pass = '"+pass+"' WHERE user_id = "+UserLogin.user_login_id;
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				username.setDisable(true);
				useremail.setDisable(true);
				userpass.setDisable(true);
				profilemessage.setTextFill(Color.GREEN);
				profilemessage.setText("***Profile Updated***");
			}
			else
			{
				profilemessage.setTextFill(Color.RED);
				profilemessage.setText("Database Error");
			}
		}
	}
	
	@FXML
	private TextField searchid;
	@FXML
	private TextField searchname;
	@FXML
	private Label productname;
	@FXML
	private Label actualprice;
	@FXML
	private Label showprice;
	@FXML
	private Label productqty;
	@FXML
	private Label productstatus;
	@FXML
	private Label productcat;
	@FXML
	private Label error;
	@FXML
	private Label productid;
	@FXML
	private Label searchproductid;
	
	static int id = 0;
	static int qty = 0;
	
	public void searchById(ActionEvent event)
	{
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		if(!StringUtils.isStrictlyNumeric(searchid.getText()))
		{
			error.setText("Not a valid id");
		}
		else
		{
			String sql = "SELECT * FROM products inner join category on category.category_id = products.product_category where product_id = "+Integer.parseInt(searchid.getText());
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
				if(rs.next())
				{
					error.setText("");
					searchname.setText("");
					searchproductid.setText(String.valueOf(rs.getInt(1)));
					productname.setText(rs.getString(2));
					actualprice.setText(String.valueOf(rs.getInt(3)));
					showprice.setText(String.valueOf(rs.getInt(4)));
					productqty.setText(String.valueOf(rs.getInt(5))+" Units");
					productcat.setText(rs.getString(9));
					
					UserArea.id = rs.getInt(1);
					UserArea.qty = rs.getInt(5);
					
				}
				else
				{
					searchproductid.setText("");
					productname.setText("");
					actualprice.setText("");
					showprice.setText("");
					productqty.setText("");
					productcat.setText("");
					
					UserArea.id = 0;
					UserArea.qty = 0;
					error.setTextFill(Color.RED);
					error.setText("Sorry...No Product at this Id");
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}	
		}
	}
	public void searchByName(ActionEvent event)
	{
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String text = searchname.getText();
		String sql = "SELECT * FROM products inner join category on category.category_id = products.product_category where product_name = '"+text+"'";
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
			if(rs.next())
			{
				error.setText("");
				searchid.setText("");
				searchproductid.setText(String.valueOf(rs.getInt(1)));
				productname.setText(rs.getString(2));
				actualprice.setText(String.valueOf(rs.getInt(3)));
				showprice.setText(String.valueOf(rs.getInt(4)));
				productqty.setText(String.valueOf(rs.getInt(5))+" Units");
				productcat.setText(rs.getString(9));
				
				UserArea.id = rs.getInt(1);
				UserArea.qty = rs.getInt(5);
				
			}
			else
			{
				searchproductid.setText("");
				productname.setText("");
				actualprice.setText("");
				showprice.setText("");
				productqty.setText("");
				productcat.setText("");
				
				UserArea.id = 0;
				UserArea.qty = 0;
				error.setTextFill(Color.RED);
				error.setText("Sorry...No Product at this Id");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}


	public void buyNow(ActionEvent event) throws SQLException
	{
		System.out.println(UserArea.id);
		if(UserArea.id != 0)
		{
			int new_qty = UserArea.qty-1;
			
			String sql = "UPDATE products SET product_qty = '"+new_qty+"' WHERE product_id = "+UserArea.id;
			Statement st = MakeConnection.con.createStatement();
			int temp = st.executeUpdate(sql);
			
			sql = "INSERT INTO business(bus_product_id,bus_user_id) VALUES('"+id+"','"+UserLogin.user_login_id+"')";
			st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			
			if(result > 0 && temp > 0)
			{
				error.setTextFill(Color.GREEN);
				error.setText("***Purchased***");
			}
			else
			{
				error.setTextFill(Color.RED);
				error.setText("Database Error...Try again");
			}
		}
		else
		{
			error.setTextFill(Color.RED);
			error.setText("Sorry..Product Not Available");
		}
	}	
	
	public void reloadProducts(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("UserArea.fxml").openStream());
		
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart - User Area");
		primaryStage.show();
	}
	
	public void logout(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("UserLogin.fxml").openStream());
		
		Scene scene = new Scene(root,500,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart - User Area");
		primaryStage.show();
	}
	public void exit(ActionEvent event)
	{
		System.exit(0);
	}
	
}
