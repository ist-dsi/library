define( ['backbone', 'app', 'text!templates/LibraryOperatorListTemplate.html', 'views/LibraryOperatorElementView'], function(Backbone, App, tpl, LibraryOperatorView){
	return Marionette.CompositeView.extend({
		template : tpl,
		itemView: LibraryOperatorView,

		itemViewContainer: "#list",
		events :{
			'click #removeOperator' : 'removeOperator',
			'submit #searchForm' : 'search'
		},
		removeOperator: function(){
			if(this.collection.length == 1){
				alert("Apenas um operador presente. Não será possível remove-lo.")
				return;
			}
			$('#userID').val($('#userName').html());
			this.search();
			this.collection.get($('#userName').html()).destroy();
		},
		initialize : function(){
			App.addRegions({ person : "#person"});
		},
		search : function(){
			var collection = this.collection;
			require(["libs/search"], function(SearchModule){
				SearchModule.searchPerson($('#userID').val(), collection);
			});
			
		}
	});
});
