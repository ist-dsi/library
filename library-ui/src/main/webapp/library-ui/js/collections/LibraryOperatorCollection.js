define(['backbone'], function(Backbone){
 return Backbone.Collection.extend({
	  url: "../api/library-core/libraryOperators",
	});
});
