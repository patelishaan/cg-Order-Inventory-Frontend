package orderinventory.cgorderinventoryfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Value("${backend.url}")
    private String backendUrl;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/auth/login";

        String role = (String) session.getAttribute("role");
        return switch (role) {
            case "STORE"     -> "redirect:/store";
            case "ORDERITEM" -> "redirect:/orderitem";
            case "ORDER"     -> "redirect:/order";
            case "INVENTORY" -> "redirect:/inventory";
            case "SHIPMENT"  -> "redirect:/shipment";
            default          -> "redirect:/auth/login";
        };
    }

    @GetMapping("/store")
    public String store(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/auth/login";
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("token", token);
        model.addAttribute("backendUrl", backendUrl);
        return "store";
    }
    
    

    @GetMapping("/shipment")
    public String shipment(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/auth/login";
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("token", token);
        model.addAttribute("backendUrl", backendUrl);
        return "shipment";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
    
    @GetMapping("/order")
    public String order(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");

        if (token == null) return "redirect:/auth/login";

        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("token", token);
        model.addAttribute("backendUrl", backendUrl);

        return "order";
    }
    
}