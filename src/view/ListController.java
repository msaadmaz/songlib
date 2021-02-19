package view;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
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
			if( !obsList.isEmpty() ) {
				// select the first item
				songDisplay.getSelectionModel().select(0);
			}
			setTextDisplay(mainStage);
			
			// set listener for the items
			songDisplay
				.getSelectionModel()
				.selectedIndexProperty()
				.addListener(
						(obs, oldVal, newVal) -> {
								setTextDisplay(mainStage);
						});
			
		} catch (FileNotFoundException e) {
			// if songs file doesn't exist it will create one
			File songs = new File("songs.txt");
			try {
				songs.createNewFile();
				try(Scanner sc = new Scanner(new File("songs.txt")) ){
					list = new SongList(sc);
					songs.delete();
				} catch (FileNotFoundException e2) {
					System.out.println("I'm broken");
				}
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
							(obs, oldVal, newVal) -> {
									setTextDisplay(mainStage);
							});
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	private void setTextDisplay(Stage mainStage) {
		int index = songDisplay.getSelectionModel().getSelectedIndex();
		if( index == -1 && !obsList.isEmpty()) {
			index = 0;
		}
		if( !list.list.isEmpty() && !obsList.isEmpty() ) {
			Song selectedSong = list.list.get(index);
			display.setText("Name: " + selectedSong.name + " \n " +
							"Artist: " + selectedSong.artist + " \n  " +
							"Album: " + selectedSong.album + " \n " +
							"Year: " + selectedSong.year);
		}
	}
	
	public void insertion(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Insert");
		alert.setContentText("Are you sure you want to insert?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) { 		// confirmation is set to go
			if(Name.getText().trim().isEmpty() && Artist.getText().trim().isEmpty()) { 		//name and artist are not input, show error
				Alert errorBoth = new Alert(AlertType.ERROR);
				errorBoth.setTitle("Insertion Error");
				errorBoth.setContentText("Please input a name and artist");
				errorBoth.showAndWait();
			}
			else if(Name.getText().trim().isEmpty()) { 		//name is not input, show error
				Alert errorName = new Alert(AlertType.ERROR);
				errorName.setTitle("Insertion Error");
				errorName.setContentText("Please input a name");
				errorName.showAndWait();
			}
			else if(Artist.getText().trim().isEmpty()) { 		// artist is not input, show error
				Alert errorAlbum = new Alert(AlertType.ERROR);
				errorAlbum.setTitle("Insertion Error");
				errorAlbum.setContentText("Please input an album");
				errorAlbum.showAndWait();
			}
			else if(list.search(Name.getText().trim(), Artist.getText().trim())){		//song and artist already exist, show error
				Alert errorDuplicate = new Alert(AlertType.ERROR);
				errorDuplicate.setTitle("Insertion Error");
				errorDuplicate.setContentText("Song and artist already exist. Please insert the song using other names");
				errorDuplicate.showAndWait();
			}
			else {
				
				try {//test for valid year
					int year = Integer.parseInt(Year.getText()); //will catch if number is not input to year textfield
					if(year < 0) { //error, list should be positive
						Alert errorNegativeYear = new Alert(AlertType.ERROR);
						errorNegativeYear.setTitle("Insertion Error");
						errorNegativeYear.setContentText("Please insert a valid year that you wish to change the currently selected song to.");
						errorNegativeYear.showAndWait();
					} else { // insert song to song list
						String songName = Name.getText().trim();
						String artistName = Artist.getText().trim();
						String albumName = Album.getText().trim();
						list.add(songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					}
				} catch( NumberFormatException n ) {		 // something wrong with year, either not input or wrong values
					if( Year.getText().trim().isEmpty() ) { 		//no input for year, insert year 0
						int year = 0;
						String songName = Name.getText().trim();
						String artistName = Artist.getText().trim();
						String albumName;
						if(Album.getText().trim().isEmpty()) { // no album present
							albumName = "No Album";
						}else {
							albumName = Album.getText().trim();
						}
						list.add(songName, artistName, albumName, year, obsList);
						songDisplay.getSelectionModel().select(obsList.indexOf(songName));
						display.setText("Name: " + songName + " \n " +
								"Artist: " + artistName + " \n  " +
								"Album: " + albumName + " \n " +
								"Year: " + year);
					} else { // year is not a number
						Alert yearError = new Alert(AlertType.ERROR);
						yearError.setContentText("Please enter in a valid year");
						yearError.showAndWait();
					}
				}
				
			}
			
		 }
	}
	
	public void deletion(ActionEvent e) {//Delete button pressed
		// confirms with user if they want to delete the song
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Deletion Confirmation");
		alert.setContentText("Are you sure you want to delete?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) { 	// Confirmation of Deletion
			if(obsList.isEmpty()) {		//if list is empty, do nothing
				Alert errorEmpty = new Alert(AlertType.ERROR);
				errorEmpty.setTitle("Deletion Error");
				errorEmpty.setContentText("There is nothing to delete.");
				errorEmpty.show();
			} else {
				int index = songDisplay.getSelectionModel().getSelectedIndex();
				if(index == -1) {	//select first song
					index = 0;
				}
				obsList.remove(index);
				list.remove(index);
				if(obsList.isEmpty()) {
					display.setText("Name: "  + " \n " +
							"Artist: "  + " \n  " +
							"Album: "  + " \n " +
							"Year: ");
				} else if (index > obsList.size()-1 && index >= 1) {	//Deletion will delete last item in song list
					index--;
					songDisplay.getSelectionModel().select(index);
					Song selectedSong = list.list.get(index);
                    display.setText("Name: " + selectedSong.name + " \n " +
                                    "Artist: " + selectedSong.artist + " \n  " +
                                    "Album: " + selectedSong.album + " \n " +
                                    "Year: " + selectedSong.year);
					
				} else {	//Delete Selected item
					songDisplay.getSelectionModel().select(index);
					Song selectedSong = list.list.get(index);
                    display.setText("Name: " + selectedSong.name + " \n " +
                                    "Artist: " + selectedSong.artist + " \n  " +
                                    "Album: " + selectedSong.album + " \n " +
                                    "Year: " + selectedSong.year);
				}
			}
		}
	}
	
	public void update(ActionEvent e) {		//Update button is clicked
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update");
		alert.setContentText("Are you sure you want to update?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {		//Confirmation is ok				
			if(list.search(updateName.getText().trim(), updateArtist.getText().trim())){ //searches for song and artist already in list
				Alert errorDuplicate = new Alert(AlertType.ERROR);
				errorDuplicate.setTitle("Update Error");
				errorDuplicate.setContentText("Song and artist already exist. Please update the song using other names");
				errorDuplicate.showAndWait();
			} else{
				try {//checks for valid year
					int year = Integer.parseInt(updateYear.getText().trim());
					if(year < 0) { //checks for positive year
						Alert errorNegativeYear = new Alert(AlertType.ERROR);
						errorNegativeYear.setTitle("Update Error");
						errorNegativeYear.setContentText("Please insert a valid year that you wish to change the currently selected song to.");
						errorNegativeYear.showAndWait();
					}else { //year is valid
						int index = songDisplay.getSelectionModel().getSelectedIndex();
						String songName = updateName.getText().trim();
						String artistName = updateArtist.getText().trim();
						if( !songName.equals("") || !artistName.equals("") ) {
							if(!songName.equals("")) { //SongName is updated
								if( list.search(songName, list.list.get(index).artist)) { //Updating song will produce a duplicate 
									Alert errorDuplicate = new Alert(AlertType.ERROR);
									errorDuplicate.setTitle("Update Error");
									errorDuplicate.setContentText("Song and artist already exist. Please update the song using other names");
									errorDuplicate.showAndWait();
								} else { // no duplicate is created, so updates the song
									String albumName = updateAlbum.getText().trim();
									list.update(index, songName, artistName, albumName, year, obsList);
									Song selectedSong = list.list.get(index);
									display.setText("Name: " + selectedSong.name + " \n " +
													"Artist: " + selectedSong.artist + " \n  " +
													"Album: " + selectedSong.album + " \n " +
													"Year: " + selectedSong.year);
								}
							} else { //artistName is being updated
								if( list.search(list.list.get(index).name, artistName) ) { //Updating artist will produce a duplicate
									Alert errorDuplicate = new Alert(AlertType.ERROR);
									errorDuplicate.setTitle("Update Error");
									errorDuplicate.setContentText("Song and artist already exist. Please update the artist using other names");
									errorDuplicate.showAndWait();
								} else { //no duplicate created, updates song
									String albumName = updateAlbum.getText().trim();
									list.update(index, songName, artistName, albumName, year, obsList);
									Song selectedSong = list.list.get(index);
									display.setText("Name: " + selectedSong.name + " \n " +
													"Artist: " + selectedSong.artist + " \n  " +
													"Album: " + selectedSong.album + " \n " +
													"Year: " + selectedSong.year);
								}
							}
						} else { //year or album are the only things being updated
							String albumName = updateAlbum.getText().trim();
							list.update(index, songName, artistName, albumName, year, obsList);
							Song selectedSong = list.list.get(index);
							display.setText("Name: " + selectedSong.name + " \n " +
											"Artist: " + selectedSong.artist + " \n  " +
											"Album: " + selectedSong.album + " \n " +
											"Year: " + selectedSong.year);
						}
					}
				} catch( NumberFormatException n ) { //value in number textfield is either empty or invalid
					if( updateYear.getText().trim().isEmpty() ) { //no input for year, set year to 0
						int index = songDisplay.getSelectionModel().getSelectedIndex();
						int year = list.list.get(index).year;
						String songName = updateName.getText().trim();
						String artistName = updateArtist.getText().trim();
						String albumName = updateAlbum.getText().trim();
						list.update(index, songName, artistName, albumName, year, obsList);
						Song selectedSong = list.list.get(index);
						display.setText("Name: " + selectedSong.name + " \n " +
										"Artist: " + selectedSong.artist + " \n  " +
										"Album: " + selectedSong.album + " \n " +
										"Year: " + selectedSong.year);
					} else { // incorrect input for year
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
