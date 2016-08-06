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

@Entity
@SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", initialValue = 1)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USER")
	int idUser;
	
	
	String idNetwork ;
	
	//Hours
	int qtdWorkLoad;
	
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<UserTask> userTasks;

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getIdNetwork() {
		return idNetwork;
	}

	public void setIdNetwork(String idNetwork) {
		this.idNetwork = idNetwork;
	}

	public int getQtdWorkLoad() {
		return qtdWorkLoad;
	}

	public void setQtdWorkLoad(int qtdWorkLoad) {
		this.qtdWorkLoad = qtdWorkLoad;
	}
	
	
	

}
