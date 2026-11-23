/**
 * User entity.
 */
public class User {
    private String name;
    private String email;
    private String homepage;

    public User(String name, String email, String homepage) {
        this.name = name;
        this.email = email;
        this.homepage = homepage;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getHomepage() {
        return homepage;
    }
}
