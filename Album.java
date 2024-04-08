import java.util.ArrayList;
import java.util.List;

public class Album {
    private String title;
    private List<Song> songs;

    public Album(String title) {
        this.title = title;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public String getTitle() {
        return title;
    }

    public List<Song> getSongs() {
        return songs;
    }
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(title).append(" (").append(songs.size()).append(" songs):\n");
    
    for (Song song : songs) {
        sb.append("- ").append(song.getTitle())
          .append(" (").append(song.formatDuration(song.getDuration())).append(")\n");
    }
    
    return sb.toString();
}

}
