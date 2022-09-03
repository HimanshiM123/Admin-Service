package com.bridgelabz.lmsadmin.controller;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.services.IAdminService;
import com.bridgelabz.lmsadmin.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    @PostMapping(value = "/addAdmin")
    public AdminModel addAdminData(@Valid @RequestBody AdminDTO adminDTO) {

        return adminService.addAdmin(adminDTO);
    }

    @GetMapping("/getAdmin/{id}")
    public AdminModel getAdmin(@PathVariable long id){

        return adminService.getAdminById(id);
    }

    @GetMapping("/getAdmin")
    public List<AdminModel> getAllAdmin(@RequestHeader String token){

        return adminService.getAllAdminData(token);
    }
    @PutMapping("updateAdmin/{id}")
    public AdminModel updateAdmin(@Valid @RequestBody AdminDTO adminDTO, @PathVariable long id, @RequestHeader String token ){
        return adminService.updateAdmin(id, adminDTO, token);
    }
    @DeleteMapping("deleteAdmin/{id}")
    public AdminModel deleteAdmin(@PathVariable Long id, @RequestHeader String token){

        return adminService.deleteAdmin(id, token);

    }

    @PostMapping("/login")
    public Response login(@RequestParam String email, @RequestParam String password){
        return adminService.login(email, password);
    }

    @PutMapping("/updatePassword")
    public AdminModel updatePassword(@RequestHeader String token, @RequestParam String newPassword){
        return adminService.updatePassword(token, newPassword);
    }

    @PutMapping("/resetPassword")
    public AdminModel resetPassword(@RequestParam String emailId){
        return adminService.resetPassword(emailId);
    }

}
