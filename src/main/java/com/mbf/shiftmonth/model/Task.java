package com.mbf.shiftmonth.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;





@Entity
@SequenceGenerator(name = "SEQ_TASK", sequenceName = "SEQ_TASK", initialValue = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TASK")
	int idTask;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idNode")
	private Node node ;
	
	String nmTask;
	String dsTask;

	@OneToMany(mappedBy="task", fetch=FetchType.LAZY)
	private List<UserTask> userTasks;
	
	
	public int getIdTask() {
		return idTask;
	}
	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
	public List<UserTask> getUserTasks() {
		return userTasks;
	}
	public void setUserTasks(List<UserTask> userTasks) {
		this.userTasks = userTasks;
	}
	public String getNmTask() {
		return nmTask;
	}
	public void setNmTask(String nmTask) {
		this.nmTask = nmTask;
	}
	public String getDsTask() {
		return dsTask;
	}
	public void setDsTask(String dsTask) {
		this.dsTask = dsTask;
	}
	

	
	
	

}
