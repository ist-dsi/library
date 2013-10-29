define(['backbone','text!templates/LockerListTemplate.html', 'app', 'views/LockerView','client-factory'], function(Backbone, tpl, App, LockerView, ClientFactory){
	return Marionette.CompositeView.extend({
	  template:tpl,
	  itemView: LockerView,
	  itemViewContainer: "#lockerOptions",
	 
	}); 
});
