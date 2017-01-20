package com.datapine.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.datapine.aop.LoggableAOP;
import com.datapine.dao.UserRepository;
import com.datapine.domain.Roles;
import com.datapine.domain.User;
import com.datapine.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() { //pre register 2 users in the DB
        userRepository.save(new User("user", passwordEncoder.encode("user"), Collections.singleton(Roles.USER)));
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), Stream.of(Roles.USER, Roles.ADMIN).collect(Collectors.toSet())));
    }

    @Override
    public User register(String email, String password) {

        Assert.isTrue(!isEmpty(email), "Email must not be empty!");
        Assert.isTrue(!isEmpty(password), "Password must not be empty!");

        //TODO validate email syntax and if it exists

        String emailCleared = clearEmail(email);
        User user = userRepository.findByEmail(emailCleared);

        if(nonNull(user))
            throw new IllegalArgumentException("User with that email already exists!");

        return userRepository.save(new User(emailCleared, passwordEncoder.encode(password), Collections.singleton(Roles.USER)));
    }

    private String clearEmail(@NotNull String email) {
        return email.trim().toLowerCase();
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    @Override
    public User updatePassword(Long userId, String oldPasswordRaw, String newPasswordRaw) {

        Assert.isTrue(!isEmpty(oldPasswordRaw), "Old password must not be empty!");
        Assert.isTrue(!isEmpty(newPasswordRaw), "New password must not be empty!");

        User user = userRepository.findOne(userId);

        if(!isPasswordValid(user, oldPasswordRaw))
            throw new IllegalArgumentException("User not found or password don't match!");

        user.setPassword(passwordEncoder.encode(newPasswordRaw));

        return userRepository.save(user);
    }

    private boolean isPasswordValid(User user, String passwordToTest) {
        return nonNull(user) && passwordEncoder.matches(passwordToTest, user.getPassword());
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(clearEmail(email));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }

    @Override
    @LoggableAOP
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if(isNull(user))
            throw new UsernameNotFoundException("User with email: " + email + " does not exist");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(final User user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

}
