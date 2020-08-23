package com.kuro.ims.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuro.ims.config.WithMockCustomUser;
import com.kuro.ims.entity.User;
import com.kuro.ims.service.UserService;
import com.kuro.ims.type.Role;
import java.util.Collections;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;


    @Test
    @WithMockCustomUser(role = Role.SALES_PERSON)
    public void whenGetCurrentUser_thenReturnUser() throws Exception
    {
        //given
        User user = new User();
        user.setName("Adewale Olaitan");
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setAddress("Lagos");
        user.setImage("someImageBinary");
        user.setPhone("000000000");

        when(userService.getUser(any())).thenReturn(user);

        //when / then
        mockMvc.perform(get("/api/users/me"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.address").value("Lagos"))
            .andExpect(jsonPath("$.data.email").value("user@test.com"))
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.image").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("Adewale Olaitan"))
            .andExpect(jsonPath("$.data.phone").value("000000000"));

        verify(userService).getUser(eq(1L));
    }


    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void whenGetUsers_thenReturnUsers() throws Exception
    {
        //given
        User user = new User();
        user.setName("Adewale Olaitan");
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setAddress("Lagos");
        user.setImage("someImageBinary");
        user.setPhone("000000000");

        when(userService.getUsers()).thenReturn(Collections.singletonList(user));

        //when / then
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(1))
            .andExpect(jsonPath("$.data[0].email").value("user@test.com"))
            .andExpect(jsonPath("$.data[0].id").value(1L))
            .andExpect(jsonPath("$.data[0].image").isNotEmpty())
            .andExpect(jsonPath("$.data[0].name").value("Adewale Olaitan"))
            .andExpect(jsonPath("$.data[0].phone").value("000000000"));

        verify(userService).getUsers();
    }


    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void whenCreateUser_thenCreateUser() throws Exception
    {
        //given
        User user = new User();
        user.setName("Adewale Olaitan");
        user.setEmail("user@test.com");
        user.setAddress("Lagos");
        user.setImage("someImageBinary");
        user.setPhone("00000000000");
        user.setRole(Role.SALES_PERSON);

        //when / then
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(Strings.EMPTY));

        verify(userService).createUser(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).isEqualToComparingFieldByField(user);
    }


    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void whenUpdateUser_thenUpdateUser() throws Exception
    {
        //given
        User user = new User();
        user.setName("Adewale Olaitan");
        user.setEmail("user@test.com");
        user.setAddress("Lagos");
        user.setImage("someImageBinary");
        user.setPhone("00000000000");
        user.setRole(Role.SALES_PERSON);

        //when / then
        mockMvc.perform(
            put("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(Strings.EMPTY));

        verify(userService).updateUser(eq(1L), userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).isEqualToComparingFieldByField(user);
    }


    @Test
    @WithMockCustomUser(role = Role.ADMIN, id = 2L)
    public void whenUpdateCurrentUser_thenUpdateUser() throws Exception
    {
        //given
        User user = new User();
        user.setName("Adewale Olaitan");
        user.setAddress("Lagos");
        user.setImage("someImageBinary");
        user.setPhone("00000000000");
        user.setRole(Role.SALES_PERSON);

        //when / then
        mockMvc.perform(
            put("/api/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(Strings.EMPTY));

        verify(userService).updateCurrentUser(eq(2L), userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).isEqualToComparingFieldByField(user);
    }


    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void whenGetRoles_thenReturnRoles() throws Exception
    {
        //given
        //when / then
        mockMvc.perform(get("/api/users/roles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(2));
    }
}
