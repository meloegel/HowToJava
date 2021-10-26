package com.howto.services;

import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.HowTo;
import com.howto.models.Role;
import com.howto.models.Step;
import com.howto.models.User;
import com.howto.models.UserRoles;
import com.howto.models.Useremail;
import com.howto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HelperFunctions helperFunctions;

    public User findUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


    @Transactional
    @Override
    public void delete(long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userRepository.deleteById(id);
    }


    @Override
    public User findByName(String name) {
        User user = userRepository.findByUsername(name.toLowerCase());
        if (user == null) {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return user;
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();
        if (user.getUserid() != 0) {
            userRepository.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());

        newUser.getRoles().clear();
        for (UserRoles ur : user.getRoles()) {
            Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
            newUser.getRoles().add(new UserRoles(newUser, addRole));
        }
        newUser.getHowTos().clear();
        for (HowTo ht : user.getHowTos()) {
            newUser.getHowTos().add(new HowTo(ht.getName(), ht.getDescription(), ht.getCategory(), ht.getComplexity(), ht.getSteps(), newUser));
        }
        for (HowTo ht : user.getHowTos()) {
            for (Step st : ht.getSteps()) {
                ht.getSteps().add(new Step(st.getStep(), st.getHowto()));
            }
        }
        newUser.getUseremails().clear();
        for (Useremail ue : user.getUseremails()) {
            newUser.getUseremails().add(new Useremail(newUser, ue.getUseremail()));
        }

        return userRepository.save(newUser);
    }


    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername())) {
            if (user.getUsername() != null) {
                currentUser.setUsername(user.getUsername()
                        .toLowerCase());
            }
            if (user.getPassword() != null) {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }
            if (user.getPrimaryemail() != null) {
                currentUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());
            }
            if (user.getRoles().size() > 0) {
                currentUser.getRoles().clear();
                for (UserRoles ur : user.getRoles()) {
                    Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
                    currentUser.getRoles().add(new UserRoles(currentUser, addRole));
                }
            }
            if (user.getUseremails()
                    .size() > 0) {
                currentUser.getUseremails().clear();
                for (Useremail ue : user.getUseremails()) {
                    currentUser.getUseremails()
                            .add(new Useremail(currentUser, ue.getUseremail()));
                }
            }
            return userRepository.save(currentUser);
        } else {
            throw new OAuth2AccessDeniedException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
