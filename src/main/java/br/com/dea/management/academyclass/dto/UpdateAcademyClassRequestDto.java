package br.com.dea.management.academyclass.dto;

import br.com.dea.management.academyclass.ClassType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateAcademyClassRequestDto {

    @NotNull(message = "StartDate could not be null")
    private LocalDate startDate;

    @NotNull(message = "EndDate could not be null")
    private LocalDate endDate;

    @NotNull(message = "ClassType could not be null")
    private ClassType classType;

    @NotNull(message = "InstructorId could not be null")
    private Long instructorId;

}