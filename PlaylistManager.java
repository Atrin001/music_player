import java.util.List;

public class PlaylistManager {
    private List<Song> playlist;
    private int currentSongIndex;

    public PlaylistManager(List<Song> playlist) {
        this.playlist = playlist;
        this.currentSongIndex = 0; // Start with the first song
    }

    public Song getCurrentSong() {
        return playlist.get(currentSongIndex);
    }

    public Song nextSong() {
        if (currentSongIndex < playlist.size() - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0; // Loop back to start
        }
        return getCurrentSong();
    }

    public Song previousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            currentSongIndex = playlist.size() - 1; // Loop back to end
        }
        return getCurrentSong();
    }

    // Method to play current song
    public void playCurrentSong() {
        Song currentSong = getCurrentSong();
        currentSong.play();
    }
    
    // You may want to add methods to add or remove songs from the playlist
    // and other functionality as needed.
}
