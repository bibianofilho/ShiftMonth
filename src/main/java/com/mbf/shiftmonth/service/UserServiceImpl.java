package com.mbf.shiftmonth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.mbf.shiftmonth.model.User;
import com.mbf.shiftmonth.repository.UserRepository;



@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository usertRepository ;
	

	@Override
	public List<User> findAll() {
		return this.usertRepository.findAll();
	}

}
