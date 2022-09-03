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
    public AdminModel addAdmin(AdminDTO adminDTO) {
        AdminModel adminModel = new AdminModel(adminDTO);
        adminModel.setCreatorStamp(LocalDateTime.now());
        adminRepository.save(adminModel);
        String body = "Admin added Successfully with id  :" + adminModel.getId();
        String subject = "Candidate added Successfully....";
        mailService.send(adminModel.getEmailId(), body, subject);
        return adminModel;
    }

    @Override
    public AdminModel getAdminById(long id) {
        Optional<AdminModel> adminModel = adminRepository.findById(id);
        return adminModel.get();
    }

    @Override
    public List<AdminModel> getAllAdminData(String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(adminId);
        if (isAdminPresent.isPresent()) {
            List<AdminModel> getListOfAdmin = adminRepository.findAll();
            if (getListOfAdmin.size() > 0)
                return getListOfAdmin;
        }
        throw  new AdminException(400, "Admin not Present");
    }

    @Override
    public AdminModel updateAdmin(long id, AdminDTO adminDTO, String token) {
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
            return isAdminPresent.get();
        }
        throw  new AdminException(400, "Admin not Present");
    }

    @Override
    public AdminModel deleteAdmin(Long id, String token) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(adminId);
        if (isAdminPresent.isPresent()){
            adminRepository.delete(isAdminPresent.get());
            return isAdminPresent.get();
        }
        throw new AdminException(400, "Admin Not found");
    }

    @Override
    public Response login(String email, String password) {
        Optional<AdminModel> isEmailPresent = adminRepository.findAdminByEmailId(email);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.crateToken(isEmailPresent.get().getId());
                return new Response("Login successful", 200, token);
            }
            throw new AdminException(400, "Invalid Credential");
        }
        throw new AdminException(400, "Admin Not Found");
    }

    @Override
    public AdminModel updatePassword(String token, String newPassword) {
        Long adminId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(adminId);
        if (isIdPresent.isPresent()){
            isIdPresent.get().setPassword(newPassword);
            return adminRepository.save(isIdPresent.get());
        } else {
            throw new AdminException(400, "Invalid Token");
        }
    }

    @Override
    public AdminModel resetPassword(String email) {
        Optional<AdminModel> isEmailPresent = adminRepository.findAdminByEmailId(email);
        if (isEmailPresent.isPresent()){
            String token = tokenUtil.crateToken(isEmailPresent.get().getId());
            String url = "http://localhost:8082/admin/resetPassword" + token;
            String subject = "Reset Password";
            String body = "To Reset Password Click This Link\n" + url + "For Reset Use this Token\n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), url, subject);
            return isEmailPresent.get();
        }
        throw new AdminException(400, "Email Not Found");
    }
}
