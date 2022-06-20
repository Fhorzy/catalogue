package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.repos.specs.UserSpec;
import id.co.nds.catalogue.validators.RoleValidator;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class UserService implements Serializable{
    @Autowired
    private UserRepo userRepo;

    UserValidator userValidator = new UserValidator();

    RoleValidator roleValidator = new RoleValidator();

    public UserEntity addUser(UserModel userModel) throws ClientExceptions{
        userValidator.notNullCheckUserId(userModel.getId());
        userValidator.nullCheckFullname(userModel.getFullname());
        userValidator.validateFullname(userModel.getFullname());
        userValidator.nullCheckRoleId(userModel.getRoleId());
        userValidator.validateCheckRolelId(userModel.getRoleId());
        userValidator.nullCheckCallNumber(userModel.getCallNumber());
        userValidator.validateCallNumber(userModel.getCallNumber());

        long countCallNumber = userRepo.countByCallNumber(userModel.getCallNumber());
        if(countCallNumber > 0) {
            throw new ClientExceptions("Call Number is already existsed");
        }

        UserEntity user = new UserEntity();
        user.setFullname(userModel.getFullname());
        user.setRoleId(userModel.getRoleId());
        user.setCallNumber(userModel.getCallNumber());
        user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setCreatorId(userModel.getActorId() == null ? 0 : userModel.getActorId());
        
        return userRepo.save(user);
    }

    public List<UserEntity> getAllUser() {
        List<UserEntity> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);

        return users;
    }

    public List<UserEntity> getAllUserByCriteria(UserModel userModel) {
        List<UserEntity> users = new ArrayList<>();
        UserSpec specs = new UserSpec(userModel);
        userRepo.findAll(specs).forEach(users::add);

        return users;
    }

    public UserEntity getUserById(Integer id) throws ClientExceptions, NotFoundException {
        userValidator.nullCheckUserId(id);
        userValidator.validateUserId(id);


        UserEntity user = userRepo.findById(id).orElse(null);
        userValidator.nullCheckObject(user);

        return user;
    }

    public List<UserEntity> findUsersByRoleId(String id) throws ClientExceptions, NotFoundException {
        roleValidator.nullCheckRoleId(id);
        roleValidator.validateRoleId(id);

        List<UserEntity> user = userRepo.findUsersByRoleId(id);
        roleValidator.nullCheckObject(user);

        return user;
    }

    public List<UserEntity> findUsersByRoleName(String name) throws ClientExceptions, NotFoundException {
        roleValidator.nullCheckRoleName(name);
        roleValidator.validateRoleName(name);

        List<UserEntity> user = userRepo.findUsersByRoleName(name);
        roleValidator.nullCheckObject(user);

        return user;
    }

    public String getUserRoleById(Integer id) throws ClientExceptions, NotFoundException {
        userValidator.nullCheckUserId(id);
        
        return userRepo.getUserRoleById(id);
    }

    public UserEntity editUser(UserModel userModel) throws ClientExceptions, NotFoundException{
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("Cannot find user with id: " + userModel.getId());
        }

        UserEntity user = new UserEntity();
        user = getUserById(userModel.getId());

        if(userModel.getCallNumber() != null) {
            userValidator.validateCallNumber(userModel.getCallNumber());
            Long countCallNumber = userRepo.countByCallNumber(userModel.getCallNumber());

            if(countCallNumber > 0) {
                throw new ClientExceptions("Call Number is already existed");
            }

            user.setCallNumber(userModel.getCallNumber());
        }

        if(userModel.getFullname() != null) {
            userValidator.validateFullname(userModel.getFullname());
            user.setFullname(userModel.getFullname());
        }

        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdaterId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        return userRepo.save(user);
    }

    public UserEntity deleteUser(UserModel userModel) throws ClientExceptions, NotFoundException{
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("Cannot find user with id: " + userModel.getId());
        }

        UserEntity user = new UserEntity();
        user = getUserById(userModel.getId());

        if(user.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NONACTIVE)) {
            throw new ClientExceptions("User id (" + userModel.getId() + ") is already been deleted.");
        }

        user.setRecStatus(GlobalConstant.REC_STATUS_NONACTIVE);
        user.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        user.setDeleterId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        return userRepo.save(user);
    }
}
