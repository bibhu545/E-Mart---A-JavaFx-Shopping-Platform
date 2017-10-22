package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProductMenu implements Initializable
{
	@FXML private TableView<Products> table;
	@FXML private TableColumn<Products,Integer> product_id;
	@FXML private TableColumn<Products,String> product_name;
	@FXML private TableColumn<Products,Integer> actual_price;
	@FXML private TableColumn<Products,Integer> show_price;
	@FXML private TableColumn<Products,Integer> product_qty;
	@FXML private TableColumn<Products,String> product_category;
	@FXML private TableColumn<Products,String> product_status;
	public ObservableList<Products> list = FXCollections.observableArrayList();
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
		String sql = "SELECT * FROM products INNER JOIN category on products.product_category = category.category_id ORDER BY product_id DESC";
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
				list.add(new Products(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(9), rs.getString(7)));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		table.setItems(list);
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
	@FXML
	private TextField editsearchid;
	@FXML
	private TextField editsearchname;
	@FXML
	private TextField editproductname;
	@FXML
	private TextField editactualprice;
	@FXML
	private TextField editshowprice;
	@FXML
	private TextField editproductqty;
	@FXML
	private TextField editproductcat;
	@FXML
	private Label editerror;
	
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
					productqty.setText(String.valueOf(rs.getInt(5)));
					productcat.setText(rs.getString(9));
					productstatus.setText(rs.getString(7));
				}
				else
				{
					searchproductid.setText("");
					productname.setText("");
					actualprice.setText("");
					showprice.setText("");
					productqty.setText("");
					productcat.setText("");
					productstatus.setText("");
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
				productqty.setText(String.valueOf(rs.getInt(5)));
				productcat.setText(rs.getString(9));
				productstatus.setText(rs.getString(7));
			}
			else
			{
				searchproductid.setText("");
				productname.setText("");
				actualprice.setText("");
				showprice.setText("");
				productqty.setText("");
				productcat.setText("");
				productstatus.setText("");
				error.setText("Sorry...No Product at this Id");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void editProductById()
	{
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String text = editsearchid.getText();
		String sql = "SELECT * FROM products where product_id = "+Integer.parseInt(editsearchid.getText());
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
				editerror.setText("");
				editsearchname.setText("");
				productid.setText(String.valueOf(rs.getInt(1)));
				editproductname.setText(rs.getString(2));
				editactualprice.setText(String.valueOf(rs.getInt(3)));
				editshowprice.setText(String.valueOf(rs.getInt(4)));
				editproductqty.setText(String.valueOf(rs.getInt(5)));
				editproductcat.setText(rs.getString(6));
			}
			else
			{
				editproductname.setText("");
				editactualprice.setText("");
				editshowprice.setText("");
				editproductqty.setText("");
				editproductcat.setText("");
				editsearchid.setText("");
				editerror.setText("Sorry...No Product at this Id");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void editProductByName()
	{
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String text = editsearchname.getText();
		String sql = "SELECT * FROM products where product_name = '"+text+"'";
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
				editerror.setText("");
				editsearchid.setText("");
				productid.setText(String.valueOf(rs.getInt(1)));
				editproductname.setText(rs.getString(2));
				editactualprice.setText(String.valueOf(rs.getInt(3)));
				editshowprice.setText(String.valueOf(rs.getInt(4)));
				editproductqty.setText(String.valueOf(rs.getInt(5)));
				editproductcat.setText(rs.getString(6));
			}
			else
			{
				editproductname.setText("");
				editactualprice.setText("");
				editshowprice.setText("");
				editproductqty.setText("");
				editproductcat.setText("");
				editsearchname.setText("");
				editerror.setText("Sorry...No Product at this Name");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void updateProduct(ActionEvent event)
	{
		
		try 
		{
			new MakeConnection();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		String name = editproductname.getText();
		String actualprice = editactualprice.getText();
		String showprice = editshowprice.getText();
		String productqty = editproductqty.getText();
		String productcat = editproductcat.getText();
		String sql = "SELECT * FROM category WHERE cat_status = 'active'";
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
			ArrayList<Integer> cat = new ArrayList<Integer>();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				cat.add(rs.getInt(1));
			}
			if(!cat.contains(Integer.parseInt(productcat)))
			{
				editerror.setText("Invalid Category...Available : "+cat);
			}
			else
			{
				sql = "Update products set product_name = '"+name+"',actual_price = "+actualprice+",show_price = "+showprice+",product_qty = "+productqty+",product_category = "+productcat+" WHERE product_id = "+productid.getText();
				int res = st.executeUpdate(sql);
				if(res > 0)
				{
					editsearchid.setText("");
					productid.setText("");
					editsearchname.setText("");
					editproductname.setText("");
					editactualprice.setText("");
					editshowprice.setText("");
					editproductqty.setText("");
					editproductcat.setText("");
					editsearchname.setText("");
					editerror.setText("Product Updated");
				}
				else
				{
					editerror.setText("Database Error");
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(ActionEvent event) throws Exception
	{
		String id = searchproductid.getText();
		if(id.isEmpty())
		{
			error.setText("Product Empty...");
		}
		else
		{
			String sql = "Update products set product_status = 'deleted' where product_id = "+id;
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				searchid.setText("");
				searchproductid.setText("");
				productname.setText("");
				actualprice.setText("");
				showprice.setText("");
				productqty.setText("");
				productcat.setText("");
				productstatus.setText("");
				error.setTextFill(Color.RED);
				error.setText("Product Deleted...");
			}
			else
			{
				error.setText("Database Error...Try Again");
			}	
		}
	}
	
	@FXML
	private Label categories;
	@FXML
	private Label insertmessage;
	@FXML
	private TextField getname;
	@FXML
	private TextField getactualprice;
	@FXML
	private TextField getshowprice;
	@FXML
	private TextField getqty;
	@FXML
	private TextField getcategory;
	public void getCategories() throws Exception
	{
		
		String sql = "Select * from category where cat_status = 'active'";
		Statement st = MakeConnection.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		TreeMap<Integer, String> tm = new TreeMap<Integer,String>();
		while(rs.next())
		{
			tm.put(rs.getInt(1), rs.getString(2));
		}
		categories.setText("Categories : "+tm.toString());
	}
	public void addProduct(ActionEvent event) throws Exception
	{
		String name = getname.getText();
		String act_price = getactualprice.getText();
		String show_price = getshowprice.getText();
		String qty = getqty.getText();
		String cat = getcategory.getText();

		
		if(name.isEmpty())
		{
			insertmessage.setTextFill(Color.RED);
			insertmessage.setText("Name can not be empty");
		}
		else if(act_price.isEmpty())
		{
			insertmessage.setTextFill(Color.RED);
			insertmessage.setText("Actual Price can not be empty");
		}		
		else if(show_price.isEmpty())
		{
			insertmessage.setTextFill(Color.RED);
			insertmessage.setText("Show Price can not be empty");
		}		
		else if(qty.isEmpty())
		{
			insertmessage.setTextFill(Color.RED);
			insertmessage.setText("Quantity can not be empty");
		}
		else if(cat.isEmpty())
		{
			insertmessage.setTextFill(Color.RED);
			insertmessage.setText("Category can not be empty");
		}
		else
		{
			String sql = "INSERT INTO products (product_name,actual_price,show_price,product_qty,product_category)\r\n" + 
					"VALUES('"+name+"',"+act_price+","+show_price+","+qty+","+cat+")";
			Statement st = MakeConnection.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0)
			{
				insertmessage.setTextFill(Color.GREEN);
				insertmessage.setText("Product Inserted");;
				getname.setText("");
				getactualprice.setText("");
				getshowprice.setText("");
				getqty.setText("");
				getcategory.setText("");
			}
			else
			{
				insertmessage.setText("Database Error...");
			}
			
		}
	}
	
	public void reloadTable(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("ProductMenu.fxml").openStream());
		
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart - Admin Area");
		primaryStage.show();
	}
	
	public void backToMenu(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("AdminArea.fxml").openStream());
		System.out.println("Hello");
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart - Admin Area");
		primaryStage.show();
	}
	public void exit(ActionEvent event) throws IOException
	{
		System.exit(0);
	}
}
