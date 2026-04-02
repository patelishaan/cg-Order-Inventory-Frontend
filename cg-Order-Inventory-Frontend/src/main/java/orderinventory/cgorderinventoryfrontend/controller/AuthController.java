package orderinventory.cgorderinventoryfrontend.controller;


import jakarta.servlet.http.HttpSession;
import orderinventory.cgorderinventoryfrontend.dto.LoginRequestDto;
import orderinventory.cgorderinventoryfrontend.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${backend.url}")
    private String backendUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto request, HttpSession session, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LoginRequestDto> entity = new HttpEntity<>(request, headers);

            ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity(
                    backendUrl + "/auth/login",
                    entity,
                    LoginResponseDto.class
            );

            LoginResponseDto body = response.getBody();
            session.setAttribute("jwt", body.getToken());
            session.setAttribute("email", body.getEmail());
            session.setAttribute("role", body.getRole());

            return "redirect:/dashboard";

        } catch (HttpClientErrorException e) {
            model.addAttribute("error", "Invalid email or password");
            model.addAttribute("loginRequest", request);
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Could not reach the server. Is the backend running?");
            model.addAttribute("loginRequest", request);
            return "login";
        }
    }
}
