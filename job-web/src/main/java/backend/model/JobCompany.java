package backend.model;

public class JobCompany {
    private Job job;
    private Company company;

    // Constructor không tham số
    public JobCompany() {
    }

    // Constructor đầy đủ tham số
    public JobCompany(Job job, Company company) {
        this.job = job;
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
