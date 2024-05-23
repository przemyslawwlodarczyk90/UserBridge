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
public class GlobalExceptionHandlerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private LoginService loginService;

   @MockBean
   private ConfirmationService confirmationService;

   @MockBean
   private DeleteUserService deleteUserService;

   @Test
   public void handleValidationExceptions() throws Exception {
      String invalidRequest = "{ \"email\": \"invalid\", \"password\": \"\" }";

      mockMvc.perform(post("/api/users/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidRequest))
              .andExpect(status().isBadRequest())
              .andExpect(jsonPath("$.email").value("Email should be valid"))
              .andExpect(jsonPath("$.password").value("Password is mandatory"));
   }

   @Test
   public void handleTokenNotFoundException() throws Exception {
      Mockito.doThrow(new TokenNotFoundException("Token not found"))
              .when(confirmationService).confirmToken("invalid-token");

      mockMvc.perform(get("/api/users/confirm")
                      .param("token", "invalid-token"))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$").value("Token not found"));
   }

   @Test
   public void handleTokenExpiredException() throws Exception {
      Mockito.doThrow(new TokenExpiredException("Token has expired"))
              .when(confirmationService).confirmToken("expired-token");

      mockMvc.perform(get("/api/users/confirm")
                      .param("token", "expired-token"))
              .andExpect(status().isBadRequest())
              .andExpect(jsonPath("$").value("Token has expired"));
   }

   @Test
   public void handleUserNotFoundException() throws Exception {
      Mockito.doThrow(new UserNotFoundException("User not found"))
              .when(deleteUserService).delete(1L);

      mockMvc.perform(delete("/api/users/1"))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$").value("User not found"));
   }

   @Test
   public void handleInvalidCredentialsException() throws Exception {
      String invalidLogin = "{ \"email\": \"invalid@example.com\", \"password\": \"wrongpassword\" }";

      Mockito.doThrow(new InvalidCredentialsException("Invalid email or password"))
              .when(loginService).login(Mockito.any());

      mockMvc.perform(post("/api/users/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidLogin))
              .andExpect(status().isUnauthorized())
              .andExpect(jsonPath("$").value("Invalid email or password"));
   }
}
