package net.palacesoft.trellolite;

import net.palacesoft.trellolite.login.LoginController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        app.run(args);
    }

    @Bean
    public Filter canAccessFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                String path = request.getServletPath();
                HttpSession session = request.getSession();
                if (session.getAttribute(LoginController.USER) == null
                        && !(path.contains("auth/loggedIn") || path.contains("auth/logIn"))) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        };
    }
}
