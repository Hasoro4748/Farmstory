package com.farmstory.repository;

import com.farmstory.entity.FileEntity;
import com.farmstory.repository.custom.FileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> , FileRepositoryCustom {

    public Optional<List<FileEntity>> findByAno(int Ano);

}
