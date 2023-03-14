package br.com.dea.management.employee;

import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-mysql")
public class EmployeeCreationSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeCreationWithAValidPayload_thenCreateAEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();

        String payload = "{\n" +
                "  \"name\": \"name\",\n" +
                "  \"email\": \"email@mail.com\",\n" +
                "  \"linkedin\": \"linkedin\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"employeeType\":\"DEVELOPER\",\n" +
                "  \"position\": {\n" +
                "    \"description\": \"BACKEND DEVELOPER\",\n" +
                "    \"seniority\": \"PLENO\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getUser().getName()).isEqualTo("name");
        assertThat(employee.getUser().getEmail()).isEqualTo("email@mail.com");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("linkedin");
        assertThat(employee.getUser().getPassword()).isEqualTo("1234");
        assertThat(employee.getEmployeeType().toString()).isEqualTo("DEVELOPER");
        assertThat(employee.getPosition().getDescription()).isEqualTo("BACKEND DEVELOPER");
        assertThat(employee.getPosition().getSeniority()).isEqualTo("PLENO");
    }
}