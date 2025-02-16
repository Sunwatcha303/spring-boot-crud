package com.main.spring_boot_crud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.main.spring_boot_crud.model.User;
import com.main.spring_boot_crud.repository.UserRepository;

@SpringBootTest
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByIdSuccess() {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userRepository.findById(99999999)).thenReturn(java.util.Optional.of(mockupUser));

        User findUser = userService.getUserById(99999999);

        assertNotNull(findUser);
        assertEquals(findUser.getId(), 99999999);
        assertEquals(findUser.getUsername(), "mockup");

        verify(userRepository, times(1)).findById(99999999);
    }

    @Test
    void testGetAllUserSuccess() {
        List<User> mockupData = Arrays.asList(
                new User(99999997, "mockup", "mockup", "mockup"),
                new User(99999998, "mockup", "mockup", "mockup"),
                new User(99999999, "mockup", "mockup", "mockup"));

        when(userRepository.findAll()).thenReturn(mockupData);

        List<User> findAllUser = userService.getAllUsers();

        assertNotNull(findAllUser);
        assertEquals(findAllUser.size(), 3);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByUsernameSuccess() {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userRepository.findByUsername("mockup")).thenReturn(java.util.Optional.of(mockupUser));

        User findUser = userService.getUserByUsername("mockup");

        assertNotNull(findUser);
        assertEquals(findUser.getId(), 99999999);
        assertEquals(findUser.getUsername(), "mockup");

        verify(userRepository, times(1)).findByUsername("mockup");
    }

    @Test
    void testDeleteUserSuccess() {
        doNothing().when(userRepository).deleteById(99999999);

        userService.deleteUserById(99999999);

        verify(userRepository, times(1)).deleteById(99999999);
    }

    @Test
    void testUpdateUserSuccess() {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userRepository.findById(99999999)).thenReturn(java.util.Optional.of(mockupUser));

        when(userRepository.save(any(User.class))).thenReturn(mockupUser);

        User findUser = userService.updateUser(mockupUser);

        assertNotNull(findUser);
        assertEquals(findUser.getId(), 99999999);
        assertEquals(findUser.getUsername(), "mockup");

        verify(userRepository, times(1)).save(mockupUser);
    }

    @Test
    void testUpdateUserNotFound() {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userRepository.findById(99999999)).thenReturn(Optional.empty());

        User findUser = userService.updateUser(mockupUser);

        assertNull(findUser);
    }

    @Test
    void testCreateUserSuccess() {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userRepository.save(any(User.class))).thenReturn(mockupUser);

        User findUser = userService.createUser(mockupUser);

        assertNotNull(findUser);
        assertEquals(findUser.getId(), 99999999);
        assertEquals(findUser.getUsername(), "mockup");

        verify(userRepository, times(1)).save(mockupUser);
    }
}