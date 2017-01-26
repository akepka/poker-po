package application;
	

import game.*;
import players.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Poker extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			Controller controller = loader.getController();
			Scene scene = new Scene(root,690,473);
			
			//Controller controller = root.getCo
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			
			
			
			
			
			
			
			controller.print("Witam Pañstwa guptaki\n");
			controller.print("Witam Pañstwa guptaki\n");
			controller.print("Witam Pañstwa guptaki\n");
			controller.println("Witam Pañstwa guptaki");
			controller.print("Witam Pañstwa guptaki\n");
			controller.print("Witam Pañstwa guptaki\n");
			controller.print("Witam Pañstwa guptaki\n");
			controller.print("Witam Pañstwa guptaki\n");
			controller.getButton().setDisable(true);
			
			Player player = new Player("Andrzej", 1000);
			Computer computer = new Computer("Computer", 1000);
			
			Game game = new Game(player, computer);
			
			
			game.start();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
