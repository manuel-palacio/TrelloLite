package net.palacesoft.trellolite.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class LoginController {

    public static final String USER = "user";

    @RequestMapping(value = "/loggedIn", method = RequestMethod.GET)
    public void loggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(USER) != null) {
            response.getWriter().print(true);
        } else {
            response.getWriter().print(false);
        }
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public void logIn(@RequestBody Credentials credentials, HttpServletRequest request, HttpServletResponse response) {
        String userName = credentials.getUsername();
        String password = credentials.getPassword();
        if (userName.equals("admin") && password.equals("admin")) {
            request.getSession().setAttribute(USER, userName);
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
