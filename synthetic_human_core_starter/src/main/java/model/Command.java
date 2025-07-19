package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private CommandPriority priority;

    @NotBlank
    @Size(max = 100)
    private String author;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}