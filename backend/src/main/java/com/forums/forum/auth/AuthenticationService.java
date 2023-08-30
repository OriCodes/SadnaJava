package com.forums.forum.auth;

import com.forums.forum.config.JwtService;
import com.forums.forum.exception.IllegalUserNameException;
import com.forums.forum.model.Role;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws IllegalUserNameException {

        if(request.getUserName().isEmpty()){
            throw new IllegalUserNameException("User name can't be empty");
        }
        if (userRepository.existsByUsername(request.getUserName())) {
            throw new IllegalUserNameException("User name already exist");
        }

        var user = User.builder()
               .username(request.getUserName())
               .dob(request.getDob())
               .gender(request.getGender())
               .profileUrl(request.getProfileUrl())
               .password(passwordEncoder.encode(request.getPassword()))
               .role(Role.USER)
               .build();
       userRepository.save(user);
       var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUserName()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
