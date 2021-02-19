package structures;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;


// array list of song objects that will dynamically make sure it is sorted alphabetically 
public class SongList {
	public ArrayList<Song> list = new ArrayList<Song>();
	public ArrayList<String> names = new ArrayList<String>();
	
	// constructed through a file
	public SongList(Scanner sc) {
		
		// each line is a song
		while( sc.hasNextLine() ) {
			String song = sc.nextLine();
			
			// tokens (the components of the song object) are delimmited by a comma
			StringTokenizer components = new StringTokenizer(song,"|");
			
			// construct a song
			Song temp = new Song();
			temp.name = components.nextToken();
			temp.artist = components.nextToken();
			temp.album = components.nextToken();
			temp.year = Integer.parseInt(components.nextToken());
			
			// add to song list
			this.list.add(temp);
			
		}
		
		// sort the list
		this.list.sort(null);
		
		// add all the names from the list into a names array list
		for(int i = 0; i < this.list.size(); i++) {
			this.names.add(this.list.get(i).toString());
		}
	}
	
	// prints list
	public void printList() {
		System.out.println(list);
	}
	
	// gets the size of the list
	public int getSize() {
		return list.size();
	}


	// removes an item from the list
	public void remove(int index) {
		this.list.remove(index);
		this.names.remove(index);
	}
	
	// adds a new song to the song list and returns the new observable list so that it can properly be aligned with songlist
	public void add(String name, String artist, String album, int year, ObservableList<String> obsList) {
		
		if( this.list.isEmpty() ) {
			this.list.clear();
			obsList.clear();
		}
		
		Song newSong = new Song();
		newSong.name = name;
		newSong.artist = artist;
		
		// if there is no album name then the album gets set to no album
		if( album.equals("") ) {
			newSong.album = "No Album";
		} else {
			newSong.album = album;
		}
		newSong.year = year;
		
		// adds the new song and sorts the updated list
		this.list.add(newSong);
		this.list.sort(null);
		
		// adds the new song to the OBS list
		obsList.add(newSong.toString());
		
		// re-inputs songs from SongList to OBS list to guarentee the songs are properly aligned in both lists
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < this.list.size(); i++) {
			temp.add(this.list.get(i).toString());
			obsList.set(i, this.list.get(i).toString());
		}
		
		//updates name array list
		this.names = temp;
	}
	
	// updates a songs in songlist and updates the observable list as well
	public void update(int index, String name, String artist, String album, Integer year, ObservableList<String> obsList) {
		
		// counter to see if the list needs to be resorted, and if OBS list needs to be updated
		int counter = 0;
		if( !name.equals("") ) {
			this.list.get(index).name = name;
			counter++;
		}
		if( !artist.equals("") ) {
			this.list.get(index).artist = artist;
			counter++;
		}
		if( !album.equals("") ) {
			this.list.get(index).album = album;
		}
		if( year != null ) {
			this.list.get(index).year = Integer.parseInt(year.toString());
		}
		
		// sorts list and updates OBS list
		if(counter >= 1) {
			this.list.sort(null);
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < this.list.size(); i++) {
				temp.add(this.list.get(i).toString());
				obsList.set(i, this.list.get(i).toString());
			}
			this.names = temp;
		}
	}
	
	// sequential search method to located a song and artist
	public boolean search(String name, String artist) {
		for(int i = 0; i < this.list.size(); i++) {
			if( this.list.get(i).artist.equals( artist ) && this.list.get(i).name.equals( name ) ) {
				return true;
			}
		}
		return false;
	}

}
