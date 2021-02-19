package app;

import javafx.application.Application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import structures.SongList;
import view.ListController;


public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
		getClass().getResource("/view/gui.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		ListController ListController =
				loader.getController();
		ListController.start(primaryStage);
		
		Scene scene = new Scene(root, 750, 750);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
		primaryStage.setOnCloseRequest(event -> {
			SongList list = ListController.getSongList();
			File songs = new File("songs.txt");
			try {
				songs.createNewFile();
				FileWriter myWriter = new FileWriter("songs.txt");
				for(int i = 0; i < list.getSize(); i++) {
					String line = list.list.get(i).name + "|" + list.list.get(i).artist + "|" + list.list.get(i).album + "|" + list.list.get(i).year + "\n";
					myWriter.write(line);
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
