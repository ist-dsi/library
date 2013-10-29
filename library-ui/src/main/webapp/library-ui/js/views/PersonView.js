define(['backbone', 'text!templates/PersonViewTemplate.html', 'app'], function(Backbone, tpl, App){
	return Marionette.ItemView.extend({
		template: tpl,
		events:{
			'click #addOperator' : 'add',
			'click openModal' : 'prepareOpenModal'
		},
		add : function(){
			var model = this.model;
			var thisView = this;
			model.url = "../api/library-core/libraryOperators/" + this.model.get("id");
			model.save({}, {
				success:function(){
					model.collection.add(model);
					model.set("isOperator", false);
					thisView.refreshView();
				},
				error: function(){
				}
			});
			
		},
		prepareOpenModal : function(){
			$('#userName').html(this.model.get('id'));
		},
		
		refreshView: function(){
			var thisView = this;
			require(["libs/search"], function(SearchModule){
				SearchModule.searchPerson(thisView .model.get("id"), thisView .model.collection);
			});
		}
	}); 
});
