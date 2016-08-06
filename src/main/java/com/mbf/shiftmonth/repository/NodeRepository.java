package com.mbf.shiftmonth.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NodeRepository extends JpaRepository<Node, Long> {

	@Query("select n from Node n where n.nmNode=:nmNode")
	List<Node> getNodeForName(@Param("nmNode") String nodeName);
	
	Node findByIdNode(int id);
	
	@Query("select n from Node n where n.project.idProject=:idProject")
	List<Node> findNodeByIdProject(@Param("idProject") int idProject);
}

