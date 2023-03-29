package br.com.dea.management.academyclass.creation;

import br.com.dea.management.academyclass.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
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
@ActiveProfiles("test")
class AcademyClassCreationSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    @Autowired
    private AcademyTestUtils academyTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingAcademyClassCreationWithAValidPayload_thenCreateAAcademyClassSuccessfully() throws Exception {
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(1);
        Employee employee = this.employeeRepository.findAll().get(0);

        String payload = "{\n" +
                "  \"startDate\": \"2023-03-28\",\n" +
                "  \"endDate\": \"2023-03-29\",\n" +
                "  \"classType\": \"DEVELOPER\",\n" +
                "  \"instructorId\": \n" + employee.getId() +
                "}";

        mockMvc.perform(post("/academy-class")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);

        assertThat(academyClass.getInstructor().getId()).isEqualTo(employee.getId());
        assertThat(academyClass.getStartDate()).isEqualTo("2023-03-28");
        assertThat(academyClass.getEndDate()).isEqualTo("2023-03-29");
        assertThat(academyClass.getClassType()).isEqualTo(ClassType.DEVELOPER);
    }

}
