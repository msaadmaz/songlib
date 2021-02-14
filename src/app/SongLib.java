package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import structures.SongList;
import view.ListController;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws Exception{
		try( Scanner sc = new Scanner(new File("songs.txt"))){
			SongList list = new SongList(sc);
			list.printList();
		} catch (FileNotFoundException e) {
			System.out.print("file not found");
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
		getClass().getResource("/view/gui.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		ListController ListController =
				loader.getController();
		ListController.start(primaryStage);
		Scene scene = new Scene(root, 200, 300);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
