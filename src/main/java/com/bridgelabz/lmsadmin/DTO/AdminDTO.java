package com.bridgelabz.lmsadmin.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class AdminDTO {
    private Long adminId;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = "Invalid First Name...!")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = "Invalid  Last Name...!")
    private String lastName;
    @Pattern(regexp = "^[6789][0-9]{9}$")
    private String mobile;
    @NotNull(message = "email should not be null")
    private String emailId;
    private String profilePath;
    private boolean status;
    private String password;

    private LocalDateTime creatorStamp;
    private LocalDateTime updatedStamp;
}
