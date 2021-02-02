package ru.gafarov.Messenger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.model.Role;
import ru.gafarov.Messenger.model.Status;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.repository.RoleRepository;
import ru.gafarov.Messenger.repository.UserRepository;
import ru.gafarov.Messenger.security.jwt.JwtTokenProvider;
import ru.gafarov.Messenger.service.Transcrypter;
import ru.gafarov.Messenger.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final Transcrypter transcrypter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder
            , JwtTokenProvider jwtTokenProvider, Transcrypter transcrypter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.transcrypter = transcrypter;
    }


    @Override
    public User register(User user) {

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles= new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll: {} users found", result);
         return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", result.getEmail(), id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted",id);

    }

    @Override
    public Long getMyId(String token) {
        String myName = jwtTokenProvider.getUserName(token);
        User me =  findByUsername(myName);
        log.info("IN getMyId user: {} found", me);
        return me.getId();
    }

    @Override
    public List<User> searchPeople(String partOfName) {
        String partOfNameLowerCyrilic = transcrypter.toCyrilic(partOfName);
        String partOfNameLowerLatin = transcrypter.toLatin(partOfName);
        log.info("IN toCyrilic result: {}", partOfNameLowerCyrilic);
        log.info("IN toLatin reult: {}", partOfNameLowerLatin);

        return userRepository.searchPeople(partOfName, partOfNameLowerCyrilic, partOfNameLowerLatin);
    }
}
