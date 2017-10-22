package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminControls 
{
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button back;
	@FXML
	private Button login;
	@FXML
	private Label error;
	@FXML
	public void adminLogin(ActionEvent event) throws Exception
	{
		new MakeConnection();
		String txtuser = username.getText();
		String txtpassword = password.getText();
		
		String sql = "SELECT * FROM admin_master WHERE admin_username = '"+txtuser+"' and admin_pass = '"+txtpassword+"'";
		Statement st = MakeConnection.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			error.setText("Success");
			
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader .load(getClass().getResource("AdminArea.fxml").openStream());
			
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Emart - Admin Area");
			primaryStage.show();
			
		}
		else
			error.setText("Invalid");
	}
	@FXML
	public void back(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader .load(getClass().getResource("Choice.fxml").openStream());
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
