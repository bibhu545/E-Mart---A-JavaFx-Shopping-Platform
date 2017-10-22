package application;

import java.io.IOException;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserSignUp 
{
	@FXML
	private TextField username;	
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField repassword;
	@FXML
	private Button back;
	@FXML
	private Button login;
	@FXML
	private Label error;
	@FXML
	public void userSignUp(ActionEvent event) throws Exception
	{
		new MakeConnection();
		String txtusername = username.getText();
		String useremail = email.getText();
		String repass = repassword.getText();
		String txtpassword = password.getText();
		
		if(txtusername.isEmpty())
		{
			error.setText("Name can not be empty");
		}
		else if(useremail.isEmpty())
		{
			error.setText("Email can not be empty");
		}
		else if(txtpassword.isEmpty())
		{
			error.setText("Password can not be empty");
		}
		else if(repass.isEmpty())
		{
			error.setText("Please re-enter password");
		}
		else
		{
			if(txtpassword.equals(repass))
			{
				String sql = "Insert into users (user_name,user_email,user_pass) values('"+txtusername+"','"+useremail+"','"+txtpassword+"')";
				Statement st = MakeConnection.con.createStatement();
				int rs = st.executeUpdate(sql);
				if(rs > 0)
				{
					error.setText("Success");
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
				else
					error.setText("Invalid");
			}
			else
			{
				error.setTextFill(Color.RED);
				error.setText("Passwords did not match");
			}
		}
	

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
