package com.mbf.shiftmonth.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;
import com.mbf.shiftmonth.model.Task;
import com.mbf.shiftmonth.model.User;
import com.mbf.shiftmonth.treeTemp.NodeBean;

public interface ProjectService {

	List<Project> findAll();
	
	List<Node> getNodeForName(String nodeName);
	
	List<Node> findNodeAll();
	
	Node addNode(Node node);
	
	List<Project> findByIdProject(int idProject);
	
	List<Node> findNodeByIdProject(int idProject);
	
	Task addTask(Task task);
	
	Project addProject(Project project);
	
	List<NodeBean> getProjectTree(Project project);
	
}
