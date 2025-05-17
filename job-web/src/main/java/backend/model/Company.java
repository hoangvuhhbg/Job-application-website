package backend.model;

public class Company {
    private String id;
    private String name;
    private String logoUrl;
    private String address;
    private String email;
    private String taxNum;
    private String licenseUrl;
    private String status;
    private String password;
    private String position;

    // Constructor
    public Company() {
    }

    public Company(String id, String name, String logoUrl, String address, String email,
                   String taxNum, String licenseUrl, String status, String password, String position) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.address = address;
        this.email = email;
        this.taxNum = taxNum;
        this.licenseUrl = licenseUrl;
        this.status = status;
        this.password = password;
        this.position = position;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
