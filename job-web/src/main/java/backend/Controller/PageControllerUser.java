package backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageControllerUser {
    @Autowired
    private EntryService entry;
    //HomePage
    @GetMapping("/User/Home")
    public String homePage(){
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
}
