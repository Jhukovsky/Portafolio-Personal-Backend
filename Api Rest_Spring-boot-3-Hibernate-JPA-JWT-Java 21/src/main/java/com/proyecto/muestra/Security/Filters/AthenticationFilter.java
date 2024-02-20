package com.proyecto.muestra.Security.Filters;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.muestra.Login.UserLogin;
import com.proyecto.muestra.Security.JWT.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtils jwtUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLogin user = null;
        String userName = "";
        String password = "";

        try{
            user = new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);
            userName = user.userName();
            password = user.password();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String main = userName;
        String credentials = password;

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(main,credentials);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = (String) authResult.getPrincipal();
        String token = jwtUtils.getToken(userName);

        response.addHeader("Autorizado: ", token);

        Map<String,Object> httpResponse = new HashMap<>();
        httpResponse.put("token",token);
        httpResponse.put("message","Autenticacion correcta");
        httpResponse.put("User", userName);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

}
