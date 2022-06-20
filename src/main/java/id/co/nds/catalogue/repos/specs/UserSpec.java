package id.co.nds.catalogue.repos.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.UserModel;

public class UserSpec implements Specification<UserEntity> {
    private UserModel userModel;

    public UserSpec(UserModel userModel) {
        super();
        this.userModel = userModel;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.and();

        if(userModel.getId() != null && userModel.getId() != 0) {
            p.getExpressions().add(criteriaBuilder.equal(root.get("id"), userModel.getId()));
        }
        
        if(userModel.getCallNumber() != null && userModel.getCallNumber().length() > 0) {
            p.getExpressions().add(criteriaBuilder.like(criteriaBuilder.lower(root.get("callNumber")), "%" 
            + userModel.getCallNumber().toLowerCase() + "%"));
        }

        if(userModel.getRecStatus() != null && 
        (userModel.getRecStatus().trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE) 
        || userModel.getRecStatus().trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_NONACTIVE))) {
            p.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("recStatus")), 
            userModel.getRecStatus().toUpperCase()));
        }

        query.orderBy(criteriaBuilder.asc(root.get("id")));

        return p;
    }
    
}
