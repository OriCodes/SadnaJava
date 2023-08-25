package com.forums.forum.auth;

import com.forums.forum.config.JwtService;
import com.forums.forum.exception.UserNameAlreadyExistException;
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

    public AuthenticationResponse register(RegisterRequest request) throws UserNameAlreadyExistException {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UserNameAlreadyExistException();
        }
        var user = User.builder()
               .userName(request.getUserName())
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
        var user = userRepository.findByUserName(request.getUserName()).orElseThrow(); //todo add correct exception

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
