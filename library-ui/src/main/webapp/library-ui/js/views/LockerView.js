define(['backbone','text!templates/LockerTemplate.html'], function(Backbone, tpl){
	return Marionette.ItemView.extend({
		template:tpl,
		tagName: 'li',
		
		events: {
	        'click ': 'clicked',
    	},
    	
    	clicked : function(){
    		//set this as dropdown value
    		$('#selectedLocker').html(this.model.get("number"));
    		this.model.collection.studentModel.set("locker", this.model.get("number"));
    	}
	}); 
});
