package com.mbf.shiftmonth.treeTemp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.mockito.internal.listeners.CollectCreatedMocks;

import scala.annotation.meta.setter;



public class Tree {

	
	 private final static int ROOT = 0;

	    private HashMap<Integer, NodeBean> nodes;
	    
	    
	    public void setNodes(HashMap<Integer, NodeBean> nodes) {
			this.nodes = nodes;
		}

		public Tree() {
	    	this.nodes = new HashMap<Integer, NodeBean>();
	    }
	    
	    // Properties
	    public HashMap<Integer, NodeBean> getNodes() {
	        return nodes;
	    }

	    // Public interface
	    public NodeBean addNode(int identifier, NodeBean node) {
	        return this.addNode(identifier,node, -1);
	    }

	   /* public NodeBean addNode(int identifier,  NodeBean node,int parent) {	    	
	       
	        if (parent != -1) {
	        	NodeBean nodeParent = getNodeRecursive(parent);
	        	NodeState nodeState = new NodeState();
	        	nodeState.setExpanded(true);
	        	nodeParent.setState(nodeState);
	        	nodeParent.addChild(node);
	            //nodes.get(parent).addChild(node);
	        }else{
	        	 nodes.put(identifier, node);
	        }

	        return node;
	    }*/
	    
	    public NodeBean addNode(int identifier,  NodeBean node, int identifierParent ) {	    	
		    
	    	List<String>  tags = new ArrayList<String>();
	    	tags.add("<span class='badge'><a href='' class='linkMenuNode' onClick='menuShowLink(this);return false;' class='MenuGrid' data-idNode='"+identifier+"'  data-tpNode='"+node.getTpNode()+"' data-dsNode='"+node.getDataValue()+"'  style='a:link {color: #FF0000;}' >Menu </a></span>");
	    	/*StringBuffer buffer = new StringBuffer();
	    	buffer.append("<div class='dropdown'>");
	    	buffer.append("<button class='btn btn-default dropdown-toggle' type='button' id='dropdownMenu1' data-toggle='dropdown' aria-expanded='true'> Dropdown     <span class='caret'></span>  </button>");
  
	    	buffer.append("<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>");
	    	buffer.append("<li role='presentation'><a role='menuitem' tabindex='-1' href='#'>New Node</a></li>");
	    	buffer.append("<li role='presentation'><a role='menuitem' tabindex='-1' href='#''>New Task</a></li>");
	    	buffer.append("<li role='presentation'><a role='menuitem' tabindex='-1' href='#'>Rename</a></li>");    
	    	buffer.append("</ul>");
	    	buffer.append("</div>");	
	    	tags.add(buffer.toString());*/
	    	node.setTags(tags);
        	
	        if (identifierParent !=-1) {
	        	Collection<NodeBean> temp =  nodes.values();
	        	ArrayList<NodeBean > aaa= new  ArrayList<NodeBean > (temp);
	        	NodeBean[] nodeParentArray = new NodeBean[]{};
	        	
	        	NodeBean nodeParent =null;
	        	for (NodeBean nodeBean : aaa) {
	        		nodeParent = getNodeRecursive(identifierParent,  nodeBean);
				}
	        	
	        	
	        	NodeState nodeState = new NodeState();
	        	nodeState.setExpanded(true);
	        	nodeParent.setState(nodeState);
	        	
	        	nodeParent.addChild(node);
	            //nodes.get(parent).addChild(node);
	        }else{
	        	 nodes.put(identifier, node);
	        }

	        return node;
	    }
	    
	    /*private static void getNodeRecursive(int identifier, List<NodeBean> nodesV, NodeBean[] nodeParent ){
	    	NodeBean returnNode =null;
	    	//for (int key : nodesV.keySet()) {
	    	for(NodeBean nodeV : nodesV){
	    		int key =nodeV.getIdentifier();	    		
	    		if(key==identifier){
	    			returnNode = nodeV;
	    			//return returnNode;
	    			nodeParent = new NodeBean[]{ returnNode};
	    			return;
	    			
	    		}else{
	    			//if(key<identifier){
				    	//List<NodeBean> nodeChild =  nodeV.get(key).getNodes();
	    				List<NodeBean> nodeChild =  nodeV.getNodes();
				    	for (NodeBean nodeBean : nodeChild) {
				    		if(nodeBean.getIdentifier() == identifier ){
				    			returnNode = nodeBean;
				    			//return returnNode;
				    			//nodeParent = returnNode;
				    			nodeParent = new NodeBean[]{ returnNode};
				    			return;
				    			
				    		}else{
				    			if(nodeBean.getNodes().size()>0){
				    			 //return getNodeRecursive(identifier,nodeBean.getNodes());
				    				getNodeRecursive(identifier,nodeBean.getNodes(),nodeParent);
				    			}else{
				    				System.out.println("aki2");
				    			}
				    		}
						}
	    			//}else{
	    			//	System.out.println("aki1");
	    			//}
	    			
	    		}
	    	}
	    	//return getNodeRecursive(identifier,null);
	    }*/
	    
	    
	    private static NodeBean getNodeRecursive(int identifier, NodeBean nodesV){
	    	
	    	if(nodesV.getIdentifier()==identifier){    			
    			return nodesV;
	    	}
    			
	    	
	    	NodeBean returnNode =null;
	    	List<NodeBean> nodesVTemp=nodesV.getNodes();
	    	if(nodesVTemp!=null){
		    	for(NodeBean nodeV : nodesVTemp){
		    		int key =nodeV.getIdentifier();	    		
		    		if(key==identifier){
		    			returnNode = nodeV;
		    			//return returnNode;
		    			break;
		    		}else{	
		    				returnNode = getNodeRecursive(identifier,nodeV);
		    				if(returnNode!=null){
		    					break;
		    				}
		    			}
		    	}
		    	
	    	}
	    	return returnNode;
	    }
	    

	    public void display(String identifier) {
	        this.display(identifier, ROOT);
	    }

	    public void display(String identifier, int depth) {
	        List<NodeBean> children = nodes.get(identifier).getNodes();

	        if (depth == ROOT) {
	            System.out.println(nodes.get(identifier).getText());
	        } else {
	            String tabs = String.format("%0" + depth + "d", 0).replace("0", "    "); // 4 spaces
	            System.out.println(tabs + nodes.get(identifier).getText());
	        }
	        depth++;
	        for (NodeBean child : children) {

	            // Recursive call
	            this.display(child.getText(), depth);
	        }
	    }
}
