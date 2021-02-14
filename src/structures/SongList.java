package structures;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


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

	/*
	 * public String toString() {
	 * 
	 * }
	 */
	public void remove(String song, String artist) {
		
	}
	

}
