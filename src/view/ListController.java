package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import structures.Song;
import structures.SongList;

public class ListController {
	@FXML         
	TextField Name;                
	@FXML         
	TextField Artist;
	@FXML         
	TextField Album;
	@FXML         
	TextField Year;             
	@FXML         
	ListView<String> songDisplay;
	@FXML
	Text display;
	private ObservableList<String> obsList;              
	
	
	
	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
		try( Scanner sc = new Scanner(new File("songs.txt"))){
			SongList list = new SongList(sc);
			obsList = FXCollections.observableArrayList(list.names);
			songDisplay.setItems(obsList); 

			// select the first item
			songDisplay.getSelectionModel().select(0);

			// set listener for the items
			songDisplay
				.getSelectionModel()
				.selectedIndexProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						setTextDisplay(mainStage, list));
		} catch (FileNotFoundException e) {
			System.out.print("file not found");
			return;
		}
		
	}
	private void setTextDisplay(Stage mainStage, SongList list) {
		int index = songDisplay.getSelectionModel().getSelectedIndex();
		Song selectedSong = list.list.get(index);
		display.setText("Name: " + selectedSong.name + " \n " +
						"Artist: " + selectedSong.artist + " \n  " +
						"Album: " +selectedSong.album + " \n " +
						"Year: " +selectedSong.year);
		
	}
//	private void showItem(Stage mainStage) {                
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.initOwner(mainStage);
//		alert.setTitle("List Item");
//		alert.setHeaderText(
//				"Selected list item properties");

//		String content = "Index: " + 
//				songDisplay.getSelectionModel()
//		.getSelectedIndex() + 
//		"\nValue: " + 
//		songDisplay.getSelectionModel()
//		.getSelectedItem();

//		alert.setContentText(content);
//		alert.showAndWait();
//	}
	/*
	 * private void showItemInputDialog(Stage mainStage) { String item =
	 * songDisplay.getSelectionModel().getSelectedItem(); int index =
	 * songDisplay.getSelectionModel().getSelectedIndex();
	 * 
	 * TextInputDialog dialog = new TextInputDialog(item);
	 * dialog.initOwner(mainStage); dialog.setTitle("List Item");
	 * dialog.setHeaderText("Selected Item (Index: " + index + ")");
	 * dialog.setContentText("Enter name: ");
	 * 
	 * Optional<String> result = dialog.showAndWait(); if (result.isPresent()) {
	 * obsList.set(index, result.get()); } }
	 */
}
