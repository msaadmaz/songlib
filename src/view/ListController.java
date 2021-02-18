package view;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
	@FXML
	Button insert;
	@FXML
	Button delete;
	@FXML
	Button update;
	@FXML         
	TextField updateName;                
	@FXML         
	TextField updateArtist;
	@FXML         
	TextField updateAlbum;
	@FXML         
	TextField updateYear; 
	private ObservableList<String> obsList;  
	
	public SongList list;
	
	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
		try( Scanner sc = new Scanner(new File("songs.txt"))){
			list = new SongList(sc);
			obsList = FXCollections.observableArrayList(list.names);
			songDisplay.setItems(obsList); 
			
			// select the first item
			songDisplay.getSelectionModel().select(0);
			setTextDisplay(mainStage);
			
			// set listener for the items
			songDisplay
				.getSelectionModel()
				.selectedIndexProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						setTextDisplay(mainStage));
		}
		catch (FileNotFoundException e) {
			// need to add an alert here incase there isn't any file
			System.out.print("file not found");
			return;
		}
		
	}
	private void setTextDisplay(Stage mainStage) {
		int index = songDisplay.getSelectionModel().getSelectedIndex();
		Song selectedSong = list.list.get(index);
		display.setText("Name: " + selectedSong.name + " \n " +
						"Artist: " + selectedSong.artist + " \n  " +
						"Album: " + selectedSong.album + " \n " +
						"Year: " + selectedSong.year);
	}
	
	public void insertion(ActionEvent e) {
		// need to add alert if name or artist is null
		// need to add alert if nothing is in insertion text fields ie. all of them are null
		String songName = Name.getText();
		String artistName = Artist.getText();
		String albumName = Album.getText();
		int year = Integer.parseInt(Year.getText());
		obsList = list.add(songName, artistName, albumName, year, obsList);
	}
	
	public void deletion(ActionEvent e) {
		//need to add alert if deleting from obslist that has no more songs in it
		int index = songDisplay.getSelectionModel().getSelectedIndex();
		obsList.remove(index);
		list.remove(index);
	}
	
	public void update(ActionEvent e) {
		// need to add alert if update is clicked w/o any entries into the field
		int index = songDisplay.getSelectionModel().getSelectedIndex();
		obsList.remove(index);
		String songName = updateName.getText();
		String artistName = updateArtist.getText();
		String albumName = updateAlbum.getText();
		Integer year = Integer.parseInt(updateYear.getText());
		list.update(index, songName, artistName, albumName, year, obsList);
	}
	
	
	
}
