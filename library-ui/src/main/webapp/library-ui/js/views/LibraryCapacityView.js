 define( ['marionette','text!templates/LibraryCapacityTemplate.html'], function(Marionette, tpl){
	return Marionette.ItemView.extend({
	  template : tpl,
	  events :{
		  'click #button' : 'update',
		  'click #delete' : 'deleteLibrary'
	  },
	  update: function(){
		  var model = this.model;
		  this.model.set("name", $('#name').val());
		  this.model.set("capacity", $('#capacity').val());
		  this.model.set("lockers", $('#lockers').val());
		  $('#selectedLibrary').html(model.get("name") + "");
		  this.model.save({}, {error:function(){
			  model.fetch({success: function(){
				  $('#name').val(model.get("name"));
				  $('#capacity').val(model.get("capacity"));
				  $('#lockers').val(model.get("lockers"));
				  $('#selectedLibrary').html(model.get("name") + "");
			  }});
		  }});
	  },
	  deleteLibrary: function(){
		  this.model.destroy();
		  window.location.reload();
	  }
	});
});
