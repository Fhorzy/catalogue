package id.co.nds.catalogue.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.SaleEntity;

@Repository
@Transactional
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
    
}
