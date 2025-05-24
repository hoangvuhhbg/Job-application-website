package backend.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import backend.model.Job;
import jakarta.servlet.http.HttpSession;

@Service
public class JobService {
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
}
