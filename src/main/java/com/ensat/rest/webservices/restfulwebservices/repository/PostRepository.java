package com.ensat.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Repository;

import com.ensat.rest.webservices.restfulwebservices.entity.Post;
import com.ensat.rest.webservices.restfulwebservices.entity.User;
import com.fasterxml.jackson.annotation.JsonAlias;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	

}
