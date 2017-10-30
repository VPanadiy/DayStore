package dream.development.services;

import dream.development.model.User;

public interface UserService {

    User findUserByEmail(String email);

    void saveUser(User user);

}
