<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/template/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shift Month</title>
<script src="../web/js/jquery/jquery-1.11.2.min.js"></script>
<!-- jquery-ui -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<!-- jquery-ui Combobox -->
<link rel="stylesheet" href="../web/css/jquery/jquery.ui.combobox.css">  
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<!-- bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>


<!-- bootstrap treeview -->
<link rel="stylesheet" type="text/css" href="../web/css/bootstrap/bootstrap-treeview.css">
<script src="../web/js/bootstrap/bootstrap-treeview.js"></script>


<!-- bootstrap dialog -->
<link rel="stylesheet" type="text/css" href="../web/css/bootstrap/bootstrap-dialog.min.css">
<script src="../web/js/bootstrap/bootstrap-dialog.min.js"></script>



<link rel="stylesheet" href="<c:url value='../web/css/project.css'/>">
<script src="../web/js/project.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	function mischandler(){
		return false;
	}
	document.getElementById("tree").oncontextmenu = mischandler;
	
		    	
   	$("#menuRename").click(function(e) { 
   		var nodeSelected =$('#tree').treeview('getSelected');
   		nodeSelected = $('#tree').treeview('getSelected')[0];
   		var tpNodeSelected = nodeSelected.tpNode;
   		var nodeParent =$('#tree').treeview('getParent', nodeSelected);
   		if(tpNodeSelected=="TASK"){
   			
   			var taskObj={nmTask:nodeSelected.text,
   						dsTask:nodeSelected.dataValue.dsTask,
   						idTask:nodeSelected.identifier,
   						idNode:nodeParent.identifier,
   						idProject: $('#combobox').find('option:selected').val()
   						};
   			
   			dialogTask("EDIT",taskObj);
   		}else if(tpNodeSelected=="NODE"){
   			
   			var nodeObj={
						nmNode:nodeSelected.text,						
						idNode:nodeSelected.identifier,
						idProject: $('#combobox').find('option:selected').val(),
						idNodeParent:nodeParent.identifier
						};  	
   			dialogNode("EDIT",nodeObj);
   		}
    });
   	
   	$("#menuNode").click(function(e) {
   		var nodeSelected =$('#tree').treeview('getSelected');
   		nodeSelected = $('#tree').treeview('getSelected')[0];
   		var nodeObj = { idProject:$('#combobox').find('option:selected').val()};
   		nodeObj.idNodeParent = nodeSelected.identifier;
   		   		
   		
   		dialogNode("NEW",nodeObj);
    });
   	
   	$("#menuTask").click(function(e) {
   		
   		var nodeSelected =$('#tree').treeview('getSelected');
   		nodeSelected = $('#tree').treeview('getSelected')[0];
   		var taskObj = {idNode:nodeSelected.identifier,
   					   idProject:$('#combobox').find('option:selected').val()
   						};   		
   		
   		dialogTask("NEW",taskObj);  
    });   
     
     $("#MenuB").hide().menu();
   	$(".MenuEditNodeRoot").hide();
   	$("#liEdit").hide();

	$( "#combobox" ).change(function(){		
		var project = {"idProject":$('#combobox').find('option:selected').val()};
		var json = JSON.stringify(project );   	
		$.ajax({
			type: "POST",
			url: '/shiftmonth/project/getProjectTree',					
			data: {"paramJson":json},
			success: function (allData) {
				if(allData.success){
					setDataTreeview(allData.result);
					if(allData.result.length==0 && $('#combobox').find('option:selected').val()!="-1" ){
						$(".MenuEditNodeRoot").show();
					}else{
						$(".MenuEditNodeRoot").hide();
					}
				} else {
					Message.error('Error retriving models.');
				}
			}
		});
		
		$.ajax({
			type: "POST",
			url: '/shiftmonth/project/getProjectById',					
			data: {"paramJson":json},
			success: function (allData) {
				if(allData.success){
					$('#txaDsProject').val(allData.result[0].dsProject);
					$("#txaDsProject").attr("disabled", "disabled");
				} else {
					Message.error('Error retriving models.');
				}
			}
		});		
		
		var projectSelected = $('#combobox').find('option:selected');
		if(projectSelected.val()=="-1"){
			$('#editProject').attr("disabled", "disabled");
			$('#editProject').val("");
			$("#liSave").hide();
			$("#liCancel").hide();
			$("#liEdit").hide();
			$("#liNew").show();
			$("#txaDsProject").attr("disabled", "disabled");
			$(".MenuEditNodeRoot").hide();
			$("#liEdit").hide();
		}else if(projectSelected.val()!="-1"){
			$('#editProject').val(projectSelected.text());
			$(".MenuEditProject").find("#liDelete").hide();
			$("#liSave").hide();
			$("#liCancel").hide();
			$('#editProject').attr("disabled", "disabled");		
			$("#txaDsProject").removeAttr("disabled"); 
			$("#liEdit").show();
		}		
	});
		
		
	$(".MenuEditProject").on("click", "li", function() {
		if($(this).attr("id")=="liEdit"){
			$(".MenuEditProject").attr("data-action","EDIT")
			$("#editProject").removeAttr("disabled"); 
			$("#liSave").show();
			$("#liCancel").show();
			$("#liEdit").hide();
			$("#liNew").hide();
			$("#txaDsProject").removeAttr("disabled"); 
		}else if($(this).attr("id")=="liSave"){
			var actionProject =  $(".MenuEditProject").attr("data-action");
			var projectObj =null;
			
			var msgAlert ="";
			if($('#editProject').val().length==0){		
				msgAlert="<li>Project Name Required.</li>";
			}
			if($('#txaDsProject').val().length==0){		
				msgAlert+="<li>Project Description Required.</li>";
			}					
			if(msgAlert.length>0){
				alertDialog("Alert","<ul>"+msgAlert+"</ul>");
				return;					
			}
			
			if(actionProject=="NEW"){					
				projectObj = {"nmProject":$('#editProject').val(),
							  "dsProject":$('#txaDsProject').val()};					
			}else if(actionProject=="EDIT"){					
				projectObj = {"idProject":$('#combobox').find('option:selected').val(),
							  "nmProject":$('#editProject').val(),
							  "dsProject":$('#txaDsProject').val()
							};
			}
			
			var json = JSON.stringify(projectObj );
			$.ajax({
				type: "POST",
				url: '/shiftmonth/project/addProject',
				data:{"paramJson":json},
				success: function (allData) {
					if(allData.success){
							setDataTreeview(null);
							var newOptions = allData.result;
							if(actionProject=="NEW"){		
						        $('#combobox').append($("<option></option>").attr("value",newOptions.idProject).text(newOptions.nmProject)); 
						        $('#combobox').val(newOptions.idProject);
							}
					} else {
						Message.error('Error retriving models.');
					}
				}
			});
			
			$('#editProject').attr("disabled", "disabled");
			$("#liSave").hide();
			$("#liCancel").hide();
			$("#liEdit").show();
			$("#liNew").show();
			$('#txaDsProject').attr("disabled", "disabled");
			$('.MenuEditNodeRoot').show();
		}else if($(this).attr("id")=="liCancel"){
			$('#editProject').attr("disabled", "disabled");
			$("#liSave").hide();
			$("#liCancel").hide();
			$("#liEdit").show();
			$("#liNew").show();
			$('#txaDsProject').attr("disabled", "disabled");
		}else if($(this).attr("id")=="liNew"){
			$(".MenuEditProject").attr("data-action","NEW");
			$('#editProject').removeAttr("disabled");
			$('#editProject').val("");
			$("#liSave").show();
			$("#liCancel").show();
			$("#liEdit").hide();
			$("#liNew").hide();
			$('#combobox').val(-1);
			setDataTreeview(null);
			$('#txaDsProject').val("");
			$("#txaDsProject").removeAttr("disabled"); 
		}
	});
		
	$("#liNewNodeRoot").on("click", function() {
		BootstrapDialog.show({
           title: 'Node',
           draggable: true,
           message: '<form>'+
		             '<div class="form-group">'+
		                '<label class="control-label" >Name:</label>'+
		                '<input type="text" class="form-control" id="nmNode">'+
		             '</div>'+          
		         '</form>'
           ,
           buttons: [{
               label: 'Cancel',
               action: function(dialogItself){
                   dialogItself.close();
               }
           },
               {
               label: 'OK',
               cssClass: 'btn-primary',
               action: function(dialogItself){
            	    if($('#nmNode').val().length==0){
	   					alertDialog("Alert","Node Name Required");
	   					return;
   					}
     		    	var nodeNew = {
     	  				    "nmNode": $('#nmNode').val(),
     	  				    "project":{"idProject": $('#combobox').find('option:selected').val() }
     	  				}  			
     	  			var json = JSON.stringify(nodeNew );   			
     	  			$.ajax({
     	  				type: "POST",
     	  				url: '/shiftmonth/project/addNode',	
     	  				data: {"paramJson":json},
     	  				 dataType: "json",
     	  				success: function (allData) {
     	  					if(allData.success){				
     	  						dataTreeview = allData.result;
     	  						setDataTreeview(dataTreeview);
      	  					if(allData.result.length==0){
    							$(".MenuEditNodeRoot").show();
    						}else{
    							$(".MenuEditNodeRoot").hide();
    						}
     	  					} else {
     	  						Message.error('Error retriving models.');
     	  					}
     	  				}
     	  			});
         			dialogItself.close();
               }
           }
           ]
       });
	});


     $.ajax({
		type: "POST",
		url: '/shiftmonth/project/getProjects',
		success: function (allData) {
			if(allData.success){				
				var newOptions = allData.result;					
				$.each(newOptions, function(key, value) {   
			        $('#combobox')
			            .append($("<option></option>")
			            .attr("value",value.idProject)
			            .text(value.nmProject)); 
			   }); 
			} else {
				Message.error('Error retriving models.');
			}
		}
	}); 	    
	     
     menuShowLink= function(obj ){	 
		 menuShow(obj);			
	}
	
	 menuShow= function(obj, LocationX, LocationY ){
		event.stopPropagation();
	    event.preventDefault();	
	    
	    var tpNode =$(obj).attr("data-tpnode");
	    
	    if(tpNode=="TASK"){
	    	$("#menuNode").hide();
	    	$("#menuTask").hide();
	    }else if(tpNode=="NODE"){
	    	$("#menuNode").show();
	    	$("#menuTask").show();
	    }
	    
		var nodeId =parseInt($(obj).attr("id"));		
		var menu = $( "#MenuB" );
		menu.show();
		
		if(isNaN(LocationX)){
			menu.position({
	            my: "left top",
	           at: "left bottom",
	            of: obj
	          });
		}else{
			menu.css('left', LocationX+5);
			menu.css('top', LocationY+5);	
		}

		  $( document ).on( "click", function() {
            menu.hide();
          });
		  
		  	  
		  $('#tree').treeview('selectNode', [ parseInt($(obj).closest("li").attr("data-nodeid")), { silent: true } ]);
	};
	     
     $("#tree").hover(
   		  function(obj) {
   			  if(obj.button==2 && $(obj.target).closest("span").find(".linkMenuNode").attr("class")!="linkMenuNode"){	    				  
    			  $('#tree').treeview('selectNode', [ parseInt($(obj.target).closest(".node-tree").data("nodeid")), { silent: true } ]);
   				  	menuShow($(obj.target).find(".badge a"),obj.pageX+5,obj.pageY+5);
   		    	}else{
   		    		if ($(obj.target).attr("class")!="linkMenuNode"){	    		    		
   		    			$( "#MenuB" ).hide();
   		    		}
   		    	}
   });   
});
  </script>
