import java.util.ArrayList;
import java.util.List;

public class Artist extends User {
    private String artisticName;
    private List<Song> songs = new ArrayList<>();  // List to store songs created by the artist
    private List<Album> albums;
    

    public Artist(String username, String password, Gender gender, String dob, String artisticName) {
        super(username, password, gender, dob);
        this.artisticName = artisticName;
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();  // Initialize the albums list here
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    @Override
public String toString() {
    return super.toString() + ":" + artisticName;
}
public void createSong(String title, double duration) {
    Song song = new Song(title, duration);
    songs.add(song);
}
public void createAlbum(String title) {
    Album album = new Album(title);
    albums.add(album);
}
public void addSongToAlbum(String albumTitle, Song song) {
    boolean found = false;
    for (Album album : albums) {
        if (album.getTitle().equals(albumTitle)) {
            album.addSong(song);
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("Album not found! Creating new album with the song.");
        Album newAlbum = new Album(albumTitle);
        newAlbum.addSong(song);
        albums.add(newAlbum);
    }
    // Add the song to the artist's personal list if it's not already there
    if (!songs.contains(song)) {
        songs.add(song);
    }
}

public void displayAlbums() {
    if (albums.isEmpty()) {
        System.out.println("No albums available.");
        return;
    }
    System.out.println("Albums:");
    for (Album album : albums) {
        System.out.println("- " + album);
    }
}

public void displaySongs() {
    if (songs.isEmpty()) {
        System.out.println("No songs available.");
        return;
    }
    System.out.println("Songs:");
    for (Song song : songs) {
        System.out.println("- " + song);
    }
}


public void changeArtisticName(String newArtisticName) {
    this.artisticName = newArtisticName;
}

public String getArtisticName() {
    return artisticName;
}


    // Additional functionalities for Artist can be added here
}