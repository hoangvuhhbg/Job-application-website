package backend.Controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import backend.model.CVJob;
import backend.model.CVUpload;
import backend.model.JobCompany;
import backend.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageControllerUser {
    @Autowired
    private EntryService entry;
    @Autowired
    private JobService jobSer;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CVService CVSer;

    //HomePage
    @GetMapping("/User/Home")
    public String homePage(Model model, HttpSession session){
        ArrayList<JobCompany> JobCompanys = new ArrayList<>();
        JobCompanys = jobSer.getJobComList();
        model.addAttribute("JobCompanys", JobCompanys);
        return "User/Home";
    }

    //login Page
    @GetMapping("/User/login")
    public String loginPage() {
        return "User/login";
    }
    @PostMapping("/login")
    public String loginCheck(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){
        entry.login(session, email, password);
        String role = String.valueOf(session.getAttribute("role"));
        //check role
        if(role.equals("admin")){
            return "redirect:/User/admin";
        }
        else if(role.equals("user")){
            return "redirect:/User/Home";
        }
        else{
            model.addAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/User/login";
        }
    }

    //register
    @GetMapping("/User/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "User/register";
    }
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        entry.addAccount(user);
        return "redirect:/User/login";
    }
    @GetMapping("/User/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/User/Home";
    }

    @GetMapping("/User/JobDetail")
    public String JobDetailPage(@RequestParam(value="JobID") String JobId, Model model, HttpSession session){
        JobCompany jobCompany = jobSer.getJobCom(JobId);
        ArrayList<CVUpload> CVuploads = CVSer.getCVUploadlist(session);
        model.addAttribute("CVUploads", CVuploads);
        model.addAttribute("jobCompany", jobCompany);
        return "User/JobDetail";
    }
    @PostMapping("/User/Apply")
    public String applyJob(@RequestParam("jobID") String jobID, @RequestParam("CV") MultipartFile cvFile, HttpSession session){
        try {
            Map uploadResult = cloudinary.uploader().upload(cvFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            CVJob cvjob = new CVJob();
            cvjob.setCvUrl(imageUrl);
            cvjob.setUserId(String.valueOf(session.getAttribute("ID")));
            cvjob.setStatus("waiting");
            JobCompany jobCompany = jobSer.getJobCom(jobID);
            cvjob.setJobCompany((jobCompany));
            jobSer.applyCV(cvjob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/User/Home";
    }

    @GetMapping("/User/qlyCV")
    public String qlyCVPage(HttpSession session, Model model){
        ArrayList<CVUpload> CVuploads = CVSer.getCVUploadlist(session);
        model.addAttribute("CVUploads", CVuploads);
        return "User/qlyCV";
    }
    @PostMapping("/User/setmain")
    public String setmain(HttpSession session, @RequestParam("cvId") String cvID){
        String UserID = String.valueOf(session.getAttribute("ID"));
        try {
            Conn conn = new Conn();
            String sql = "update cv_upload set status = 'other' where Userid = '"+UserID+"'";
            conn.s.executeUpdate(sql);
            sql = "update cv_upload set status = 'main' where id = "+cvID+" and Userid = '"+UserID+"'";
            conn.s.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/User/qlyCV";
    }
    @PostMapping("/User/delCV")
    public String delCV(HttpSession session, @RequestParam("cvId") String cvID){
        String UserID = String.valueOf(session.getAttribute("ID"));
        try {
            Conn conn = new Conn();
            String sql = "Select cv_url from cv_upload where id = "+cvID+"";
            ResultSet rs = conn.s.executeQuery(sql);
            String cv_url = "";
            if(rs.next()){
                cv_url = rs.getString("cv_url");
            }
            sql = "delete from cv_upload where id = "+cvID+" and userid = '"+UserID+"'";
            conn.s.executeUpdate(sql);
            CVSer.delImg(cv_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/User/qlyCV";
    }

    @GetMapping("/User/uploadCV")
    public String uploadCVPage(){
        return "User/uploadCV";
    }
    @PostMapping("/User/uploadCV")
    public String uploadCV(@RequestParam("CVimg") MultipartFile file, HttpSession session, Model model){
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            CVUpload cv = new CVUpload();
            cv.setCvUrl(imageUrl);
            CVSer.uploadCV(cv, session);
            return "redirect:/User/qlyCV";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("Error", "Có lỗi xảy ra");
            return "redirect:/User/qlyCV";
        }
    }
    @GetMapping("/User/AppliedJob")
    public String AppliedJobPage(Model model, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        ArrayList<CVJob> list = jobSer.getCVJobList(UserID);
        model.addAttribute("CVJobs", list);
        return "User/AppliedJob";
    }
}
