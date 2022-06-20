package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class RoleValidator {
    public void nullCheckRoleId(String id) throws ClientExceptions {
        if(id == null) {
            throw new ClientExceptions("Role id is required");
        }
    }

    public void notNullRoleId(String id) throws ClientExceptions {
        if(id != null) {
            throw new ClientExceptions("Role id is auto generated, do not input id");
        }
    }

    public void nullCheckRoleName(String name) throws ClientExceptions {
        if(name == null) {
            throw new ClientExceptions("Role name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException {
        if(o == null) {
            throw new NotFoundException("Category is not found");
        }
    }

    public void validateRoleId(String id) throws ClientExceptions{
        if(id.length() != 5 || !id.startsWith("R")) {
            throw new ClientExceptions("Role id must contains five digits and starts with 'R'");
        }
    }

    public void validateRoleName(String name) throws ClientExceptions {
        if(name.trim().equalsIgnoreCase("")) {
            throw new ClientExceptions("Role name is required");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientExceptions{
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NONACTIVE)) {
            throw new ClientExceptions("Role with id " + id + " is already been deleted.");
        }
    }
    
}
