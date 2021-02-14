package structures;

public class Song implements Comparable<Song>{
	String name;
	String artist;
	String album;
	int year;

	public String toString() {
		return name + " " + artist + " " + album + " " + year;
	}

	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		if( this.name.toLowerCase().compareTo( o.name.toLowerCase() ) == 0 ) {
//			if( this.artist.toLowerCase().compareTo( o.artist.toLowerCase() ) == 1) {
//				return 1;
//			} else if(this.artist.toLowerCase().compareTo( o.artist.toLowerCase() ) == -1) {
//				return -1;
//			} else {
//				return 0;
//			}
			return this.artist.toLowerCase().compareTo( o.artist.toLowerCase() );
//			return o.name.toLowerCase().compareTo( this.name.toLowerCase() );
		}
		return this.name.toLowerCase().compareTo( o.name.toLowerCase() );
	}
}
