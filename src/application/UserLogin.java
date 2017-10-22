package application;

import java.io.IOException;
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

public class UserLogin 
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
	
	static int user_login_id = 0;
	
	public void userLogin(ActionEvent event) throws Exception
	{
		new MakeConnection();
		
		String txtuser = username.getText();
		String txtpassword = password.getText();
		
		String sql = "SELECT * FROM users WHERE user_email = '"+txtuser+"' and user_pass = '"+txtpassword+"'";
		Statement st = MakeConnection.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			error.setText("Success");
			user_login_id = rs.getInt(1);
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
