package structures;

public class Song implements Comparable<Song>{
	String name;
	String artist;
	String album;
	int year;

	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		if( this.name.toLowerCase().compareTo( o.name.toLowerCase() ) == 0 ) {

			return this.artist.toLowerCase().compareTo( o.artist.toLowerCase() );
		}
		return this.name.toLowerCase().compareTo( o.name.toLowerCase() );
	}
}
