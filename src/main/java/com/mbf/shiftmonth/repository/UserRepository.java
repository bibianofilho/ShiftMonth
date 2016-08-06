package com.mbf.shiftmonth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbf.shiftmonth.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
