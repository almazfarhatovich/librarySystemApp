package appLibrary.apis;

import appLibrary.db.services.UserService;
import appLibrary.dto.requests.AuthRequest;
import appLibrary.dto.responses.AuthResponse;
import appLibrary.dto.requests.RegisterRequest;
import appLibrary.dto.responses.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "Any users can authenticate")
public class AuthApi {
    private final UserService service;

    @Operation(summary = "Registration ", description = "Any user can do registration")
    @PostMapping("register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return service.userRegister(request);
    }

    @Operation(summary = "Login", description = "Only registered users can login")
    @PostMapping("login")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return service.authenticate(authRequest);
    }

}
