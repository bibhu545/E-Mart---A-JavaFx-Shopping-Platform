package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChoiceControl 
{
	@FXML
	private Button adminlogin;
	@FXML
	private Button userlogin;
	@FXML
	private Button sellerlogin;
	@FXML
	private Button usersignup;
	@FXML
	private Button sellersignup;
	@FXML
	public void adminLogin(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader .load(getClass().getResource("AdminLogin.fxml").openStream());
		Scene scene = new Scene(root,500,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart :: Admin Login");
		primaryStage.show();
	}
	@FXML
	public void userLogin(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader .load(getClass().getResource("UserLogin.fxml").openStream());
		Scene scene = new Scene(root,500,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart :: User Login");
		primaryStage.show();
	}
	@FXML
	public void sellerLogin(ActionEvent event)
	{
		
	}
	@FXML
	public void userSignUp(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader .load(getClass().getResource("UserSignUp.fxml").openStream());
		Scene scene = new Scene(root,500,450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Emart :: User Login");
		primaryStage.show();
	}
	@FXML
	public void sellerSignup(ActionEvent event)
	{
		
	}
	@FXML
	public void exit(ActionEvent event)
	{
		System.exit(0);
	}
}
