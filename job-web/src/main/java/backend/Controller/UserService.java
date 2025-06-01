package backend.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import backend.model.User;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    public User getUser(String id){
        User user = new User();
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM user WHERE id = '"+id+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone"));
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getString("status"));
                user.setAddress(rs.getString("address"));
                user.setMajor(rs.getString("major"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public void ChangeProfile(HttpSession session, User user){
        String CustomerID = String.valueOf(session.getAttribute("ID"));
        System.out.println(1);
        try {
            Conn conn = new Conn();
            String sql = "Update user set email = '"+user.getEmail()+"', phone = '"+user.getPhoneNumber()+"', major = '"+user.getMajor()+"', address = '"+user.getAddress()+"' where id = '"+CustomerID+"'";
            conn.s.executeUpdate(sql);
            
        } catch (Exception e) {
        }
    }
    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM user";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("ID");
                list.add(getUser(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void updateStatus(String id, String status){
        try {
            Conn conn = new Conn();
            System.out.println(status + id);
            String sql = "update user set status = '"+status+"' where id = '"+id+"'";
            conn.s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
