package view;

import java.io.File;


import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
			File songFile = new File("songs.txt");
			list = new SongList(sc);
			songFile.delete();
			sc.close();
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
			
		} catch (FileNotFoundException e) {
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
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Insert");
		alert.setContentText("Are you sure you want to insert?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if(Name.getText().trim().isEmpty() && Artist.getText().trim().isEmpty()) {
				Alert errorBoth = new Alert(AlertType.ERROR);
				errorBoth.setContentText("Please input a name and artist");
				errorBoth.showAndWait();
			}
			else if(Name.getText().trim().isEmpty()) {
				Alert errorName = new Alert(AlertType.ERROR);
				errorName.setContentText("Please input a name");
				errorName.showAndWait();
			}
			else if(Artist.getText().trim().isEmpty()) {
				Alert errorAlbum = new Alert(AlertType.ERROR);
				errorAlbum.setContentText("Please input an album");
				errorAlbum.showAndWait();
			}
			else {
				
				try {
					int year = Integer.parseInt(Year.getText());
					String songName = Name.getText();
					String artistName = Artist.getText();
					String albumName = Album.getText();
					obsList = list.add(songName, artistName, albumName, year, obsList);
					songDisplay.getSelectionModel().select(obsList.indexOf(songName));
					display.setText("Name: " + songName + " \n " +
							"Artist: " + artistName + " \n  " +
							"Album: " + albumName + " \n " +
							"Year: " + year);
					
				} catch( NumberFormatException n ) {
					if( Year.getText().trim().isEmpty() ) {
						int year = 0;
						String songName = Name.getText();
						String artistName = Artist.getText();
						String albumName = Album.getText();
						obsList = list.add(songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					} else {
						Alert yearError = new Alert(AlertType.ERROR);
						yearError.setContentText("Please enter in a valid year");
						yearError.showAndWait();
					}
				}
				
			}
			
		 }
	}
	
	public void deletion(ActionEvent e) {
		//need to add alert if deleting from obslist that has no more songs in it
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Deletion");
		alert.setContentText("Are you sure you want to delete?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			int index = songDisplay.getSelectionModel().getSelectedIndex();
			obsList.remove(index);
			list.remove(index);
			if(obsList.isEmpty()) {
				display.setText("Name: "  + " \n " +
						"Artist: "  + " \n  " +
						"Album: "  + " \n " +
						"Year: ");
			}
		}
	}
	
	public void update(ActionEvent e) {
		// need to add alert if update is clicked w/o any entries into the field
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update");
		alert.setContentText("Are you sure you want to update?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			int index = songDisplay.getSelectionModel().getSelectedIndex();
			String songName = updateName.getText();
			String artistName = updateArtist.getText();
			String albumName = updateAlbum.getText();
			Integer year = null;
			try {
				year = Integer.parseInt(updateYear.getText());
			} catch( NumberFormatException n ) {
				if( updateYear.getText().equals("") ) {
					year = list.list.get(index).year;
				} else {
					// need to throw an alert here to say that it has to be numbers inserted not strings
					System.out.println("Enter in a number");
				}
			}
			list.update(index, songName, artistName, albumName, year, obsList);
		}
		
	}
	
	public SongList getSongList() {
		return list;
	}
	
	
}
