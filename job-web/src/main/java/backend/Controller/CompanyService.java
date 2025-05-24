package backend.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import backend.model.Company;

@Service
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
                return company;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
