package com.main.spring_boot_crud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.spring_boot_crud.model.User;
import com.main.spring_boot_crud.model.UserRequest;
import com.main.spring_boot_crud.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUserByIdSuccess() throws Exception {
        User mockupUser = new User(99999999, "mockup99999999", "mockup", "mockup");

        when(userService.getUserById(99999999)).thenReturn(mockupUser);

        mockMvc.perform(get("/user/99999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99999999))
                .andExpect(jsonPath("$.username").value("mockup99999999"));
    }

    @Test
    void testGetUserByIdFaildNull() throws Exception {

        when(userService.getUserById(99999999)).thenReturn(null);

        mockMvc.perform(get("/user/99999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUserSuccess() throws Exception {
        List<User> mockupData = Arrays.asList(
                new User(99999997, "mockup", "mockup", "mockup"),
                new User(99999998, "mockup", "mockup", "mockup"),
                new User(99999999, "mockup", "mockup", "mockup"));

        when(userService.getAllUsers()).thenReturn(mockupData);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        UserRequest user = new UserRequest("mockup", "mockup", "mockup");
        User newUser = new User(user);
        when(userService.createUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("mockup"));
    }

    @Test
    void testCreateUserFound() throws Exception {
        UserRequest user = new UserRequest("mockup", "mockup", "mockup");
        User newUser = new User(user);

        when(userService.getUserByUsername(user.getUsername())).thenReturn(newUser);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userService.getUserById(mockupUser.getId())).thenReturn(mockupUser);

        when(userService.updateUser(any(User.class))).thenReturn(mockupUser);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockupUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mockup"));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userService.getUserById(mockupUser.getId())).thenReturn(null);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockupUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userService.getUserById(mockupUser.getId())).thenReturn(mockupUser);

        doNothing().when(userService).deleteUserById(mockupUser.getId());

        mockMvc.perform(delete("/user/{id}", mockupUser.getId()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserById(mockupUser.getId());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        User mockupUser = new User(99999999, "mockup", "mockup", "mockup");

        when(userService.getUserById(mockupUser.getId())).thenReturn(null);

        mockMvc.perform(delete("/user/{id}", mockupUser.getId()))
                .andExpect(status().isNotFound());
    }
}
