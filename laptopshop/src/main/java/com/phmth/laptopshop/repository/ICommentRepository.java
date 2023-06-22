package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.CommentEntity;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, Long>{

}
