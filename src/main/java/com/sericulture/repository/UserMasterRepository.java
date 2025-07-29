package com.sericulture.repository;

import com.sericulture.model.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

    Optional<UserMaster> findByUserMasterIdAndActive(Long id, boolean active);

//    boolean existsUserMasterByUserMasterIdAndActive(Long id, Integer active);
}
