import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Listener extends User {
    private Map<String, List<Song>> playlists = new HashMap<>();
    protected Gender gender;
    public Listener(String username, String password, Gender gender, String dateOfBirth) {
        super(username, password, gender, dateOfBirth);
    }
    public void createPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter playlist name:");
        String name = scanner.nextLine();
        if (!playlists.containsKey(name)) {
            playlists.put(name, new ArrayList<>());
            System.out.println("Playlist " + name + " created!");
        } else {
            System.out.println("Playlist already exists!");
        }
    }

    public void deletePlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter playlist name to delete:");
        String name = scanner.nextLine();
        playlists.remove(name);
        System.out.println("Playlist " + name + " deleted!");
    }

    public void addSongToPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter playlist name:");
        String playlistName = scanner.nextLine();
        System.out.println("Enter song title:");
        String songTitle = scanner.nextLine();
        System.out.println("Enter song duration (in minutes):");
        double duration = scanner.nextDouble();
        Song songToAdd = new Song(songTitle, duration);
        if (playlists.containsKey(playlistName)) {
            playlists.get(playlistName).add(songToAdd);
            System.out.println("Song added to " + playlistName + "!");
        } else {
            System.out.println("Playlist not found!");
        }
    }

    public void displayPlaylists() {
        System.out.println("Playlists:");
        for (String name : playlists.keySet()) {
            System.out.println("- " + name);
        }
    }

    public void displaySongsInPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter playlist name to view songs:");
        String playlistName = scanner.nextLine();
        if (playlists.containsKey(playlistName)) {
            System.out.println("Songs in " + playlistName + ":");
            for (Song song : playlists.get(playlistName)) {
                System.out.println("- " + song);
            }
        } else {
            System.out.println("Playlist not found!");
        }
    }

    public void playSong() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter song title to play:");
        String songTitle = scanner.nextLine();
    
        // Find song by title in playlists
        Song songToPlay = findSongInPlaylists(songTitle);
        if (songToPlay == null) {
            System.out.println("Song not found.");
            return;
        }
    
        System.out.println("Press Enter to start playing. Type 'stop' and press Enter at any time to stop playing.(you may not see what you write)");
        String command = scanner.nextLine();
        
        if (command.equalsIgnoreCase("stop")) {
            System.out.println("Playback stopped.");
            return;
        }
    
        Thread playThread = new Thread(() -> songToPlay.play());
        playThread.start(); // Start playing the song in a separate thread
    
        while (playThread.isAlive()) {
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("stop")) {
                playThread.interrupt(); // Interrupt the song playing thread
                break;
            }
        }
    
        if (playThread.isAlive()) {
            // Wait for the song to finish playing if it wasn't interrupted
            try {
                playThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private Song findSongInPlaylists(String title) {
        for (List<Song> playlist : playlists.values()) {
            for (Song song : playlist) {
                if (song.getTitle().equalsIgnoreCase(title)) {
                    return song;
                }
            }
        }
        return null; // Song not found
    }
    }

