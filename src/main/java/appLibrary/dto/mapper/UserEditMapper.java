package appLibrary.dto.mapper;

import appLibrary.db.models.User;
import appLibrary.dto.requests.RegisterRequest;
import appLibrary.enums.Role;
import appLibrary.exceptions.MatchException;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper {

    public User create(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        if (!request.getPassword().matches(request.getRepeatPassword())){
            throw new MatchException("Passwords don't matches!");
        }
        else {
            user.setPassword(request.getPassword());
        }

        user.setRole(Role.USER);
        return user;
    }

    public void update(User user, RegisterRequest request) {
        if (!user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }
}
