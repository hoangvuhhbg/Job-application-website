package backend.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import backend.model.Company;

@Service

//Lấy Company theo ID
public class CompanyService {
    public Company getCompany(String ID){
        try {
            Conn conn = new Conn();
            String sql = "Select * from company where id = '"+ID+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                Company company = new Company();
                company.setId(rs.getString("ID"));
                company.setName(rs.getString("name"));
                company.setLogoUrl(rs.getString("logo_url"));
                company.setAddress(rs.getString("address"));
                company.setEmail(rs.getString("email"));
                company.setTaxNum(rs.getString("tax_num"));
                company.setLicenseUrl(rs.getString("license_url"));
                company.setStatus(rs.getString("status"));
                company.setPassword(rs.getString("password"));
                company.setPosition(rs.getString("position"));
                company.setDescription(rs.getString("description"));
                return company;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //Lấy danh sách company
    public ArrayList<Company> getComList(){
        ArrayList<Company> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "select * from company";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String comID = rs.getString("ID");
                Company company = getCompany(comID);
                list.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //Lấy danh sách company chưa được duyệt
    public ArrayList<Company> getVerifyList(){
        ArrayList<Company> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "select * from company where status = 'unverified'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String comID = rs.getString("ID");
                Company company = getCompany(comID);
                list.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Đổi status cho company
    public void updateStatus(String comID, String status){
        try {
            Conn conn = new Conn();
            String sql = "update company set status = '"+status+"' where id = '"+comID+"'";
            conn.s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Lấy danh sách tất cả company
    public ArrayList<Company> getAllCom(){
        ArrayList<Company> list = new ArrayList<>();
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM company where status = 'verified'";
            ResultSet rs = conn.s.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("ID");
                list.add(getCompany(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
