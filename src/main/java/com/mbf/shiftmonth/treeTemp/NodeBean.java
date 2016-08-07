package com.mbf.shiftmonth.treeTemp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.mbf.shiftmonth.model.Node;

public class NodeBean {
	
	String text;
	List<NodeBean> nodes;
	//private List<Node> children = new LinkedList<Node>(); 
	
	 private int identifier;
	 
	 
	 private String backColor ="";
	 
	 NodeState state;
	 
	 String icon = "";//"glyphicon-stop";
	 
	 List<String> tags;
	 
	 TpNode tpNode;
	 
	 String  expandIcon = "glyphicon glyphicon-chevron-right";
	 
	 
	 HashMap<String,String> dataValue;
	 
	 public  HashMap<String,String> getDataValue() {
		return dataValue;
	}

	public void setDataValue( HashMap<String,String> dataValue) {
		this.dataValue = dataValue;
	}

	public String getExpandIcon() {
		return expandIcon;
	}

	public void setExpandIcon(String expandIcon) {
		this.expandIcon = expandIcon;
	}

	public TpNode getTpNode() {
		return tpNode;
	}

	public void setTpNode(TpNode tpNode) {
		this.tpNode = tpNode;
	}

	public static enum TpNode {	
		 NODE(1), TASK(2);
	 		private final int valor; 
	 		TpNode(int valorOpcao){ 
	 			valor = valorOpcao; 
	 		} 
	 		public int getValor(){ 
	 			return valor; 
	 		} 
	 }

	
	 
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	// Constructor
    public NodeBean(int identifier) {
        this.identifier = identifier;
        //nodes = new ArrayList<NodeBean>();
    }
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<NodeBean> getNodes() {
		return nodes;
	}
	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public void setNodes(List<NodeBean> nodes) {
		if(nodes==null){
			nodes = new ArrayList<NodeBean>();
		}
		this.nodes = nodes;
	}
	

	

	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/**
     * Remove the Node<T> element at index index of the List<Node<T>>.
     * @param index the index of the element to delete.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
    	nodes.remove(index);
    }
    
    /**
     * Inserts a Node<T> at the specified position in the child list. Will     * throw an ArrayIndexOutOfBoundsException if the index does not exist.
     * @param index the position to insert at.
     * @param child the Node<T> object to insert.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void insertChildAt(int index, NodeBean child) throws IndexOutOfBoundsException {
        if (index == getNumberOfChildren()) {
            // this is really an append
            addChild(child);
            return;
        } else {
        	nodes.get(index); //just to throw the exception, and stop here
        	nodes.add(index, child);
        }
    }
    

    /**
     * Returns the number of immediate children of this Node<T>.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (nodes == null) {
            return 0;
        }
        return nodes.size();
    }
	
    /**
     * Adds a child to the list of children for this Node<T>. The addition of
     * the first child will create a new List<Node<T>>.
     * @param child a Node<T> object to set.
     */
    public void addChild(NodeBean child) {
        if (nodes == null) {
        	nodes = new LinkedList<NodeBean>();
        }
        nodes.add(child);
    }
    
   
    public NodeBean lastNode(NodeBean child) {
        if (nodes == null) {
        	nodes = new LinkedList<NodeBean>();
        }
        return getNodeIndex(nodes.lastIndexOf(child));
    }
    
    public NodeBean getNodeIndex(int index) {        
        return nodes.get(index);
    }

}
