package br.com.dea.management.employee.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequestDto {

    @NotNull(message = "Name could not be null")
    @NotEmpty(message = "Name could not be empty")
    private String name;

    @NotNull(message = "Email could not be null")
    @NotEmpty(message = "Email could not be empty")
    @Email(message = "Email passed is not valid!")
    private String email;

    @NotNull(message = "Linkedin could not be null")
    @NotEmpty(message = "Linkedin could not be empty")
    private String linkedin;

    @NotNull(message = "Password could not be null")
    @NotEmpty(message = "Password could not be empty")
    @Size(min = 4, max = 8, message = "Password must be between 4 and 8 characters")
    private String password;

    @NotNull(message = "EmployeeType could not be null")
    @NotEmpty(message = "EmployeeType could not be empty")
    private String employeeType;

    @NotNull(message = "Position could not be null")
    private CreatePositionRequestDto position;
}