package backend.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import backend.model.Company;
import backend.model.Job;
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
    @Autowired
    private UserService userSer;
    @Autowired
    private CompanyService comSer;

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
        String status = String.valueOf(session.getAttribute("status"));
        //check role
        if(role.equals("admin")){
            return "redirect:/Admin/Verify";
        }
        else if(role.equals("user")){
            if(!status.equals("locked")){
                return "redirect:/User/Home";
            }
            else{
                return "redirect:/User/logout";
            }
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

    //Trang chi tiết
    @GetMapping("/User/JobDetail")
    public String JobDetailPage(@RequestParam(value="JobID") String JobId, Model model, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        JobCompany jobCompany = jobSer.getJobCom(JobId);
        ArrayList<CVUpload> CVuploads = CVSer.getCVUploadlist(session);
        String status = "notsaved";
        if(jobSer.checkSaved(UserID, JobId)){
            status = "saved";
        }
        model.addAttribute("status", status);
        model.addAttribute("CVUploads", CVuploads);
        model.addAttribute("jobCompany", jobCompany);
        return "User/JobDetail";
    }
    //Hành động ứng tuyển
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
    //Hành động lưu tin
    @PostMapping("/User/savedJob")
    public String saveJob(@RequestParam("jobID") String jobID,  @RequestParam("status") String status, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        String role = String.valueOf(session.getAttribute("role"));
        if(role.equals("user")){
            if(status.equals("notsaved")){
                jobSer.saveJob(jobID, UserID);
            }
            else if(status.equals("saved")){
                jobSer.unsaveJob(jobID, UserID);
            }
        }
        return "redirect:/User/savedJob";
    }


    //Trang quản lý CV
    @GetMapping("/User/qlyCV")
    public String qlyCVPage(HttpSession session, Model model){
        ArrayList<CVUpload> CVuploads = CVSer.getCVUploadlist(session);
        model.addAttribute("CVUploads", CVuploads);
        return "User/qlyCV";
    }
    //Cho CV làm main
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
    //Xóa CV
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

    //Trang upload CV
    @GetMapping("/User/uploadCV")
    public String uploadCVPage(){
        return "User/uploadCV";
    }
    //UploadCV
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
    //Xem tin đã ứng tuyển
    @GetMapping("/User/AppliedJob")
    public String AppliedJobPage(Model model, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        ArrayList<CVJob> list = jobSer.getCVJobList(UserID);
        model.addAttribute("CVJobs", list);
        return "User/AppliedJob";
    }

    //Xem trang cá nhân
    @GetMapping("/User/Profile")
    public String profilePage(Model model, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        User user = userSer.getUser(UserID);
        model.addAttribute("user", user);
        return "User/Profile";
    }
    //Hoạt động đổi trạng thái
    @PostMapping("/User/setStatus")
    public String setStatus(HttpSession session, @RequestParam("status") String status){
        String UserID = String.valueOf(session.getAttribute("ID"));
        try {
            Conn conn = new Conn();
            String status1 = "";
            if(status.equals("private")){
                status1 = "public";
            }
            else if(status.equals("public")){
                status1 = "private";
            }
            String sql = "update user set status = '"+status1+"' where id = '"+UserID+"'";
            conn.s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/User/Profile";
    }
    //Hoạt động lưu tt cá nhân
    @PostMapping("/User/ChangeProfile")
    public String changeProfile(@ModelAttribute("user") User user, HttpSession session){
        userSer.ChangeProfile(session, user);
        return "redirect:/User/Profile";
    }
    //Trang xem tin đã lưu
    @GetMapping("/User/savedJob")
    public String savedJobPage(Model model, HttpSession session){
        String UserID = String.valueOf(session.getAttribute("ID"));
        ArrayList<JobCompany> JobCompanys = new ArrayList<>();
        JobCompanys = jobSer.getsavedJobComList(UserID);
        model.addAttribute("JobCompanys", JobCompanys);
        return "User/savedJob";
    }

    //Xem trang thông tin công ty
    @GetMapping("/User/CompanyPage")
    public String companyPage(Model model, @RequestParam("ComID") String comID){
        Company company = comSer.getCompany(comID);
        ArrayList<Job> list = jobSer.getJobListByCom(comID);
        model.addAttribute("company", company);
        model.addAttribute("jobs", list);
        return "User/CompanyPage";
    }
}
