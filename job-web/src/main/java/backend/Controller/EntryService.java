package backend.Controller;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import backend.model.Company;
import backend.model.User;
import jakarta.servlet.http.HttpSession;

@Service
public class EntryService {
    public void addAccount(User user){
        try{
            Conn conn = new Conn();
            String sql = "SELECT * FROM USER WHERE username = '" + user.getUsername()+ "'";
            ResultSet rs1 = conn.s.executeQuery(sql);
            if(rs1.next()){
                
            }
            else{
                sql = "SELECT COUNT(*) FROM USER";
                ResultSet rs = conn.s.executeQuery(sql);
                
                if (rs.next()) {
                    int count = rs.getInt(1);              
                    String newID = String.format("USER%04d", count + 1);
                    sql = "INSERT INTO USER(ID, name, email, phone, username, password, position) values('"+newID+"','"+user.getName()+"', '"+user.getEmail()+"', '"+user.getPhoneNumber()+"', '"+user.getUsername()+"', '"+user.getPassword()+"', 'user')";
                    conn.s.executeUpdate(sql);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCompany(Company company, Model model){
        try{
            Conn conn = new Conn();
            String sql = "SELECT * FROM company WHERE email = '" + company.getEmail()+ "'";
            ResultSet rs1 = conn.s.executeQuery(sql);
            if(rs1.next()){
                model.addAttribute("Error", "Email đã tồn tại");
            }
            else{
                sql = "SELECT COUNT(*) FROM Company";
                ResultSet rs = conn.s.executeQuery(sql);
                
                if (rs.next()) {
                    int count = rs.getInt(1);              
                    String newID = String.format("COM%04d", count + 1);
                    sql = "INSERT INTO Company(ID, name, logo_url, email, password, tax_num, license_url, status, position, address) values('"+newID+"','"+company.getName()+"', '"+company.getLogoUrl()+"', '"+company.getEmail()+"', '"+company.getPassword()+"', '"+company.getTaxNum()+"', '"+company.getLicenseUrl()+"', '"+company.getStatus()+"', '"+company.getPosition()+"', '"+company.getAddress()+"')";
                    conn.s.executeUpdate(sql);
                }
            }
            
        } catch (SQLException e) {
            model.addAttribute("Error", "Có lỗi xảy ra");
            e.printStackTrace();
        }
    }
    public void login(HttpSession session, String email, String password){
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM USER WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String UserID = rs.getString("ID");
                String role = rs.getString("position");
                session.setAttribute("ID", UserID);
                session.setAttribute("role", role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void login_Com(HttpSession session, String email, String password){
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM Company WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String UserID = rs.getString("ID");
                String role = rs.getString("position");
                session.setAttribute("ID", UserID);
                session.setAttribute("role", role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
