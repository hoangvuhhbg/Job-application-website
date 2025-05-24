package backend.Controller;

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

    //intro page
    @GetMapping("/Company/intro")
    public String homePage(){
        return "Company/intro";
    }
    @GetMapping("/Company/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Company/intro";
    }
    //login
    @GetMapping("/Company/login")
    public String loginPage() {
        return "Company/login";
    }
    @PostMapping("/login_Com")
    public String loginCheck(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){
        entry.login_Com(session, email, password);
        String role = String.valueOf(session.getAttribute("role"));
        //check role
        if(role.equals("company")){
            return "redirect:/Company/Home";
        }
        else{
            model.addAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/Company/login";
        }
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

    @GetMapping("/Company/Home")
    public String HomePage(Model model) {
        return "Company/Home";
    }

    @GetMapping("/Company/addJob")
    public String addJobForm(Model model) {
        Job job = new Job();
        model.addAttribute("job", job);
        return "Company/addJob";
    }

    @PostMapping("/Company/addJob")
    public String submitJobForm(@ModelAttribute("job") Job job, HttpSession session){
        jobSer.addJob(session, job);
        return "redirect:/Company/Home";
    }
}
