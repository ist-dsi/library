define( ['backbone','text!templates/LibraryListTemplate.html', 'views/LibraryView', 'models/LibraryModel'], function(Backbone, tpl,  LibraryView, LibraryModel){
	return Marionette.CompositeView.extend({
		template : tpl,
		itemView: LibraryView,
		className: "container-fluid",

		events:{
			'click #add' : 'addNewLibrary'
		},

		appendHtml: function(collectionView, itemView){
			collectionView.$("ul").append(itemView.el);
			itemView.onClick = this.clicked;
		},
		initialize : function(){
			this.model =  new Backbone.Model();
		},

		addNewLibrary : function(){
			var newLibrary = new LibraryModel();
			var collection = this.collection;
			newLibrary.save({},
					{
				success: function(){
					collection.add(newLibrary);
					newLibrary.trigger('custom');
				}});
		}

	});
});
