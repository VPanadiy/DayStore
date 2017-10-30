package dream.development.bootstrap;

import dream.development.model.Product;
import dream.development.model.Role;
import dream.development.model.User;
import dream.development.repositories.ProductRepository;
import dream.development.repositories.RoleRepository;
import dream.development.repositories.UserRepository;
import dream.development.services.RoleService;
import dream.development.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;
    private UserService userService;
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger log = Logger.getLogger(SpringJpaBootstrap.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
//        loadRoles();
//        loadUsers();
//        assignUsersToUserRole();
//        assignUsersToAdminRole();
    }

    private void loadProducts() {
        Product shirt = new Product();
        shirt.setDescription("Spring Framework Guru Shirt");
        shirt.setPrice(new BigDecimal("18.95"));
        shirt.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
        shirt.setProductId("235268845711068308");
        productRepository.save(shirt);

        log.info("Saved Shirt - id: " + shirt.getId());

        Product mug = new Product();
        mug.setDescription("Spring Framework Guru Mug");
        mug.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
        mug.setProductId("168639393495335947");
        mug.setPrice(new BigDecimal("11.95"));
        productRepository.save(mug);

        log.info("Saved Mug - id:" + mug.getId());
    }

//    private void loadRoles() {
//        Role userRole = new Role();
//        userRole.setRole("USER");
//        roleService.saveRole(userRole);
//        log.info("Saved role" + userRole.getRole());
//        Role adminRole = new Role();
//        adminRole.setRole("ADMIN");
//        roleService.saveRole(adminRole);
//        log.info("Saved role" + adminRole.getRole());
//    }
//
//    private void loadUsers() {
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword(bCryptPasswordEncoder.encode("user"));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole("USER");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//        userRepository.save(user);
//
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
//        admin.setActive(1);
//        Role adminRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
//        userRepository.save(admin);
//    }

//    private void assignUsersToUserRole() {
//        List<Role> roles = (List<Role>) roleService.listAll();
//        List<User> users = (List<User>) userService.listAll();
//
//        roles.forEach(role -> {
//            if (role.getRole().equalsIgnoreCase("USER")) {
//                users.forEach(user -> {
//                    if (user.getUsername().equals("user")) {
//                        user.addRole(role);
//                        userService.saveOrUpdate(user);
//                    }
//                });
//            }
//        });
//    }
//    private void assignUsersToAdminRole() {
//        List<Role> roles = (List<Role>) roleService.listAll();
//        List<User> users = (List<User>) userService.listAll();
//
//        roles.forEach(role -> {
//            if (role.getRole().equalsIgnoreCase("ADMIN")) {
//                users.forEach(user -> {
//                    if (user.getUsername().equals("admin")) {
//                        user.addRole(role);
//                        userService.saveOrUpdate(user);
//                    }
//                });
//            }
//        });
//    }
}


