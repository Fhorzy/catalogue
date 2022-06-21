package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
public interface MaintainProductRepo extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {
    @Query(value = "SELECT * FROM ms_product WHERE rec_status = '" + 
    GlobalConstant.REC_STATUS_ACTIVE + "' AND quantity < 5", nativeQuery = true)
    List<ProductEntity> findProductsLessThan5Quantity();
}
