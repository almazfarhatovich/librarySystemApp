package appLibrary.dto.mapper;

import appLibrary.configs.security.JwtUtils;
import appLibrary.db.models.User;
import appLibrary.dto.responses.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserViewMapper {

    private final JwtUtils utils;

    public RegisterResponse viewUser(User user) {
        if (user == null) {
            return null;
        }
        assert false;
        String jwt = utils.generateToken(user.getEmail());
        return new RegisterResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                jwt,
                user.getRole()
        );
    }

    public List<RegisterResponse> view(List<User> users) {
        List<RegisterResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(viewUser(user));
        }
        return responses;
    }
}
