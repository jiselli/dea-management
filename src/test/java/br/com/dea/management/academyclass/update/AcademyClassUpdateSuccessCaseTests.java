package br.com.dea.management.academyclass.update;

import br.com.dea.management.academyclass.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeType;
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
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AcademyClassUpdateSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private AcademyTestUtils academyTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingAcademyClassUpdateWithAValidPayload_thenUpdateAAcademyClassSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();
        this.academyClassRepository.deleteAll();
        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);
        this.academyTestUtils.createFakeClass(1, startDate, endDate, ClassType.DEVELOPER);

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);

        String payload = "{ " +
                "  \"startDate\": \"2023-01-01\",\n" +
                "  \"endDate\": \"2024-12-20\",\n" +
                "  \"classType\": \"DESIGN\",\n" +
                "  \"instructorId\": 1" +
                "}";
        mockMvc.perform(put("/academy-class/" + academyClass.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(academyClass.getInstructor().getId()).isEqualTo(employee.getId());
        assertThat(academyClass.getStartDate()).isEqualTo(startDate);
        assertThat(academyClass.getEndDate()).isEqualTo(endDate);
        assertThat(academyClass.getClassType()).isEqualTo(ClassType.DEVELOPER);
    }

}