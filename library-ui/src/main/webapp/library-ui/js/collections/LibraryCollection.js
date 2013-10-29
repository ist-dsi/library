define(['backbone', 'models/LibraryModel'], function(Backbone, Library){ return Backbone.Collection.extend({
	  model: Library,
	  url: "../api/library-core/libraries",
	  
	  parse: function(data) {
			return data.libraries;
	  }
	});
});
