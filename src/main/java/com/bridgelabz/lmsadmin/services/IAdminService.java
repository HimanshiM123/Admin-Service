package com.bridgelabz.lmsadmin.services;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.util.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminService {
    AdminModel addAdmin(AdminDTO adminDTO);

    AdminModel getAdminById(long id);

    List<AdminModel> getAllAdminData(String token);

    AdminModel updateAdmin(long id, AdminDTO adminDTO, String token);

    AdminModel deleteAdmin(Long id, String token);

    Response login(String email, String password);

    AdminModel updatePassword(String token, String newPassword);

    AdminModel resetPassword(String emailId);
}
