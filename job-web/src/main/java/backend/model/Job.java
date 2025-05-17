package backend.model;
import java.time.LocalDate;

public class Job {

    private String id;
    private String position;
    private int num;
    private String workMethod;
    private String level;
    private String major;
    private String address;
    private String description;
    private String requirment;
    private String benefit;
    private int wage;
    private LocalDate date;
    private int age;
    private String experiment;
    private String companyID;

    // Getters & Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public String getWorkMethod() {
        return workMethod;
    }
    public void setWorkMethod(String workMethod) {
        this.workMethod = workMethod;
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirment() {
        return requirment;
    }
    public void setRequirment(String requirment) {
        this.requirment = requirment;
    }

    public String getBenefit() {
        return benefit;
    }
    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public int getWage() {
        return wage;
    }
    public void setWage(int wage) {
        this.wage = wage;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getExperiment() {
        return experiment;
    }
    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getCompanyID() {
        return companyID;
    }
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}