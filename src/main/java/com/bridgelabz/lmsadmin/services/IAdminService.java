package com.bridgelabz.lmsadmin.services;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminService {

    Response addAdmin(AdminDTO adminDTO);

    Response getAdmin(long id);

    Response getAllAdminData(String token);

    Response updateAdmin(long id, AdminDTO adminDTO, String token);

    Response deleteAdmin(Long id, String token);

    Response login(String email, String password);

    Response updatePassword(String token, String newPassword);

    Response resetPassword(String emailId);
    
    Response addProfilePath(Long id, String profilePath, String token);

    Boolean validate(String token);
}
