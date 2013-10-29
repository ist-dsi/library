define( ['backbone'], function(Backbone){	
	return Backbone.Model.extend({
		url : function() {
			return "../api/library-core/user/" + this.id;
		},
		
		addToCollection: function(){
			this.collection.add(this);
		},
		removeFromCollection: function(collection){
			collection = this.collection.remove(this);
			this.destroy();
			this.collection = collection;
		}
	});
});
