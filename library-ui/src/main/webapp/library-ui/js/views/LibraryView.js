define(['backbone','text!templates/LibraryElementTemplate.html', 'app', 'views/StudentListView','client-factory'], function(Backbone, tpl, App, studentListView, ClientFactory){
	return Marionette.ItemView.extend({
		template:tpl,
	  	tagName: 'li',

		events: {
	        'click ': 'clicked',
		},
		clicked: function(){
			this.onClick();
			$("#selectedLibrary").html(this.model.get("name") + "");
		}
	}); 
});
