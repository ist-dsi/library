define(['backbone', 'text!templates/StudentListTemplate.html', "views/StudentView", "libs/search", "models/StudentModel", "collections/StudentCollection"], 

	function(Backbone, tpl, studentView, searchModule, studentModel, studentCollection){
	return Marionette.CompositeView.extend({
		template : tpl,
		itemView: studentView,
		itemViewContainer: "#studentListTable",
		
		initialize: function() {
			this.collection = this.model.students;
			this.collection.bind('add', this.updateUsers, this);
			this.collection.bind('remove', this.updateUsers, this);
		},
		events: {
	        'submit #searchForm': 'search',
    	},
    	search: function(){
    		searchModule.search($('#userID').val(), this.collection, this.model.libraryID);
    	},
    	
    	updateUsers: function(){
    		value = this.collection.length * 100 / this.model.get("maxCapacity");
    		$('#studentBar').css("width", value + "%");
    	}
	}); 
});