</head>
<body><div class="titulo-principal">Shift Month</div>
	<!-- <h1 class="titulo-principal">Great Month</h1> -->

	<nav class="nav navbar-default">
		<div class="container-fluid ">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Project Name: </a>
			</div>
		
			<div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
		      
		      <ul class="nav navbar-nav MenuEditProject" data-action="none">
		        <li id="liInput"   ><input type="text" id="editProject" class="form-control" placeholder="New Project" disabled="disabled"></li>
		        <li id="liNew"     ><a href="#"><span id="new-project"class="glyphicon glyphicon-plus"></span></a></li>		        
		        <li id="liEdit"    ><a href="#"><span id="edit-project"class="glyphicon glyphicon-pencil"></span></a></li>
		        <li id="liSave"   style="display:none" ><a href="#"><span id="edit-project"class="glyphicon glyphicon-ok"></span></a></li>
		        <li id="liCancel" style="display:none" ><a href="#"><span id="edit-project"class="glyphicon glyphicon-remove"></span></a></li>
		        <!-- <li id="liDelete" ><a href="#"><span id="remove-project" class="glyphicon glyphicon-trash"></span></a></li> -->
		      </ul>
		      
		      
		      <ul class="nav navbar-nav MenuEditNodeRoot navbar-left" data-action="none" >
		      	<li class="active" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</li>
		      	<li class="navbar-brand" >Node Root:</li>
		        <li  id="liNewNodeRoot"><a href="#"><span id="new-project"class="glyphicon glyphicon-plus"></span></a></li>	
		      </ul>
	  
			  <ul class="nav navbar-nav navbar-right " data-action="none">
				  <li  class="navbar-brand"><span>Projects: </span></li>
				  <li class="navbar-brand"> <select id="combobox">  <option value="-1">Select a Project</option></select></li>
		      </ul> <!-- nav navbar-nav navbar-right -->
		    </div><!-- /.navbar-collapse -->		
		</div>
	</nav>	
	
	<div class="alert alert-danger alert-dismissible " role="alert" id="alertForm" style="display:none">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>Warning!</strong> Better check yourself, you're not looking too good.
	</div>
	
	<div class="row">
		<div class="col-md-4 ">
			
			<form class="form-group">
				<fieldset >
				    <legend class="description">Description Project</legend>
				    <textarea class="form-control" rows="15" cols="30" id="txaDsProject" disabled="disabled"></textarea>
				</fieldset>
			</form>
		</div>
			
		<div class="col-sm-8"  id="projects">	
			<div id="tree"></div>
		</div>	
		</div>	
	</div>
	
	<div>
	  
	  <ul id="MenuB" style="position: absolute" class="" >
	    <li id="menuNode" data-whatever="Node" >New Node</li>
	    <li id="menuTask"   data-whatever="Node" >New Task</li>
	    <li id="menuRename"  data-whatever="Node" >Rename</li>
	  </ul>
	</div>
</body>
</html>
