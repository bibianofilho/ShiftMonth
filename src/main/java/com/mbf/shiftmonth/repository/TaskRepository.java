package com.mbf.shiftmonth.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;
import com.mbf.shiftmonth.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TaskRepository extends JpaRepository<Task, Long> {

	
}

