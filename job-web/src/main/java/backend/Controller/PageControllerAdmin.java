package backend.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.model.Company;
import backend.model.User;

@Controller
public class PageControllerAdmin {
    @Autowired
    private CompanyService comSer;
    @Autowired
    private UserService userService;
    @Autowired
    private CVService CVSer;
    //Khởi tạo trang Admin duyệt thông tin
    @GetMapping("/Admin/Verify")
    public String verifyPage(Model model){
        ArrayList<Company> list = comSer.getVerifyList();
        model.addAttribute("companys", list);
        return "Admin/Verify";
    }
    //Hành động Trang admin khi duyệt thông tin
    @PostMapping("/Admin/Verify")
    public String verify(@RequestParam("comId") String comID){
        comSer.updateStatus(comID, "verified");
        return "redirect:/Admin/Verify";
    }
    @PostMapping("/Admin/Deny")
    public String Deny(@RequestParam("comId") String comID){
        comSer.updateStatus(comID, "denied");
        return "redirect:/Admin/Verify";
    }

    //Khởi tạo trang admin quản lý user 
    @GetMapping("/Admin/User")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        return "Admin/User";
    }
    //Hoạt động khóa/mở khóa tài khoản
    @PostMapping("/toggle-status")
    public String toggleStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
        String status1 = "private";
        if(!status.equals("locked")){
            status1 = "locked";
        }
        userService.updateStatus(id, status1);
        return "redirect:/Admin/User";
    }
    //Khởi tạo trang admin quản lý company
    @GetMapping("/Admin/Com")
    public String manageComs(Model model) {
        List<Company> list = comSer.getAllCom();
        model.addAttribute("companys", list);
        return "Admin/Com";
    }
    //Hoạt động khóa/mở khóa tài khoản cho company
    @PostMapping("/Admin/Com")
    public String updateStatus(@RequestParam("id") String id, @RequestParam("status") String status){
        String status1 = "verified";
        if(!status.equals("locked")){
            status1 = "locked";
        }
        comSer.updateStatus(id, status1);
        return "redirect:/Admin/Com";
    }
    //Trang thống kê
    @GetMapping("/Admin/statistic")
    public String statisticPage(Model model){
        int accepted = CVSer.countStatus("accepted");
        int denied = CVSer.countStatus("denied");
        model.addAttribute("accepted", accepted);
        model.addAttribute("denied", denied);
        return "Admin/statistic";
    }
}
