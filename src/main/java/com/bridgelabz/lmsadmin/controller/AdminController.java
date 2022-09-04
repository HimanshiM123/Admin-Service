package com.bridgelabz.lmsadmin.controller;

import com.bridgelabz.lmsadmin.DTO.AdminDTO;
import com.bridgelabz.lmsadmin.services.IAdminService;
import com.bridgelabz.lmsadmin.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

    /*
      Purpose : AdminController to process Data API's
      @author : Himanshi Mohabe
     */

@RestController
@RequestMapping("/admin")
public class AdminController {

    /*
     Introducing DTO and Model and Service Layer to LMS App and creating  Rest Controller
      to demonstrate the various HTTP Methods
     */

    @Autowired
    IAdminService adminService;

    /*
     *@Purpose:to add admin details into the Admin Repository
     * @Param : AdminDTO
     */

    @PostMapping(value = "/addAdmin")
    ResponseEntity<Response> addAdminData(@Valid @RequestBody AdminDTO adminDTO) {
        Response response = adminService.addAdmin(adminDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     *@Purpose : to get list of admin details in the Admin Repository using id
      @Param  : id
     */

    @GetMapping("/getAdmin/{id}")
    ResponseEntity<Response> getAdmin(@PathVariable long id){
        Response response = adminService.getAdmin(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     *@Purpose : Ability to get list of admin details in the Admin Repository
      @Param : token
     */

    @GetMapping("/getAdmin")
    ResponseEntity<Response> getAllAdmin(@RequestHeader String token){
        Response response = adminService.getAllAdminData(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Able to update admin details into the Admin Repository
     @Param : AdminDTO, id and token
     */
    @PutMapping("updateAdmin/{id}")
    ResponseEntity<Response> updateAdmin(@Valid @RequestBody AdminDTO adminDTO, @PathVariable long id, @RequestHeader String token ){
        Response response = adminService.updateAdmin(id, adminDTO, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
      @Purpose : Able to delete  admin details by id in the Admin Repository
      @Param : token and id
     */
    @DeleteMapping("deleteAdmin/{id}")
    ResponseEntity<Response> deleteAdmin(@PathVariable Long id, @RequestHeader String token){

        Response response = adminService.deleteAdmin(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*
     @Purpose : Able to Access existing admin details by using login in the Admin Repository
     @Param : email and password
     */

    @PostMapping("/login")
    ResponseEntity<Response> login(@RequestParam String email, @RequestParam String password){
        Response response = adminService.login(email, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Ability to Update Password of admin in the Admin Repository
     @Param : token and newPassword
     */

    @PutMapping("/updatePassword")
    ResponseEntity<Response> updatePassword(@RequestHeader String token, @RequestParam String newPassword){
        Response response = adminService.updatePassword(token, newPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Able to Reset Password of admin in the Admin Repository
     @Param : EmailId
     */

    @PutMapping("/resetPassword")
    ResponseEntity<Response> resetPassword(@RequestParam String emailId){
        Response response = adminService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    @Purpose : Ability to Add Profile of admin user in the Admin Repository
    @Param : id and token
    */
    @PostMapping("/addprofilepath")
    ResponseEntity<Response> addProfilePath(@RequestParam Long id, @RequestParam String profilePath, @RequestHeader String token) {
        Response response = adminService.addProfilePath(id, profilePath, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Able to validate admin in the Admin Repository
     @Param : token
     */

    @GetMapping("/validateUser/{token}")
    ResponseEntity<Response> validateUser(@PathVariable String token){
        Response response = adminService.validateUser(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
