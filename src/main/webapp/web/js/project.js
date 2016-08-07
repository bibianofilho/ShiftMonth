
//-------------------------------------- combobox UI -------------------------------
(function( $ ) {
		$.widget( "custom.combobox", {
			_create: function() {
				this.wrapper = $( "<span>" )
					.addClass( "custom-combobox" )
					.insertAfter( this.element );

				this.element.hide();
				this._createAutocomplete();
				this._createShowAllButton();
			},

			_createAutocomplete: function() {
				var selected = this.element.children( ":selected" ),
					value = selected.val() ? selected.text() : "";

				this.input = $( "<input>" )
					.appendTo( this.wrapper )
					.val( value )
					.attr( "title", "" )
					.addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: $.proxy( this, "_source" )
					})
					.tooltip({
						tooltipClass: "ui-state-highlight"
					});

				this._on( this.input, {
					autocompleteselect: function( event, ui ) {
						ui.item.option.selected = true;
						this._trigger( "select", event, {
							item: ui.item.option
						});
					},

					autocompletechange: "_removeIfInvalid"
				});
			},

			_createShowAllButton: function() {
				var input = this.input,
					wasOpen = false;

				$( "<a>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.tooltip()
					.appendTo( this.wrapper )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "custom-combobox-toggle ui-corner-right" )
					.mousedown(function() {
						wasOpen = input.autocomplete( "widget" ).is( ":visible" );
					})
					.click(function() {
						input.focus();

						// Close if already visible
						if ( wasOpen ) {
							return;
						}

						// Pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
					});
			},

			_source: function( request, response ) {
				var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
				response( this.element.children( "option" ).map(function() {
					var text = $( this ).text();
					if ( this.value && ( !request.term || matcher.test(text) ) )
						return {
							label: text,
							value: text,
							option: this
						};
				}) );
			},

			_removeIfInvalid: function( event, ui ) {

				// Selected an item, nothing to do
				if ( ui.item ) {
					return;
				}

				// Search for a match (case-insensitive)
				var value = this.input.val(),
					valueLowerCase = value.toLowerCase(),
					valid = false;
				this.element.children( "option" ).each(function() {
					if ( $( this ).text().toLowerCase() === valueLowerCase ) {
						this.selected = valid = true;
						return false;
					}
				});

				// Found a match, nothing to do
				if ( valid ) {
					return;
				}

				// Remove invalid value
				this.input
					.val( "" )
					.attr( "title", value + " didn't match any item" )
					.tooltip( "open" );
				this.element.val( "" );
				this._delay(function() {
					this.input.tooltip( "close" ).attr( "title", "" );
				}, 2500 );
				this.input.autocomplete( "instance" ).term = "";
			},

			_destroy: function() {
				this.wrapper.remove();
				this.element.show();
			}
		});
	})( jQuery );


//-------------------------------------------------------------

var dataTreeview;
var identifierNew=-10;


var defaultDataV = [
                   {
                     text: "LGH290000000000000",
                     nodes: [
                       {
                         text: "Brazil Vivo",
                         nodes: [
                           {
                             text: "Implementation"
                           }
                         ]
                       }
                     ]
                   }
                 ];

setDataTreeview = function ( dataTreeviewLocal){	
	dataTreeview = dataTreeviewLocal;	
	$('#tree').treeview({
    	showTags: true,
    	expandIcon: 'glyphicon glyphicon-chevron-right',    	
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        highlightSelected: true,
        onhoverColor: "orange",
        selectable: true,
        enableLinks: true,       
        emptyIcon:"",
        showIcon:true,
    	data:dataTreeview
    });	
}


addNode = function (nodeParentv,  nodeNew){
	
	$('#tree').treeview('addNodeP',{"data":dataTreeview, "nodeParentv":nodeParentv,"nodeNew":nodeNew});	
}



//get node pai
getNodeParent = function ( identifierParent,  nodesV, nodeParentv, newDataTreeviewTemp  ){
	returnNode =null;
	var x;
	for(x=0;nodesV.length;x++){
		var key =nodesV[x].identifier;	    		
		if(key==identifierParent){
			nodeParentv.nodes.push(nodeNew[0]);
			returnNode = nodesV[x];
			return returnNode;			
		}else{
			if(key<identifierParent){
				nodeChild = nodesV[x].nodes;
				var y;
				for (y =0; y< nodeChild.length; y++) {
					if(nodeChild[y].identifier == identifierParent ){
						returnNode = nodeBean;
		    			return returnNode;
					}else{
			    			if(nodeChild[y].nodes.length>0){
				    			 return getNodeRecursive(identifierParent,nodeChild[y].nodes);
				    		}
			    		}
				}
			}
		}
	}
	return returnNode;
}

