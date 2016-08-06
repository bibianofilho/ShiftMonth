package com.mbf.shiftmonth.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@SequenceGenerator(name = "SEQ_NODE", sequenceName = "SEQ_NODE", initialValue = 1)
public class Node {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_NODE")
	int idNode;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idProject")
	private Project project ;
	
	//int idNodeParent;
	
	String nmNode;
	
	@OneToMany(mappedBy="node", fetch=FetchType.LAZY)
	private List<Task> tasks;
	
	
	
	/*@ManyToOne	
    private Node parent;	 
	@OneToMany(mappedBy = "parent")
	private Set<Node> children = new HashSet<Node>();*/
	
	/*@OneToMany 
	@OrderColumn 
	@JoinColumn(name = "idNodeParent") 
	//private List<Node> children = new LinkedList<Node>(); 
	private List<Node> children;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "idNodeParent",insertable=false,updatable=false) 
	private Node parent; */
	
	@JsonManagedReference
	@LazyCollection(value=LazyCollectionOption.FALSE)
	@OneToMany (mappedBy = "parent", cascade = javax.persistence.CascadeType.ALL, fetch = javax.persistence.FetchType.EAGER) 
	//@OrderColumn	
	private List<Node> children;
	
	@JsonBackReference	
	@ManyToOne(fetch=FetchType.LAZY)
	private Node parent; 
	
	
	
	@Transient
	private List<Node> nodes;
	

	

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public int getIdNode() {
		return idNode;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/*public int getIdNodeParent() {
		return idNodeParent;
	}

	public void setIdNodeParent(int idNodeParent) {
		this.idNodeParent = idNodeParent;
	}*/

	public String getNmNode() {
		return nmNode;
	}

	public void setNmNode(String nmNode) {
		this.nmNode = nmNode;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	
	/**
     * Remove the Node<T> element at index index of the List<Node<T>>.
     * @param index the index of the element to delete.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }
    
    /**
     * Inserts a Node<T> at the specified position in the child list. Will     * throw an ArrayIndexOutOfBoundsException if the index does not exist.
     * @param index the position to insert at.
     * @param child the Node<T> object to insert.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void insertChildAt(int index, Node child) throws IndexOutOfBoundsException {
        if (index == getNumberOfChildren()) {
            // this is really an append
            addChild(child);
            return;
        } else {
            children.get(index); //just to throw the exception, and stop here
            children.add(index, child);
        }
    }
    

    /**
     * Returns the number of immediate children of this Node<T>.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }
	
    /**
     * Adds a child to the list of children for this Node<T>. The addition of
     * the first child will create a new List<Node<T>>.
     * @param child a Node<T> object to set.
     */
    public void addChild(Node child) {
        if (children == null) {
            children = new ArrayList<Node>();
        }
        children.add(child);
    }
	
	
	
	

}
