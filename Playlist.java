import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public String getName() {
        return name;
    }

    public void displaySongs() {
        if (songs.isEmpty()) {
            System.out.println("Playlist is empty!");
            return;
        }
        for (Song song : songs) {
            System.out.println(song.getTitle() + " (" + song.formatDuration(song.getDuration()) + ")");
        }
    }

    
}
