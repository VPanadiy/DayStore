package dream.development.services;

import dream.development.model.User;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

}
