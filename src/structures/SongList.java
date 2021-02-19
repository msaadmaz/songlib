package structures;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;


//array list of song objects that will dynamically make sure it is sorted alphabetically 
public class SongList {
	public ArrayList<Song> list = new ArrayList<Song>();
	public ArrayList<String> names = new ArrayList<String>();
	//constructed through a file
	public SongList(Scanner sc) {
		
		//each line is a song
		while( sc.hasNextLine() ) {
			String song = sc.nextLine();
			
			//tokens (the components of the song object) are delimmited by a comma
			StringTokenizer components = new StringTokenizer(song,"|");
			
			//construct a song
			Song temp = new Song();
			temp.name = components.nextToken();
			temp.artist = components.nextToken();
			temp.album = components.nextToken();
			temp.year = Integer.parseInt(components.nextToken());
			
			//add to song list
			this.list.add(temp);
			
		}
		
		//need to make comparator
		this.list.sort(null);
		for(int i = 0; i < this.list.size(); i++) {
			this.names.add(this.list.get(i).toString());
		}
	}
	
	//prints list
	public void printList() {
		System.out.println(list);
	}
	
	//gets the size of the list
	public int getSize() {
		return list.size();
	}


	// removes an item from the list
	public void remove(int index) {
		this.list.remove(index);
		this.names.remove(index);
	}
	
	// adds a new song to the song list and returns the new observable list so that it can properly be aligned with songlist
	public ObservableList<String> add(String name, String artist, String album, int year, ObservableList<String> obsList) {
		Song newSong = new Song();
		newSong.name = name;
		newSong.artist = artist;
		if( album.equals("") ) {
			newSong.album = "No Album";
		} else {
			newSong.album = album;
		}
		newSong.year = year;
		this.list.add(newSong);
		this.list.sort(null);
		obsList.add(newSong.toString());
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < this.list.size(); i++) {
			temp.add(this.list.get(i).toString());
			obsList.set(i, this.list.get(i).toString());
		}
		this.names = temp;
		return obsList;
	}
	
	// updates a songs in songlist and updates the observable list as well
	public void update(int index, String name, String artist, String album, Integer year, ObservableList<String> obsList) {
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

}
