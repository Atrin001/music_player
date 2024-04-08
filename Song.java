import java.io.IOException;

public class Song {
    private String title;
    private double duration; // Duration in minutes
    public String artist;
    public String album;

    public Song(String title, double duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }
    public void play() {
        
        // Attempt to clear the console, check if it supports escape sequences
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            // Handle exceptions
        }
        
        System.out.println("Now Playing: " + this);
        System.out.println("────────────────────────────────────────────────────");
    
       
        int totalDurationInSeconds = (int) (duration * 60);
        int progressInSeconds = 0;

        while (progressInSeconds <= totalDurationInSeconds && !Thread.currentThread().isInterrupted()) {
            double progressPercentage = (double) progressInSeconds / totalDurationInSeconds;
    
            // Generate the progress bar
            int progressBarLength = 50;
            int progress = (int) (progressPercentage * progressBarLength);
    
            System.out.print("\r[");
            for (int i = 0; i < progressBarLength; i++) {
                if (i < progress) {
                    System.out.print("*");
                } else {
                    System.out.print("_");
                }
            }
            System.out.print("]");
            System.out.print(formatTime(progressInSeconds) + " / " + formatDuration(duration) );
    
            try {
                Thread.sleep(1000); // Wait for 1 second to simulate song playing
            } catch (InterruptedException e) {
                System.out.println("\nPlayback interrupted.");
                return; // Stop playing the song if the thread is interrupted
            }
    
            progressInSeconds++;
        }
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println("\n♪♫♪♫ Song finished playing ♪♫♪♫");
        }
        System.out.println("\n♪♫♪♫ Song finished playing ♪♫♪♫");
    }
    
    public String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    public String formatDuration(double duration) {
        int totalSeconds = (int) (duration * 60);
        return formatTime(totalSeconds);
    }
    
    // Override the toString method if needed to represent the song with more details
    @Override
    public String toString() {
        return "\"" + title  +  "\" (" + duration + " mins)";
    }
}
