package com.mbf.shiftmonth.web.controller;


import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;
import com.mbf.shiftmonth.model.Task;
import com.mbf.shiftmonth.service.ProjectService;
import com.mbf.shiftmonth.treeTemp.NodeBean;
import com.mbf.shiftmonth.treeTemp.Tree;
import com.mbf.shiftmonth.utility.AjaxResult;

@Controller
@RequestMapping(value = "project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;	
	

	@RequestMapping(value = "/projectList")
	public ModelAndView home() {
		
		ModelAndView mav = new ModelAndView("project/project");		 
	    return mav;
	}
	
	@RequestMapping("/getProjectTree")
	public @ResponseBody AjaxResult getProjectTree(HttpServletRequest request ) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Project projectNew  =  mapper.readValue(request.getParameter("paramJson"), Project.class);
		List<NodeBean> nodes = projectService.getProjectTree(projectNew);
		return  AjaxResult.success(nodes);
	}

	
	@RequestMapping(value = "/addNode", method=RequestMethod.POST)
	public @ResponseBody AjaxResult addNode(
			Model model, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Node nodeNew  =  mapper.readValue(request.getParameter("paramJson"), Node.class);
		projectService.addNode(nodeNew); 
		List<NodeBean> nodes = projectService.getProjectTree(nodeNew.getProject());
		return  AjaxResult.success(nodes);
	}
	
	
	@RequestMapping(value = "/addTask", method=RequestMethod.POST)
	public @ResponseBody AjaxResult addTask(
			Model model, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Task taskNew  =  mapper.readValue(request.getParameter("paramJson"), Task.class);
		taskNew =  projectService.addTask(taskNew);
		List<NodeBean> nodes = projectService.getProjectTree(taskNew.getNode().getProject());
		return  AjaxResult.success(nodes);
	}
	
	
	@RequestMapping(value = "/getProjects")
	public @ResponseBody AjaxResult getProjects(
			Model model, HttpServletResponse response, HttpServletRequest request){
			
		List<Project> projects =  projectService.findByIdProject(-1);
		return  AjaxResult.success(projects);
	}
	
	@RequestMapping(value = "/addProject", method=RequestMethod.POST)
	public @ResponseBody AjaxResult addProject(
			Model model, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Project projectNew  =  mapper.readValue(request.getParameter("paramJson"), Project.class);
		projectNew= projectService.addProject(projectNew);
		return AjaxResult.success(projectNew);
	}
	
	
	@RequestMapping(value = "/getProjectById")
	public @ResponseBody AjaxResult getProjectById(
			Model model, HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
			
		ObjectMapper mapper = new ObjectMapper();
		Project projectNew  =  mapper.readValue(request.getParameter("paramJson"), Project.class);
		List<Project> projects =  projectService.findByIdProject(projectNew.getIdProject());
		return  AjaxResult.success(projects);
	}
	
}
