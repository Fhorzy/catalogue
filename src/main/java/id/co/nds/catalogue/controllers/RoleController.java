package id.co.nds.catalogue.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.RoleModel;
import id.co.nds.catalogue.services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseModel> postRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.add(roleModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New Role is successfully added");
            response.setData(role);

            return ResponseEntity.ok(response);
        } catch (ClientExceptions e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getAllRoleController() {
        try {
            List<RoleEntity> roles = roleService.findAll();

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(roles);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel> getRoleByIdController(@PathVariable String id) {
        try {
            RoleEntity role = roleService.findById(id);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(role);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    // @GetMapping(value="/get/user")
    // public ResponseEntity<ResponseModel> getUsersByRoleNameController(@RequestBody RoleModel roleModel) {
    //     try {
    //         List<RoleEntity> role = roleService.findUsersByRoleName(roleModel.getName());

    //         ResponseModel response = new ResponseModel();
    //         response.setMsg("Request successfully");
    //         response.setData(role);
            
    //         return ResponseEntity.ok(response);
    //     } catch (ClientExceptions e) {
    //         ResponseModel response = new ResponseModel();
    //         response.setMsg(e.getMessage());

    //         return ResponseEntity.badRequest().body(response);
    //     } catch (NotFoundException e) {
    //         ResponseModel response = new ResponseModel();
    //         response.setMsg(e.getMessage());

    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    //     } catch (Exception e) {
    //         ResponseModel response = new ResponseModel();
    //         response.setMsg("Sorry, there is a failure on our server");
    //         e.printStackTrace();

    //         return ResponseEntity.internalServerError().body(response);
    //     }
    // }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseModel> putRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.edit(roleModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Role is successfully updated");
            response.setData(role);
            return ResponseEntity.ok(response);
        } catch (ClientExceptions e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
    
            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();
    
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseModel> deleteRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.delete(roleModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Role is successfully deleted");
            response.setData(role);
            return ResponseEntity.ok(response);
        } catch (ClientExceptions e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
    
            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();
    
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
