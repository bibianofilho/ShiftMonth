package com.mbf.shiftmonth.model;

import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "SEQ_USERTASK", sequenceName = "SEQ_USERTASK", initialValue = 1)
public class UserTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USERTASK")
	int idUserTask;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUser")
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTask")
	private Task task;
	
	
	@Temporal(TemporalType.DATE)
	Calendar dtRecord;
	
	//Hours
	int qtdHour;

	public int getIdUserTask() {
		return idUserTask;
	}

	public void setIdUserTask(int idUserTask) {
		this.idUserTask = idUserTask;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Calendar getDtRecord() {
		return dtRecord;
	}

	public void setDtRecord(Calendar dtRecord) {
		this.dtRecord = dtRecord;
	}

	public int getQtdHour() {
		return qtdHour;
	}

	public void setQtdHour(int qtdHour) {
		this.qtdHour = qtdHour;
	}
	
	

	

}
