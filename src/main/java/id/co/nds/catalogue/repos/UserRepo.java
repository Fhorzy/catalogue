package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

public interface UserRepo extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity>{
    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '" + 
    GlobalConstant.REC_STATUS_ACTIVE + "' AND call_number = :call_number", nativeQuery = true)
    long countByCallNumber(@Param("call_number") String callNumber);

    @Query(value = "SELECT u.* FROM ms_user AS u " 
    + "JOIN ms_role AS r ON r.id = u.role_id " + "WHERE LOWER(u.role_id) = LOWER(:id)", nativeQuery = true)
    List<UserEntity> findUsersByRoleId(@Param("id") String id);

    @Query(value = "SELECT u.* FROM ms_user AS u " 
    + "JOIN ms_role AS r ON r.id = u.role_id " + "WHERE LOWER(r.name) = LOWER(:name)", nativeQuery = true)
    List<UserEntity> findUsersByRoleName(@Param("name") String name);

    @Query(value = "SELECT u.role_id FROM ms_user AS u WHERE u.id = :id", nativeQuery = true)
    String getUserRoleById(@Param("id") Integer id);
}
