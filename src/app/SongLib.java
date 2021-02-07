package app;

import javafx.application.Application;
import javafx.stage.Stage;
import structures.SongList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class SongLib extends Application {
	
	public void start(Stage primaryStage) {
		try( Scanner sc = new Scanner(new File("songs.txt"))){
			SongList list = new SongList(sc);
			list.printList();
		} catch (FileNotFoundException e) {
			System.out.print("file not found");
			return;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
