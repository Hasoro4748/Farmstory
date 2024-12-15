package com.farmstory.repository;

import com.farmstory.entity.pDescImgFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pDescImgFileRepository extends JpaRepository<pDescImgFile, Long> {

}
