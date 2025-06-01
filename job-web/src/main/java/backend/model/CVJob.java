package backend.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CVJob {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int id;
    private String cvUrl;
    private String userId;
    private String status;
    private JobCompany jobCompany;

    // Constructors
    public CVJob() {}

    public CVJob(String cvUrl, String userId, String status, Date date, JobCompany job) {
        this.cvUrl = cvUrl;
        this.userId = userId;
        this.status = status;
        this.date = date;
        this.jobCompany = job;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public JobCompany getJobCompany(){
        return jobCompany;
    }
    public void setJobCompany(JobCompany jobCompany){
        this.jobCompany = jobCompany;
    }
}