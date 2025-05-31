package backend.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.model.CVJob;
import backend.model.Company;
import backend.model.Job;
import backend.model.JobCompany;
import jakarta.servlet.http.HttpSession;

@Service
public class JobService {
    @Autowired CompanyService comSer;

    public void addJob(HttpSession session, Job job){
        try{
            String companyID = String.valueOf(session.getAttribute("ID"));
            Conn conn = new Conn();
            System.out.print(companyID);
            String sql = "SELECT COUNT(*) FROM job";
            ResultSet rs = conn.s.executeQuery(sql);
            if (rs.next()) {
                int count = rs.getInt(1);              
                String newID = String.format("JOB%04d", count + 1);
                sql = "INSERT INTO job (id, position, num, workMethod, level, major, address, description, " +
             "requirment, benefit, min_wage, max_wage, date, age, experiment, CompanyId, name) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.c.prepareStatement(sql);
                ps.setString(1, newID);
                ps.setString(2, job.getPosition());
                ps.setInt(3, job.getNum());
                ps.setString(4, job.getWorkMethod());
                ps.setString(5, job.getLevel());
                ps.setString(6, job.getMajor());
                ps.setString(7, job.getAddress());
                ps.setString(8, job.getDescription());
                ps.setString(9, job.getRequirment());
                ps.setString(10, job.getBenefit());
                ps.setInt(11, job.getMinWage());
                ps.setInt(12, job.getMaxWage());
                ps.setDate(13, new java.sql.Date(job.getDate().getTime()));
                ps.setString(14, job.getAge());
                ps.setString(15, job.getExperiment());
                ps.setString(16, companyID);
                ps.setString(17, job.getName());
                ps.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Job getJob(String ID){
        try {
            Conn conn = new Conn();
            String sql = "Select * from job where id = '"+ID+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                Job job = new Job();
                job.setId(ID);
                job.setPosition(rs.getString("Position"));
                job.setNum(rs.getInt("num"));
                job.setWorkMethod(rs.getString("workMethod"));
                job.setLevel(rs.getString("level"));
                job.setMajor(rs.getString("major"));
                job.setAddress(rs.getString("address"));
                job.setDescription(rs.getString("description"));
                job.setRequirment(rs.getString("requirment"));
                job.setBenefit(rs.getString("benefit"));
                job.setDate(rs.getDate("date"));
                job.setExperiment(rs.getString("experiment"));
                job.setCompanyID(rs.getString("CompanyID"));
                job.setMinWage(rs.getInt("min_wage"));
                job.setMaxWage(rs.getInt("max_wage"));
                job.setAge(rs.getString("age"));
                job.setName(rs.getString("name"));
                return job;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Job> getList(){
        ArrayList<Job> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "Select ID from job";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String ID = rs.getString("ID");
                Job job = getJob(ID);
                list.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<JobCompany> getJobComList(){
        ArrayList<JobCompany> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "Select ID from job";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String ID = rs.getString("ID");
                Job job = getJob(ID);
                Company company = comSer.getCompany(job.getCompanyID());
                list.add(new JobCompany(job, company));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public JobCompany getJobCom(String JobID){
        Job job = getJob(JobID);
        Company company = comSer.getCompany(job.getCompanyID());
        JobCompany jobCompany = new JobCompany(job, company);
        return jobCompany;
    }
    public void applyCV(CVJob cvjob){
        try{
            Conn conn = new Conn();
            String sql = "INSERT INTO CV_job (cv_url, userID, status, date, JobID) " +
             "VALUES (?, ?, ?, CURRENT_DATE, ?)";
            PreparedStatement ps = conn.c.prepareStatement(sql);
            ps.setString(1, cvjob.getCvUrl());
            ps.setString(2, cvjob.getUserId());
            ps.setString(3, cvjob.getStatus());
            ps.setString(4, cvjob.getJobCompany().getJob().getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public CVJob getCVJob(int id){
        CVJob cvJob = new CVJob();
        try{
            Conn conn = new Conn();
            String sql = "SELECT * from cv_job where id = "+id+"";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                cvJob.setId(id);
                cvJob.setCvUrl(rs.getString("cv_url"));
                cvJob.setUserId(rs.getString("UserID"));
                cvJob.setStatus(rs.getString("status"));
                cvJob.setJobCompany((getJobCom(rs.getString("JobID"))));
                cvJob.setDate(rs.getDate("date"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cvJob;
    }
    public ArrayList<CVJob> getCVJobList(String UserID){
        ArrayList<CVJob> list = new ArrayList<>();
        try{
            Conn conn = new Conn();
            String sql = "SELECT id from cv_job where UserID = '"+UserID+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                int id = rs.getInt("id");
                list.add(getCVJob(id));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
