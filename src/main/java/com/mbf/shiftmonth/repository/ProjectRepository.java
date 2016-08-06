package com.mbf.shiftmonth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	@Query("select new com.great.greatmonth.greatmonth.model.Project(p.idProject, p.nmProject, p.dsProject) from Project p where (p.idProject=:idProject  or -1 = :idProject)")	
	List<Project> findByIdProject(@Param("idProject" ) int idProject);
	
	
}
