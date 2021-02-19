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
			
			// makes File object so we can delete it after the SongList data structure is created
			File songFile = new File("songs.txt");
			list = new SongList(sc);
			// deletes the songs.txt 
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Insert");
		alert.setContentText("Are you sure you want to insert?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if(Name.getText().trim().isEmpty() && Artist.getText().trim().isEmpty()) {
				Alert errorBoth = new Alert(AlertType.ERROR);
				errorBoth.setTitle("Insertion Error");
				errorBoth.setContentText("Please input a name and artist");
				errorBoth.showAndWait();
			}
			else if(Name.getText().trim().isEmpty()) {
				Alert errorName = new Alert(AlertType.ERROR);
				errorName.setTitle("Insertion Error");
				errorName.setContentText("Please input a name");
				errorName.showAndWait();
			}
			else if(Artist.getText().trim().isEmpty()) {
				Alert errorAlbum = new Alert(AlertType.ERROR);
				errorAlbum.setTitle("Insertion Error");
				errorAlbum.setContentText("Please input an album");
				errorAlbum.showAndWait();
			}
			else if(list.search(Name.getText(), Artist.getText())){
				Alert errorDuplicate = new Alert(AlertType.ERROR);
				errorDuplicate.setTitle("Insertion Error");
				errorDuplicate.setContentText("Song and artist already exist. Please insert the song using other names");
				errorDuplicate.showAndWait();
			}
			else {
				
				try {
					int year = Integer.parseInt(Year.getText());
					if(year < 0) {
						Alert errorNegativeYear = new Alert(AlertType.ERROR);
						errorNegativeYear.setTitle("Insertion Error");
						errorNegativeYear.setContentText("Please insert a valid year that you wish to change the currently selected song to.");
						errorNegativeYear.showAndWait();
					} else {
						String songName = Name.getText().trim();
						String artistName = Artist.getText().trim();
						String albumName = Album.getText().trim();
						obsList = list.add(songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					}
				} catch( NumberFormatException n ) {
					if( Year.getText().trim().isEmpty() ) {
						int year = 0;
						String songName = Name.getText().trim();
						String artistName = Artist.getText().trim();
						String albumName;
						if(Album.getText().trim().isEmpty()) {
							albumName = "No Album";
						}else {
							albumName = Album.getText().trim();
						}
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
		// confirms with user if they want to delete the song
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Deletion Confirmation");
		alert.setContentText("Are you sure you want to delete?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if(obsList.isEmpty()) {
				Alert errorEmpty = new Alert(AlertType.ERROR);
				errorEmpty.setTitle("Deletion Error");
				errorEmpty.setContentText("There is nothing to delete.");
				errorEmpty.show();
			}
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update");
		alert.setContentText("Are you sure you want to update?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if(list.search(updateName.getText().trim(), updateArtist.getText().trim())){
				Alert errorDuplicate = new Alert(AlertType.ERROR);
				errorDuplicate.setTitle("Update Error");
				errorDuplicate.setContentText("Song and artist already exist. Please update the song using other names");
				errorDuplicate.showAndWait();
			} else{
				try {
					int year = Integer.parseInt(updateYear.getText().trim());
					if(year < 0) {
						Alert errorNegativeYear = new Alert(AlertType.ERROR);
						errorNegativeYear.setTitle("Update Error");
						errorNegativeYear.setContentText("Please insert a valid year that you wish to change the currently selected song to.");
						errorNegativeYear.showAndWait();
					}else {
						int index = songDisplay.getSelectionModel().getSelectedIndex();
						String songName = updateName.getText().trim();
						String artistName = updateArtist.getText().trim();
						String albumName = updateAlbum.getText().trim();
						list.update(index, songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					}
				} catch( NumberFormatException n ) {
					if( updateYear.getText().trim().isEmpty() ) {
						int index = songDisplay.getSelectionModel().getSelectedIndex();
						int year = list.list.get(index).year;
						String songName = updateName.getText().trim();
						String artistName = updateArtist.getText().trim();
						String albumName = updateAlbum.getText().trim();
						list.update(index, songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					} else {
						Alert errorUpdateYear = new Alert(AlertType.ERROR);
						errorUpdateYear.setTitle("Update Error");
						errorUpdateYear.setContentText("Please insert a valid year that you wish to change the currently selected song to.");
						errorUpdateYear.showAndWait();
				}
				
			}
			
		}
		}
		
	}
	
	// returns the ArrayList of songs
	public SongList getSongList() {
		return list;
	}
	
	
}
