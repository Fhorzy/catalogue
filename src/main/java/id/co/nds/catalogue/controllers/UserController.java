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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
      
    @Autowired
    UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseModel> addUserController(@RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.addUser(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New User is successfully added");
            response.setData(user);

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

    @GetMapping(value = "/get")
    public ResponseEntity<ResponseModel> getAllUsersController() {
        try {
          List<UserEntity> users = userService.getAllUser();
          
          ResponseModel response = new ResponseModel();
          response.setMsg("Request successfully");
          response.setData(users);

          return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/search")
    public ResponseEntity<ResponseModel> getAllUserByCriteriaController(@RequestBody UserModel userModel) {
        try {
            List<UserEntity> users = userService.getAllUserByCriteria(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ResponseModel> getUserByIdController(@PathVariable Integer id) {
        try {
            UserEntity user = userService.getUserById(id);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(user);

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

    @GetMapping(value="/roleid/get")
    public ResponseEntity<ResponseModel> getUsersByRoleIdController(@RequestBody UserModel userModel) {
        try {
            List<UserEntity> users = userService.findUsersByRoleId(userModel.getRoleId());

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            
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

    @GetMapping(value="/rolename/get")
    public ResponseEntity<ResponseModel> getUsersByRoleNameController(@RequestParam String name) {
        try {
            List<UserEntity> users = userService.findUsersByRoleName(name);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            
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

    @PutMapping(value="update")
    public ResponseEntity<ResponseModel> editUserController(@RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.editUser(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully updated");
            response.setData(user);

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
    public ResponseEntity<ResponseModel> deleteUserController(@RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.deleteUser(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully deleted");
            response.setData(user);

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
