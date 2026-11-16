package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
import com.example.userbridge.domain.user.exception.TokenExpiredException;
import com.example.userbridge.domain.user.exception.TokenNotFoundException;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.domain.user.service.ConfirmationService;
import com.example.userbridge.domain.user.service.LoginService;
import com.example.userbridge.domain.user.service.DeleteUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
 class GlobalExceptionHandlerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private LoginService loginService;

   @MockBean
   private ConfirmationService confirmationService;

   @MockBean
   private DeleteUserService deleteUserService;

   @Test
    void handleValidationExceptions() throws Exception {
      String invalidRequest = "{ \"email\": \"invalid\", \"password\": \"\" }";

      mockMvc.perform(post("/api/users/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidRequest))
              .andExpect(status().isBadRequest())
              .andExpect(jsonPath("$.email").value("Podaj poprawny adres e-mail"))
              .andExpect(jsonPath("$.password").value("Hasło jest wymagane"));
   }

   @Test
    void handleTokenNotFoundException() throws Exception {
      Mockito.doThrow(new TokenNotFoundException("Link aktywacyjny jest nieprawidłowy lub został już użyty"))
              .when(confirmationService).confirmToken("invalid-token");

      mockMvc.perform(get("/api/users/confirm")
                      .param("token", "invalid-token"))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$").value("Link aktywacyjny jest nieprawidłowy lub został już użyty"));
   }

   @Test
    void handleTokenExpiredException() throws Exception {
      Mockito.doThrow(new TokenExpiredException("Link aktywacyjny wygasł"))
              .when(confirmationService).confirmToken("expired-token");

      mockMvc.perform(get("/api/users/confirm")
                      .param("token", "expired-token"))
              .andExpect(status().isBadRequest())
              .andExpect(jsonPath("$").value("Link aktywacyjny wygasł"));
   }

   @Test
   @WithMockUser(roles = "ADMIN")
    void handleUserNotFoundException() throws Exception {
      Mockito.doThrow(new UserNotFoundException("Nie znaleziono użytkownika"))
              .when(deleteUserService).delete(1L);

      mockMvc.perform(delete("/api/users/1"))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$").value("Nie znaleziono użytkownika"));
   }

   @Test
    void handleInvalidCredentialsException() throws Exception {
      String invalidLogin = "{ \"email\": \"invalid@example.com\", \"password\": \"wrongpassword\" }";

      Mockito.doThrow(new InvalidCredentialsException("Nieprawidłowy e-mail lub hasło"))
              .when(loginService).login(Mockito.any());

      mockMvc.perform(post("/api/users/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidLogin))
              .andExpect(status().isUnauthorized())
              .andExpect(jsonPath("$").value("Nieprawidłowy e-mail lub hasło"));
   }
}
