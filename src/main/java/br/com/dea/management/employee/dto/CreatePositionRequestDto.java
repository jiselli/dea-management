package br.com.dea.management.employee.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePositionRequestDto {

    @NotNull(message = "Description could not be null")
    @NotEmpty(message = "Description could not be empty")
    private String description;

    @NotNull(message = "Seniority could not be null")
    @NotEmpty(message = "Seniority could not be empty")
    private String seniority;
}