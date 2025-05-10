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
public class PageController {
    @Autowired
    private EntryService entry;
    //HomePage
    @GetMapping("/Home")
    public String homePage(){
        return "Home";
    }

    //login Page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String loginCheck(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){
        String role = entry.login(username, password);

        //check role
        if(role.equals("admin")){
            session.setAttribute("username", username);
            return "redirect:/admin";
        }
        else if(role.equals("user")){
            session.setAttribute("username", username);
            return "redirect:/Home";
        }
        else{
            model.addAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }
    }

    //register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        entry.addAccount(user);
        return "redirect:/login";
    }
}
