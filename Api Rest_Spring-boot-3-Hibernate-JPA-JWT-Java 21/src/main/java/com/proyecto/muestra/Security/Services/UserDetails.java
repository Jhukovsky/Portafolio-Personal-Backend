package com.proyecto.muestra.Security.Services;

import com.proyecto.muestra.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetails implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userSpring;
        try {
            com.proyecto.muestra.Class.User user = userRepository.findByuserName(userName);
            List<GrantedAuthority> authorities = Collections.emptyList();
            userSpring = new User(user.getUserName(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    authorities);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("El usuario " + userName + "no existe.");
        }
        return userSpring;
    }
}
