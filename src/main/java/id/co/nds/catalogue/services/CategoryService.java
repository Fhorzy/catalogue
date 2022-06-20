package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.CategoryModel;
import id.co.nds.catalogue.repos.CategoryRepo;
import id.co.nds.catalogue.validators.CategoryValidator;

@Service
public class CategoryService implements Serializable {
    @Autowired
    private CategoryRepo categoryRepo;

    CategoryValidator categoryValidator = new CategoryValidator();

    public CategoryEntity add(CategoryModel categoryModel) throws ClientExceptions {
        categoryValidator.notNullCheckCategoryId(categoryModel.getId());

        categoryValidator.nullCheckName(categoryModel.getName());
        categoryValidator.validateName(categoryModel.getName());

        Long count = categoryRepo.countByName(categoryModel.getName());
        if(count > 0) {
            throw new ClientExceptions("Category name is already existsed");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryModel.getName());
        category.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        category.setCreatorId(categoryModel.getActorId() == null ? 0 : categoryModel.getActorId());

        return categoryRepo.save(category);
    }

    public List<CategoryEntity> findAll() {
        List<CategoryEntity> categories = new ArrayList<>();
        categoryRepo.findAll().forEach(categories::add);;

        return categories;
    }

    public CategoryEntity findById(String id) throws ClientExceptions, NotFoundException {
        categoryValidator.nullCheckCategoryId(id);
        categoryValidator.validateCategoryId(id);

        CategoryEntity category = categoryRepo.findById(id).orElse(null);
        categoryValidator.nullCheckObject(category);

        return category;
    }

    public CategoryEntity edit(CategoryModel categoryModel) throws ClientExceptions, NotFoundException {
        categoryValidator.notNullCheckCategoryId(categoryModel.getId());
        categoryValidator.validateCategoryId(categoryModel.getId());

        // categoryValidator.validateRecStatus(categoryModel.getId(), categoryModel.getRecStatus());

        if(!categoryRepo.existsById(categoryModel.getId())) {
            throw new NotFoundException("Cannot find category with id: " + categoryModel.getId());
        }

        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if(categoryModel.getName() != null) {
            categoryValidator.validateName(categoryModel.getName());

            Long count = categoryRepo.countByName(categoryModel.getName());

            if(count > 0) {
                throw new ClientExceptions("Category name is already existed");
            }

            category.setName(categoryModel.getName());
        }

        category.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        category.setUpdaterId(categoryModel.getActorId() == null ? 0 : categoryModel.getActorId());

        return categoryRepo.save(category);
    }

    public CategoryEntity delete(CategoryModel categoryModel) throws ClientExceptions, NotFoundException {
        categoryValidator.notNullCheckCategoryId(categoryModel.getId());
        categoryValidator.validateCategoryId(categoryModel.getId());

        if(!categoryRepo.existsById(categoryModel.getId())) {
            throw new NotFoundException("Cannot find category with id: " + categoryModel.getId());
        }

        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if(category.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NONACTIVE)) {
            throw new ClientExceptions("Category id (" + categoryModel.getId() + ") is already been deleted.");
        }

        category.setRecStatus(GlobalConstant.REC_STATUS_NONACTIVE);
        category.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        category.setDeleterId(categoryModel.getActorId() == null ? 0 : categoryModel.getActorId());

        return categoryRepo.save(category);
    }
}
