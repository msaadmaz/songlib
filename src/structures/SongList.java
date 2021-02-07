package structures;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

//song object that will store each song
class Song {
	String name;
	String artist;
	String album;
	int year;
	
	public String toString() {
		return name + " " + artist + " " + album + " " + year;
	}
	
//	Song(String name, String artist, String album, int year) {
//		this.name = name;
//		this.artist = artist;
//		this.album = album;
//		this.year = year;
//	}
	
}

//array list of song objects that will dynamically make sure it is sorted alphabetically 
public class SongList {
	ArrayList<Song> list = new ArrayList<Song>();
	
	//constructed through a file
	public SongList(Scanner sc) {
		
		//each line is a song
		while( sc.hasNextLine() ) {
			String song = sc.nextLine();
			
			//tokens (the components of the song object) are delimmited by a comma
			StringTokenizer components = new StringTokenizer(song,",");
			
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
	}
	
	//prints list
	public void printList() {
		System.out.println(list);
	}
	
	//gets the size of the list
	public int getSize() {
		return list.size();
	}
	

}
