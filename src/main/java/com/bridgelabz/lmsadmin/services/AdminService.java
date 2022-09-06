package com.bridgelabz.lmsadmin.services;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import com.bridgelabz.lmsadmin.exception.AdminException;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.repository.IAdminRepository;
import com.bridgelabz.lmsadmin.util.Response;
import com.bridgelabz.lmsadmin.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService{
    @Autowired
    IAdminRepository adminRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;

    @Override
    public Response addAdmin(AdminDTO adminDTO) {
        AdminModel adminModel = new AdminModel(adminDTO);
        adminModel.setCreatorStamp(LocalDateTime.now());
        adminRepository.save(adminModel);
        String token = tokenUtil.crateToken(adminModel.getId());
        String body = "Admin added Successfully with id  :" + adminModel.getId();
        String subject = "Admin added Successfully....";
        mailService.send(adminModel.getEmailId(), body, subject);
        return new Response("Admin Added successfully", 200, token);
    }

    @Override
    public Response getAdmin(long id) {
        Optional<AdminModel> adminModel = adminRepository.findById(id);
        return new Response("Admin Found", 200, adminModel);
    }

    @Override
    public Response getAllAdminData(String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(adminId);
        if (isAdminPresent.isPresent()){
            List<AdminModel> getAllAdminData = adminRepository.findAll();
            if (getAllAdminData.size() > 0)
                return new Response("Admin Found", 200, getAllAdminData);
            else
                throw new AdminException(400, "No Data Found");
        }
        throw new AdminException(400, "Admin not found");
    }

    @Override
    public Response updateAdmin(long id, AdminDTO adminDTO, String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(adminId);
        if (isAdminPresent.isPresent()){
            isAdminPresent.get().setFirstName(adminDTO.getFirstName());
            isAdminPresent.get().setLastName(adminDTO.getLastName());
            isAdminPresent.get().setMobile(adminDTO.getMobile());
            isAdminPresent.get().setEmailId(adminDTO.getEmailId());
            isAdminPresent.get().setProfilePath(adminDTO.getProfilePath());
            isAdminPresent.get().setStatus(adminDTO.isStatus());
            isAdminPresent.get().setPassword(adminDTO.getPassword());
            isAdminPresent.get().setCreatorStamp(adminDTO.getCreatorStamp());
            isAdminPresent.get().setUpdatedStamp(adminDTO.getUpdatedStamp());
            String body = "Admin updated Successfully with id  :" + isAdminPresent.get().getId();
            String subject = "Admin updated Successfully....";
            mailService.send(isAdminPresent.get().getEmailId(), body, subject);
            return new Response("Update Admin Details", 200, isAdminPresent.get());
        }
        throw  new AdminException(400, "Admin not Present");
    }

    @Override
    public Response deleteAdmin(Long id, String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(adminId);
        if (isAdminPresent.isPresent()){
            adminRepository.delete(isAdminPresent.get());
            return new Response("Delete Admin...", 200, isAdminPresent);
        }
        throw new AdminException(400, "Admin Not found");
    }

    @Override
    public Response login(String email, String password) {
        Optional<AdminModel> isEmailPresent = adminRepository.findAdminByEmailId(email);
        if (isEmailPresent.isPresent()){
            if (isEmailPresent.get().getPassword().equals(password)){
                String token = tokenUtil.crateToken(isEmailPresent.get().getId());
                return new Response("Login successful", 200, token);
            }
            throw new AdminException(400, "Invalid Credential");
        }
        throw new AdminException(400, "Admin Not Found");
    }

    @Override
    public Response updatePassword(String token, String newPassword) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(adminId);
        if (isIdPresent.isPresent()){
            isIdPresent.get().setPassword(newPassword);
           adminRepository.save(isIdPresent.get());
           return new Response("Update Password", 200, isIdPresent);
        } else {
            throw new AdminException(400, "Invalid Token");
        }
    }

    @Override
    public Response resetPassword(String email) {
        Optional<AdminModel> isEmailPresent = adminRepository.findAdminByEmailId(email);
        if (isEmailPresent.isPresent()){
            String token = tokenUtil.crateToken(isEmailPresent.get().getId());
            String url = "http://localhost:8082/admin/resetPassword" + token;
            String subject = "Reset Password";
            String body = "To Reset Password Click This Link\n" + url + "For Reset Use this Token\n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), url, subject);
            return new Response("Reset Password", 200, isEmailPresent);
        }
        throw new AdminException(400, "Email Not Found");
    }

    @Override
    public Response addProfilePath(Long id, String profilePath, String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isTokenPresent = adminRepository.findById(adminId);
        if (isTokenPresent.isPresent()) {
            Optional<AdminModel> isIdPresent = adminRepository.findById(id);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setProfilePath(profilePath);
                adminRepository.save(isIdPresent.get());
                String body = "Admin Profile Path change successfully with id" + isIdPresent.get().getId();
                String subject = "Admin Profile Path change successfully in LMS";
                mailService.send(isIdPresent.get().getEmailId(), subject, body);
                return new Response("successfully validate", 200, isIdPresent);
            }
            throw new AdminException(400, "Admin not found");
        }
        throw new AdminException(400, "Token not found");
    }

    @Override
    public Boolean validate(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isUserPresent = adminRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            return true;
        }
        return false;
    }
}
