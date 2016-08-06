package com.mbf.shiftmonth.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "SEQ_PROJECT", sequenceName = "SEQ_PROJECT", initialValue = 1)
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PROJECT")
	int idProject;
	
	String nmProject;
	String dsProject;
	
	@OneToMany(mappedBy="project", fetch=FetchType.EAGER)
	private List<Node> nodes;
	
	public Project( int idProject, String nmProject, String dsProject ){
		this.idProject =idProject;
		this.nmProject = nmProject;
		this.dsProject=dsProject;
	}
	
	public Project(){
		
	}
	
	
	
	public int getIdProject() {
		return idProject;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	public String getNmProject() {
		return nmProject;
	}
	public void setNmProject(String nmProject) {
		this.nmProject = nmProject;
	}
	public String getDsProject() {
		return dsProject;
	}
	public void setDsProject(String dsProject) {
		this.dsProject = dsProject;
	}
	public List<Node> getNode() {
		return nodes;
	}
	public void setNode(List<Node> node) {
		this.nodes = node;
	}
	
	
	
	
	

}
