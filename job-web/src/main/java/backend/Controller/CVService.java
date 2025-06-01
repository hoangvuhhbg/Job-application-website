package backend.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import backend.model.CVUpload;
import jakarta.servlet.http.HttpSession;

@Controller
public class CVService {
    @Autowired
    private Cloudinary cloudinary;

    //upload CV vào CSDL
    public void uploadCV(CVUpload cv, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        try{
            Conn conn = new Conn();
            String sql = "INSERT INTO CV_upload (cv_url, userID, status) " +
             "VALUES (?, ?, ?)";
                PreparedStatement ps = conn.c.prepareStatement(sql);
                ps.setString(1, cv.getCvUrl());
                ps.setString(2, UserID);
                ps.setString(3, "other");
                ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Lấy danh sách CV
    public ArrayList<CVUpload> getCVUploadlist(HttpSession session){      
        String UserID = String.valueOf(session.getAttribute("ID"));  
        ArrayList<CVUpload> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "Select * from cv_upload where userid = '"+UserID+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                CVUpload cv = new CVUpload();
                cv.setId(rs.getInt("ID"));
                cv.setCvUrl(rs.getString("cv_url"));
                cv.setUserId(UserID);
                cv.setStatus(rs.getString("status"));
                list.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //Lấy danh sách CV được công khai
    public ArrayList<CVUpload> getCVmainlist(){      
        ArrayList<CVUpload> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "SELECT * " +
                                "FROM cv_upload " +
                                "join user on cv_upload.userID = user.id " +
                                "where user.status = 'public'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                CVUpload cv = new CVUpload();
                cv.setId(rs.getInt("cv_upload.id"));
                cv.setCvUrl(rs.getString("cv_upload.cv_url"));
                cv.setUserId(rs.getString("cv_upload.userID"));
                cv.setStatus(rs.getString("cv_upload.status"));
                list.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Xóa CV đã upload theo url
    public void delImg(String url) {
        try{
            String[] parts = url.split("/");
            String filenameWithExt = parts[parts.length - 1];
            String publicID = filenameWithExt.split("\\.")[0];
            cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
        } catch(Exception e){
        }
    }
    //Đếm CV theo status
    public int countStatus(String status){
        int count = 0;
        try {
            Conn conn = new Conn();
            String sql = "select count(*) as count from cv_job where status = '"+status+"'";
            ResultSet rs = conn.s.executeQuery(sql);        
            while(rs.next()){
                count = rs.getInt("count");
            }
        } catch (Exception e) {
        }
        return count;
    }
}
