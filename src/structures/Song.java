package structures;

public class Song implements Comparable<Song>{
	public String name;
	public String artist;
	public String album;
	public int year;

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
