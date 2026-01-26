package de.carina.likeherotozero.service;

import de.carina.likeherotozero.model.User;
import de.carina.likeherotozero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzername nicht gefunden: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public User registerUser(String username, String encodedPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Benutzername bereits vergeben!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole("SCIENTIST");

        return userRepository.save(user);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
}