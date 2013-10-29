define(['backbone', 'text!templates/StudentElementTemplate.html', 'libs/search'], function(Backbone, tpl, searchModule){
	return Marionette.ItemView.extend({
	  template: tpl,
	  tagName: 'tr',
	  events: {
	        'click #see': 'see',
	        'click #leave': 'leave',
  		},
  		see : function(){
  			searchModule.search(this.model.get("id"), this.model.collection, this.model.collection.libraryID);
  		},
  		leave : function(){
  			this.model.removeFromCollection();
  		}
	}); 
});
