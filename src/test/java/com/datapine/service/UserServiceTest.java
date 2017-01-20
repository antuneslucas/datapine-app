package com.datapine.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.datapine.dao.UserRepository;
import com.datapine.domain.User;
import com.datapine.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserServiceImpl userService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfTryToRegisterWithNullEmail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email must not be empty!");
        userService.register(null, "password");
    }

    @Test
    public void shouldThrowExceptionIfTryToRegisterWithEmptyEmail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email must not be empty!");
        userService.register("", "password");
    }

    @Test
    public void shouldThrowExceptionIfTryToRegisterWithNullPassword() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password must not be empty!");
        userService.register("user@user.com", null);
    }

    @Test
    public void shouldThrowExceptionIfTryToRegisterWithEmptyPassword() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password must not be empty!");
        userService.register("user@user.com", "");
    }

    @Test
    public void shouldThrowExceptionIfTryToRegisterWithExistentEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(buildDummyUser());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("User with that email already exists!");
        userService.register("user@user.com", "password");
    }

    @Test
    public void shouldRegisterIfEmailAndPasswordAreFine() {
        String email = "user@user.com";
        String password = "password";

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save((User) anyObject())).thenReturn(buildDummyUser());

        User user = userService.register(email, password);

        assertThat(user, is(notNullValue()));
        assertThat(user.getEmail(), is(email));
        assertThat(user.getPassword(), is(password));
    }

    private User buildDummyUser() {
        return new User("user@user.com", "password", null);
    }

}
