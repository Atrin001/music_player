
public class User {
    protected String username;
    protected String password; // For real applications, ensure this is encrypted!
    protected String dateOfBirth; // This could be a Date object or a string in yyyy-MM-dd format.
    protected Gender gender;
    public User(String username, String password, Gender gender, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    public boolean validateCredentials(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    @Override
    public String toString() {
        return username + ":" + password + ":" + gender + ":" + dateOfBirth;
    }
    

    // Getters and setters go here
}
