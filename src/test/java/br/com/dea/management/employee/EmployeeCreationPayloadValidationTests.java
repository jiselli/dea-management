package br.com.dea.management.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-mysql")
public class EmployeeCreationPayloadValidationTests {

    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        String payload = "{}";
        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(11)))
                .andExpect(jsonPath("$.details[*].field", hasItem("name")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Name could not be null")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Name could not be empty")))
                .andExpect(jsonPath("$.details[*].field", hasItem("email")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email could not be null")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email could not be empty")))
                .andExpect(jsonPath("$.details[*].field", hasItem("linkedin")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Linkedin could not be null")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Linkedin could not be empty")))
                .andExpect(jsonPath("$.details[*].field", hasItem("employeeType")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("EmployeeType could not be null")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("EmployeeType could not be empty")))
                .andExpect(jsonPath("$.details[*].field", hasItem("position")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Position could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("password")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Password could not be null")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Password could not be empty")));
    }

    @Test
    void whenPayloadHasInvalidEmail_thenReturn400AndTheErrors() throws Exception {

        String payload = "{\n" +
                "  \"name\": \"name\",\n" +
                "  \"email\": \"email\",\n" +
                "  \"linkedin\": \"linkedin\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"employeeType\":\"DEVELOPER\",\n" +
                "  \"position\": {\n" +
                "    \"description\": \"BACKEND DEVELOPER\",\n" +
                "    \"seniority\": \"PLENO\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details[*].field", hasItem("email")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email passed is not valid!")));
    }

    @Test
    void whenPayloadNotSendPosition_thenReturn400AndTheErrors() throws Exception {

        String payload = "{\n" +
                "  \"name\": \"name\",\n" +
                "  \"email\": \"email\",\n" +
                "  \"linkedin\": \"linkedin\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"employeeType\":\"DEVELOPER\"\n" +
                "}";

        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(jsonPath("$.details[*].field", hasItem("position")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Position could not be null")));
    }

    @Test
    void whenPayloadHasInvalidPasswordSize_thenReturn400AndTheErrors() throws Exception {

        String payload = "{\n" +
                "  \"name\": \"name\",\n" +
                "  \"email\": \"email\",\n" +
                "  \"linkedin\": \"linkedin\",\n" +
                "  \"password\": \"123\",\n" +
                "  \"employeeType\":\"DEVELOPER\",\n" +
                "  \"position\": {\n" +
                "    \"description\": \"BACKEND DEVELOPER\",\n" +
                "    \"seniority\": \"PLENO\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(jsonPath("$.details[*].field", hasItem("password")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Password must be between 4 and 8 characters")));
    }
}