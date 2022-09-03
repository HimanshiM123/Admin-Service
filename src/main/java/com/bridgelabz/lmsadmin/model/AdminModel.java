package com.bridgelabz.lmsadmin.model;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Data
public class AdminModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String emailId;
    private String profilePath;
    private boolean status;
    private String password;
    private LocalDateTime creatorStamp;
    private LocalDateTime updatedStamp;

    public AdminModel(AdminDTO adminDTO) {
        this.firstName = adminDTO.getFirstName();
        this.lastName = adminDTO.getLastName();
        this.mobile = adminDTO.getMobile();
        this.emailId = adminDTO.getEmailId();
        this.profilePath = adminDTO.getProfilePath();
        this.status = adminDTO.isStatus();
        this.password = adminDTO.getPassword();
        this.creatorStamp = adminDTO.getCreatorStamp();
        this.updatedStamp = adminDTO.getUpdatedStamp();
    }

    public AdminModel() {

    }
}
