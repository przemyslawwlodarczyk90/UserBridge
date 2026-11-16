package com.example.userbridge.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Uses src/test/resources/static/index.html as a stand-in for the React build. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpaWebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rootServesIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void clientSideRouteFallsBackToIndex() throws Exception {
        mockMvc.perform(get("/reset-password").param("token", "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SPA_TEST_INDEX")));
    }

    @Test
    void nestedClientSideRouteFallsBackToIndex() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SPA_TEST_INDEX")));
    }

    @Test
    void missingAssetIsNotFound() throws Exception {
        mockMvc.perform(get("/assets/missing.js"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unknownApiPathIsNotRewrittenToIndex() throws Exception {
        mockMvc.perform(get("/api/does-not-exist"))
                .andExpect(content().string(org.hamcrest.Matchers.not(containsString("SPA_TEST_INDEX"))));
    }
}
