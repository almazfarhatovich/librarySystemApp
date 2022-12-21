package appLibrary.db.services;

import appLibrary.configs.security.JwtUtils;
import appLibrary.db.models.User;
import appLibrary.db.repositories.UserRepository;
import appLibrary.dto.requests.AuthRequest;
import appLibrary.dto.responses.AuthResponse;
import appLibrary.dto.mapper.UserEditMapper;
import appLibrary.dto.mapper.UserViewMapper;
import appLibrary.dto.requests.RegisterRequest;
import appLibrary.dto.responses.RegisterResponse;
import appLibrary.exceptions.AlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserEditMapper editMapper;
    private final UserViewMapper viewMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest authRequest) {

        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new IllegalStateException("not found"));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("BadCredentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                token,
                user.getRole()
        );
    }

    public RegisterResponse userRegister(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Email already registered!");
        }
        User user = editMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return viewMapper.viewUser(user);
    }
}
