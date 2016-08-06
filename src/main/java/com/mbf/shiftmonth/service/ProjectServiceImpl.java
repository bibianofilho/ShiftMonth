package com.mbf.shiftmonth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbf.shiftmonth.model.Node;
import com.mbf.shiftmonth.model.Project;
import com.mbf.shiftmonth.model.Task;
import com.mbf.shiftmonth.repository.NodeRepository;
import com.mbf.shiftmonth.repository.ProjectRepository;
import com.mbf.shiftmonth.repository.TaskRepository;
import com.mbf.shiftmonth.treeTemp.NodeBean;
import com.mbf.shiftmonth.treeTemp.NodeState;
import com.mbf.shiftmonth.treeTemp.Tree;

@Component
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private NodeRepository nodeRepository;	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Project> findAll() {
		return this.projectRepository.findAll();
	}
	@Override
	public List<Node> getNodeForName(String nodeName){
		return this.nodeRepository.getNodeForName(nodeName);
	};
	
	@Override
	public List<Node> findNodeAll() {
		return this.nodeRepository.findAll();
	}
	
	
	@Override
	public Node addNode(Node node){
		 return this.nodeRepository.saveAndFlush(node);
	}
	
	@Override
	public List<Project> findByIdProject(int idProject){
		 return this.projectRepository.findByIdProject(idProject);
	}
	
	@Override
	public List<Node> findNodeByIdProject(int idProject){
		 return this.nodeRepository.findNodeByIdProject(idProject);
	}
	
	@Override
	public Task addTask(Task task){
		 return this.taskRepository.saveAndFlush(task);
	}
	
	@Override
	public Project addProject(Project project){
		 return this.projectRepository.saveAndFlush(project);
	}
	
	
	@Override
	public List<NodeBean> getProjectTree(Project project){
		List<Node> nodesdb = findNodeByIdProject(project.getIdProject());
		NodeBean nodeBean;
		NodeBean nodeTask = null;
		Tree tree = new Tree();		
		for (Node node : nodesdb) {
			
			nodeBean = new NodeBean(node.getIdNode());
			nodeBean.setText(node.getNmNode());
			
			nodeBean.setTpNode(NodeBean.TpNode.NODE);	
			
			NodeState nodeState = new NodeState();
			if(node.getChildren()!= null && node.getChildren().size()==0){				
				nodeState.setExpanded(false);
			}else{
				nodeState.setExpanded(true);
			}
        	nodeBean.setState(nodeState);
			
			if(node.getParent()==null){
				tree.addNode(node.getIdNode(), nodeBean);
			}else{
				tree.addNode(node.getIdNode(), nodeBean, node.getParent().getIdNode());
			}
			
			List<Task> tasks = node.getTasks();
			if(tasks!=null){
				for (Task task : tasks) {				
					if(node.getTasks().size()>0){
						nodeTask = new NodeBean(task.getIdTask());
						nodeTask.setText(task.getNmTask());
						nodeTask.setTpNode(NodeBean.TpNode.TASK);
						nodeTask.setIcon("glyphicon glyphicon-list");
						HashMap<String,String> dataValues = new HashMap<String,String>();
						dataValues.put("dsTask", task.getDsTask());
						nodeTask.setDataValue(dataValues);
						tree.addNode(nodeTask.getIdentifier(), nodeTask, node.getIdNode());
						
					}
				}
			}
			
		}
		
		return new ArrayList<NodeBean>(tree.getNodes().values());
	}
	
	
	
}
