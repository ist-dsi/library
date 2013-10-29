define(['backbone'], function(Backbone){
	return Backbone.Model.extend({
		url : function() {
			return '../api/library-core/users/' + this.id;
		} 
	});
});
