package backend.Controller;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import backend.model.User;

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
    public String login(String username, String password){
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM USER WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = conn.s.executeQuery(sql);
            String s = "";
            while(rs.next()){
                s = rs.getString("position");
                return s;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
