
package backend.model;

public class User {
    private String id = "";
    private String name = "";
    private String email = "";
    private String phoneNumber = "";
    private String username = "";
    private String password = "";
    private String status = "";
    private String address = "";
    private String major = "";

    public User(){};
    public User(String id, String name, String email, String phoneNumber, String username, String password, String status, String address, String major) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.status = status;
        this.address = address;
        this.major = major;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getStatus() {
        return status;
    }
    public String getAddress() {
        return address;
    }
    public String getMajor() {
        return major;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setMajor(String major){
        this.major = major;
    }
}
