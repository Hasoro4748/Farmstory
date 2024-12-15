package com.farmstory.repository;

import com.farmstory.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    public Optional<List<Comment>> findCommentsByParent(int boardId);
    public Optional<List<Comment>> findCommentsByCsParent(String csParentNo);

    public void deleteCommentByCsParent(String csParentNo);
}
