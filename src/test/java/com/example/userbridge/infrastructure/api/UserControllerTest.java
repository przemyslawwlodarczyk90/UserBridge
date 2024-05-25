package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import com.example.userbridge.domain.user.service.ConfirmationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
 class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @MockBean
    private ConfirmationService confirmationService;

    @Test
    public void registerUser() throws Exception {
        String userRegistrationRequest = """
            {
                "userDto": {
                    "firstName": "Jan",
                    "lastName": "Kowalski",
                    "email": "jan.kowalski@example.com",
                    "phoneNumber": "+48123456789",
                    "street": "Ulica Główna",
                    "postalCode": "00-123",
                    "city": "Warszawa"
                },
                "password": "haslo123"
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("User registered successfully"));
    }

    @Test
     void loginUser() throws Exception {
        String loginRequest = """
            {
                "email": "jan.kowalski@example.com",
                "password": "haslo123"
            }
        """;

        Mockito.when(userFacade.loginUser(any(LoginDto.class))).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("fake-jwt-token"));
    }

    @Test
     void editUser() throws Exception {
        String userEditRequest = """
            {
                "firstName": "Jan",
                "lastName": "Kowalski",
                "email": "jan.kowalski@example.com",
                "phoneNumber": "+48123456789",
                "street": "Ulica Główna",
                "postalCode": "00-123",
                "city": "Warszawa"
            }
        """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userEditRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User updated successfully"));
    }

    @Test
     void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userFacade).deleteUser(1L);
    }

    @Test
     void confirmUser() throws Exception {
        mockMvc.perform(get("/api/users/confirm")
                        .param("token", "fake-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User confirmed successfully"));
    }
}
