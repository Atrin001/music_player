import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountService {
    

    public User createAccount() {
        Scanner scanner = new Scanner(System.in);
        User user = null;  // Initialize user to null
    
    int choice = 0;
    while (choice != 1 && choice != 2 && choice != 3) {
        try {
            System.out.println("1. Listener");
            System.out.println("2. Artist");
            System.out.println("3. Exit");
               
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            if (choice != 1 && choice != 2 && choice != 3) {
                System.out.println("Invalid choice. Please choose 1 for Listener or 2 for Artist.");
            }
         
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine(); // Consume the invalid input to avoid looping the error
        }
           if(choice==3){
            System.exit(0);
        }
    }    System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Enter gender (MALE, FEMALE, OTHER):");
        String genderInput = scanner.nextLine();
        Gender gender = null;
        try {
            gender = Gender.valueOf(genderInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender. Please enter MALE, FEMALE, or OTHER.");
            return null; // or you might want to loop until valid input is received
        }
    
        System.out.println("Enter date of birth (YYYY-MM-DD):");
        String dob = scanner.nextLine();

        String artisticName = "";
        if (choice == 2) {
            System.out.println("Enter your artistic name:");
            artisticName = scanner.nextLine();
        }
        
        if (choice == 1) {
            user = new Listener(username, password, gender, dob);
        } else if (choice == 2) {
            user = new Artist(username, password, gender, dob, artisticName);
        } else if (choice == 3) {
            System.exit(0);
        } else {
            System.out.println("Invalid choice");
            return null;
        }
    
        saveUserToFile(user);  // Save user data
        return user;  // Return the created user
    }
      private void saveUserToFile(User user) {
    try (FileWriter fw = new FileWriter("users.txt", true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
        
        out.println(user.toString());  // Save user data to a new line
    } catch (IOException e) {
        System.out.println("Error saving user data: " + e.getMessage());
    }
    }

  
    public User login() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Are you a:");
        System.out.println("1. Listener");
        System.out.println("2. Artist");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        if(choice==3){
            System.exit(0);
        }
        scanner.nextLine();  // Consume the newline
    
        
        System.out.println("Enter username (or type 'exit' to go back):");
        String username = scanner.nextLine();
        if ("exit".equalsIgnoreCase(username)) {
            return null;
        }

        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User user = Main.users.get(username);  // Retrieve the user object based on username
    
        if (user != null) {  // If a user with the entered username exists
            if ((choice == 1 && user instanceof Listener) || (choice == 2 && user instanceof Artist)) {
                if (user.validateCredentials(username, password)) {
                    System.out.println("Login successful!");
                    return user;
                } else {
                    System.out.println("Incorrect password.");
                    return null;
                }
            } else {
                System.out.println("Account type does not match the choice.");
                return null;
            }
        } else {
            System.out.println("User not found.");
            return null;
        }
    }
    
    }
    
     
    

