package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminArea 
{
	@FXML
	public void productMenu(ActionEvent event) throws Exception
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
	@FXML
	public void categoryMenu(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader .load(getClass().getResource("CategoryMenu.fxml").openStream());
		
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
	
	@FXML
	public void SeeStats(ActionEvent event)
	{
		
	}
}
