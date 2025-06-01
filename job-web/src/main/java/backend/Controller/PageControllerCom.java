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
import backend.model.Company;
import backend.model.Job;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageControllerCom {
    @Autowired
    private EntryService entry;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private JobService jobSer;
    @Autowired
    private CVService CVSer;
    @Autowired
    private CompanyService comSer;

    //intro page
    @GetMapping("/Company/intro")
    public String homePage(){
        return "Company/intro";
    }
    //Khi sang logout
    @GetMapping("/Company/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Company/intro";
    }
    //login page
    @GetMapping("/Company/login")
    public String loginPage() {
        return "Company/login";
    }
    //Kiểm tra login của company
    @PostMapping("/login_Com")
    public String loginCheck(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){
        entry.login_Com(session, email, password);
        String role = String.valueOf(session.getAttribute("role"));
        String status = String.valueOf(session.getAttribute("status"));
        //check role
        if(role.equals("company")){
            if(status.equals("verified")){
                return "redirect:/Company/Home";
            }
            else if(status.equals("unverified") || status.equals("denied")){
                return "redirect:/Company/Verify";
            }
            else{
                return "redirect:/Company/logout";
            }
        }
        else{
            model.addAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/Company/login";
        }
    }
    //Trang đợi khi company chưa được duyệt
    @GetMapping("/Company/Verify")
    public String VerifyPage(HttpSession session, Model model){
        String comID = String.valueOf(session.getAttribute("ID"));
        Company company = comSer.getCompany(comID);
        model.addAttribute("company", company);
        return "Company/Verify";
    }
    //Hoạt động update lại thông tin duyệt
    @PostMapping("/Company/updateLicense")
    public String updateLicense(HttpSession session, @RequestParam("CVimg") MultipartFile cvFile, @RequestParam("taxNum") String taxNum){
        String comID = String.valueOf(session.getAttribute("ID"));
        try {
            Map uploadResult = cloudinary.uploader().upload(cvFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            Conn conn = new Conn();
            String sql = "Select license_url from company where id = '"+comID+"'";
            ResultSet rs = conn.s.executeQuery(sql);
            if(rs.next()){
                String oldUrl = rs.getString("license_url");
                CVSer.delImg(oldUrl);
            }
            sql = "update company set license_url = '"+imageUrl+"', tax_Num = '"+taxNum+"', status = 'unverified' where id = '"+comID+"'";
            conn.s.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/Company/Verify";
    }

    //register
    @GetMapping("/Company/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("Company", new Company());
        return "Company/register";
    }
    @PostMapping("/register_Com")
    public String processProductForm(@ModelAttribute("Company") Company company, @RequestParam("logoFile") MultipartFile logofile,  @RequestParam("licenseFile") MultipartFile licensefile, Model model){
        //upload + lay url
        try {
            Map uploadResult = cloudinary.uploader().upload(logofile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            company.setLogoUrl(imageUrl);
            uploadResult = cloudinary.uploader().upload(licensefile.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("secure_url");
            company.setLicenseUrl(imageUrl);
            company.setPosition("company");
            company.setStatus("unverified");
            entry.addCompany(company, model);
            return "redirect:/Company/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("Error", "Có lỗi xảy ra");
            return "redirect:/Company/login";
        }
    }
    //Trang danh sách CV
    @GetMapping("/Company/Home")
    public String HomePage(Model model) {
        ArrayList<CVUpload> list = CVSer.getCVmainlist();
        model.addAttribute("CVs", list);
        return "Company/Home";
    }
    //Form thêm hjob
    @GetMapping("/Company/addJob")
    public String addJobForm(Model model) {
        Job job = new Job();
        model.addAttribute("job", job);
        return "Company/addJob";
    }
    //Hành động thêm job
    @PostMapping("/Company/addJob")
    public String submitJobForm(@ModelAttribute("job") Job job, HttpSession session){
        jobSer.addJob(session, job);
        return "redirect:/Company/Home";
    }
    //Trang quản lý công việc
    @GetMapping("/Company/jobManager")
    public String jobManager(HttpSession session, Model model){
        String comID = String.valueOf(session.getAttribute("ID"));
        ArrayList<Job> list = jobSer.getJobListByCom(comID);
        model.addAttribute("jobs", list);
        return "Company/jobManager";
    }
    //Hoạt động khi xóa việc
    @PostMapping("/Company/delJob")
    public String delJob(@RequestParam("jobId") String JobID){
        jobSer.delJob(JobID);
        return "redirect:/Company/jobManager";
    }

    //Trang duyệt CV
    @GetMapping("/Company/ApproveCV")
    public String ApproveCVPage(@RequestParam("JobID") String JobID, Model model, HttpSession session){
        ArrayList<CVJob> acceptedList = jobSer.getCVJobListByCom(JobID, "accepted", session);
        ArrayList<CVJob> waitingList = jobSer.getCVJobListByCom(JobID, "waiting", session);
        model.addAttribute("JobID", JobID);
        model.addAttribute("acceptedList", acceptedList);
        model.addAttribute("waitingList", waitingList);
        return "Company/ApproveCV";
    }
    //Hành động duyệt
    @PostMapping("/Company/acceptCV")
    public String acceptCV(@RequestParam("cvId") int cvId, @RequestParam("JobID") String JobID){
        jobSer.updateCVJob(cvId, "accepted");
        return "redirect:/Company/ApproveCV?JobID=" + JobID;
    }
    @PostMapping("/Company/denyCV")
    public String denyCV(@RequestParam("cvId") int cvId, @RequestParam("JobID") String JobID){
        jobSer.updateCVJob(cvId, "denied");
        return "redirect:/Company/ApproveCV?JobID=" + JobID;
    }

    //Trang xem trang thông tin Page
    @GetMapping("/Company/companyPage")
    public String companyPage(HttpSession session, Model model){
        String comID = String.valueOf(session.getAttribute("ID"));
        Company company = comSer.getCompany(comID);
        ArrayList<Job> list = jobSer.getJobListByCom(comID);
        model.addAttribute("company", company);
        model.addAttribute("jobs", list);
        return "Company/companyPage";
    }
}
