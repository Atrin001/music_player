import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<String, User> users = new HashMap<>();

    public static boolean validateUserCredentials(String username, String password) {
        User user = users.get(username);
        return user != null && user.validateCredentials(username, password);
    }
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
    
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        clearScreen();  
        loadUsersFromFile();
        Scanner scanner = new Scanner(System.in);
            
        while (true) {
            clearScreen(); 
            System.out.println("Welcome to the Music App!");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            try{
            
            int mainChoice = getUserChoice(3);
            AccountService accountService = new AccountService();

            switch (mainChoice) {
                case 1:  // Login
                    User user = accountService.login();
                    if (user instanceof Artist) {
                        handleArtistMenu((Artist) user);
                    } else if (user instanceof Listener) {
                        handleListenerMenu((Listener) user);
                    }
                    break;

                case 2:  // Create Account
                User newUser = accountService.createAccount();
                if (newUser != null) {  // Only proceed if a valid user was returned
                    users.put(newUser.username, newUser);
                    System.out.println("Account created successfully!");
                } else {
                    System.out.println("Account creation failed. Please try again.");
                }
                break;
                

                case 3:  // Exit
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        catch (InvalidChoiceException e) {
            System.out.println(e.getMessage());
            // You can prompt the user to try again or handle it as needed
        }
        }
    }

    private static void handleArtistMenu(Artist artist) {
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        boolean artistMenu = true;
        while (artistMenu) {
            System.out.println("What would you like to do?");
            System.out.println("1. Create a song");
            System.out.println("2. Change artistic name");
            System.out.println("3. Create an album");
            System.out.println("4. Add a song to an album");
            System.out.println("5. View albums");
            System.out.println("6. View songs");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            try{
            int artistChoice = getUserChoice(8);

            switch (artistChoice) {
                case 1:
                    System.out.println("Enter song title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter song duration (in minutes):");
                    double duration = scanner.nextDouble();
                    scanner.nextLine();  // consume newline
                    artist.createSong(title, duration);
                    System.out.println("Song created!");
                    break;

                case 2:
                    System.out.println("Enter new artistic name:");
                    String newArtisticName = scanner.nextLine();
                    artist.changeArtisticName(newArtisticName);
                    break;

                case 3:
                    System.out.println("Enter album title:");
                    String albumTitle = scanner.nextLine();
                    artist.createAlbum(albumTitle);
                    System.out.println("Album created!");
                    break;

                case 4:
                    System.out.println("Enter album title to add song to:");
                    String targetAlbumTitle = scanner.nextLine();
                    System.out.println("Enter song title:");
                    String songTitle = scanner.nextLine();
                    System.out.println("Enter song duration (in minutes):");
                    double songDuration = scanner.nextDouble();
                    scanner.nextLine();  // consume newline
                    Song songToAdd = new Song(songTitle, songDuration);
                    artist.addSongToAlbum(targetAlbumTitle, songToAdd);
                    System.out.println("Song added to album!");
                    break;

                case 5:
                    artist.displayAlbums();
                    break;

                case 6:
                    artist.displaySongs();
                    break;

                case 7:
                    artistMenu = false;
                    break;

                case 8:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
            catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
                // You can prompt the user to try again or handle it as needed
            }
        }
    }

    private static void handleListenerMenu(Listener listener) {
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        boolean listenerMenu = true;
        while (listenerMenu) {
            System.out.println("What would you like to do?");
            System.out.println("1. Create a playlist");
            System.out.println("2. Delete a playlist");
            System.out.println("3. Add a song to a playlist");
            System.out.println("4. Display playlists");
            System.out.println("5. Display songs in a playlist");
            System.out.println("6. Play a song");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            try{
            int listenerChoice = getUserChoice(8);

            switch (listenerChoice) {
                case 1:
                    listener.createPlaylist();
                    break;

                case 2:
                    listener.deletePlaylist();
                    break;

                case 3:
                    listener.addSongToPlaylist();
                    break;

                case 4:
                    listener.displayPlaylists();
                    break;

                case 5:
                    listener.displaySongsInPlaylist();
                    break;

                case 6:
                    listener.playSong();
                    break;

                case 7:
                    listenerMenu = false;
                    break;

                case 8:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
            catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
                // You can prompt the user to try again or handle it as needed
            }
        }
    }

    private static int getUserChoice(int maxValidChoice) throws InvalidChoiceException {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > maxValidChoice) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                if (choice < 1 || choice > maxValidChoice) {
                    throw new InvalidChoiceException("Invalid choice. Please enter a number between 1 and " + maxValidChoice + ".");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                throw new InvalidChoiceException("Please enter a valid number.");
            }
        }
        return choice;
    }
    private static Gender parseGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender value in file: " + genderStr);
            return null; // or you can choose a default value
        }
    }
    
    private static void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                User user = null; // Initialize user to null
                Gender gender = parseGender(parts[2]);
                if (gender != null) {
                    if (parts.length > 4) {  // it's an Artist
                        user = new Artist(parts[0], parts[1], gender, parts[3], parts[4]);
                    } else {
                        user = new Listener(parts[0], parts[1], gender, parts[3]);
                    }
                    users.put(parts[0], user);  // Add to the users map only if user is not null
                } else {
                    System.out.println("Skipping user due to invalid gender: " + parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }
    
}
