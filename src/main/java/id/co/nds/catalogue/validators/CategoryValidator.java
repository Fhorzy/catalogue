package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class CategoryValidator {
    public void nullCheckCategoryId(String id) throws ClientExceptions {
        if(id == null) {
            throw new ClientExceptions("Category id is required");
        }
    }

    public void notNullCheckCategoryId(String id) throws ClientExceptions {
        if(id != null) {
            throw new ClientExceptions("Category id is auto generated, do not input id");
        }
    }

    public void nullCheckName(String name) throws ClientExceptions {
        if(name == null) {
            throw new ClientExceptions("Category name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException {
        if(o == null) {
            throw new NotFoundException("Category is not found");
        }
    }

    public void validateCategoryId(String id) throws ClientExceptions {
        if(id.length() != 6 || !id.startsWith("PC")) {
            throw new ClientExceptions("Category id must contains six digits and starts with 'PC'");
        }
    }

    public void validateName(String name) throws ClientExceptions {
        if(name.trim().equalsIgnoreCase("")) {
            throw new ClientExceptions("Category name is required");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientExceptions{
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NONACTIVE)) {
            throw new ClientExceptions("Category with id " + id + " is already been deleted.");
        }
    }
}
