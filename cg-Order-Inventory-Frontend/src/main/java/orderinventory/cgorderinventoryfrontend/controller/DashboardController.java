package orderinventory.cgorderinventoryfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("token", token);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}