//-------------------------------------- Dialog -------------------------------
alertDialog = function (titleV, messageV){
	BootstrapDialog.show({
	            title: titleV,
	            type:BootstrapDialog.TYPE_DANGER,
	            message: messageV,
	            buttons: [{
	                id: 'btn-OK',
	                label: 'OK.',
	                action: function(dialog) {
	                    dialog.close();
	                }
	            }]
	        });
	
	return;

}

var taskObj ={
		nmTask:"",
		dsTask:""
}

//actionDialog("NEW", "EDIT")
dialogTask = function(actionDialog, taskObj){	

 var messageDialog = $('<form>'+
			'<div class="form-group">'+
			'<label for="recipient-name" class="control-label" id="nmRecipient" >Name:</label>'+
			'<input type="text" class="form-control" id="nmTask">'+
			'</div>'+  
			'<div class="form-group">'+
			'<label for="recipient-name" class="control-label" id="nmRecipient" >Description:</label>'+
			//'<input type="text" class="form-control" id="dsTask">'+
			'<textarea class="form-control" rows="5" cols="30" id="dsTask" ></textarea>'+
			'</div>'+ 
		'</form>');
 
 var TaskNew = {
 		//"node": {"idNode":taskObj.idNode,
 		//"project":{"idProject": taskObj.idProject }}  
		}
 
 if(actionDialog=="EDIT"){
	 $(messageDialog).find("#nmTask").val(taskObj.nmTask);
	 $(messageDialog).find("#dsTask").val(taskObj.dsTask);	 
	 
	 TaskNew = {"idTask":taskObj.idTask,
		 		"node": {"idNode":taskObj.idNode,
		 		"project":{"idProject": taskObj.idProject }}  
				}
 } else if(actionDialog=="NEW"){
	 TaskNew = {
		 		"node": {"idNode":taskObj.idNode,
		 		"project":{"idProject": taskObj.idProject }}  
				}
 }
	
	var dialogTaskLocal = new BootstrapDialog({
						    title: 'Task',
						    draggable: true,
						    message: $(messageDialog),
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
						        	
						        	var msgAlert ="";
						          	if($('#nmTask').val().length==0){
						          		msgAlert="<li>Task Name Required</li>";					
						        	}  		    	
						          	if($('#dsTask').val().length==0){
						          		msgAlert+="<li>Task Description Required</li>";
						        	}  		    	
						          	if(msgAlert.length>0){
						        		alertDialog("Alert","<ul>"+msgAlert+"</ul>");
						        		return;					
						        	}
						          	
						        	var nodeSelected =$('#tree').treeview('getSelected');
						        	TaskNew.nmTask= $('#nmTask').val();
						        	TaskNew.dsTask= $('#dsTask').val();
						  			var json = JSON.stringify(TaskNew );
						  			$.ajax({
						  				type: "POST",
						  				url: '/shiftmonth/project/addTask',	
						  				data: {"paramJson":json},
						  				 dataType: "json",
						  				success: function (allData) {
						  					if(allData.success){				
						  						dataTreeview = allData.result;
						  						setDataTreeview(dataTreeview);
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
 
 	dialogTaskLocal.setMessage(messageDialog).open();
}


dialogNode = function(actionDialog, nodeObj){	

	 var messageDialog = $('<form> '+
					          '<div class="form-group"> '+
					           ' <label for="recipient-name" class="control-label" id="nmRecipient" >Name:</label> '+
					           ' <input type="text" class="form-control" id="nmNode"> '+
					          '</div> '+
					        '</form>');
	 
	 var nodeNew = {};
	 
	 if(actionDialog=="EDIT"){
		 $(messageDialog).find("#nmNode").val(nodeObj.nmNode);
		 nodeNew = {"idNode":nodeObj.idNode,			 		
			 		"project":{"idProject": nodeObj.idProject} ,
			 		"parent":{idNode:nodeObj.idNodeParent}
					}
	 } else if(actionDialog=="NEW"){
		 nodeNew = {			 		
			 		"project":{"idProject": nodeObj.idProject } ,
			 		"parent":{idNode:nodeObj.idNodeParent}
					}
	 }
		
		var dialogLocal = new BootstrapDialog({
							    title: 'Node',
							    draggable: true,
							    message: $(messageDialog),
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
							        	nodeNew.nmNode= $('#nmNode').val();
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
	 
		dialogLocal.setMessage(messageDialog).open();
	}
