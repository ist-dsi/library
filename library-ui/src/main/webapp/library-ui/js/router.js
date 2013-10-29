define([
        'jquery',
        'underscore',
        'mustache',
        'backbone',
        'marionette',
        'app',
        'client-factory',
        'views/StudentListView',
        'views/LibraryCapacityView',
        'views/LibraryOperatorListView',
	'jquery.bootstrap'
        ], function($, _, Mustache, Backbone, Marionette, App, ClientFactory, studentListView, LibraryCapacityView, LibraryOperatorListView, Bootstrap) {

	var Router = Backbone.Marionette.AppRouter.extend({
		initialize : function() {

		},

		appRoutes: {
			"" : "showHome",
			"operator" : "showHome",
			"capacityManagement" : "manageCapacity",
			"operatorsManagement" : "manageOperators"
		},



		controller: {
			presentLibraryList : function(onClickHandler, title, modifiable){
				App.addRegions({content : "#librarycontent"});
				require(['views/LibraryListView'], function(HomeView) {
					var todoCollection = ClientFactory.LibraryCollection;
					todoCollection.fetch({ success : function() {
						libraryListView = new HomeView({ collection: todoCollection});
						libraryListView.model.set("title", title);
						libraryListView.model.set("Modifiable", modifiable);
						libraryListView.clicked = onClickHandler;
						App.page.show(libraryListView);
					}});
				});
			},
			showHome : function() {
				onClickHandler = function(){
					var studentCollection = ClientFactory.StudentCollection;
					var libraryID = this.model.get("id");
					var libraryName = this.model.get("name");
					var library = this.model;
					studentCollection.libraryID =libraryID;
					studentCollection.fetch({ success : function() {
						studentsListView =new studentListView({ model: studentCollection}); 
						App.content.show(studentsListView);
						studentCollection.students.libraryID =libraryID; 
						studentCollection.students.libraryName =libraryName;
					}});
					App.addRegions({person : "#person"});
				};
				this.presentLibraryList(onClickHandler, "Operator", false);

			},
			manageCapacity : function(){
				onClickHandler = function(){
					App.content.show(new LibraryCapacityView({model : this.model}));
				};
				this.presentLibraryList(onClickHandler, "Capacity", true);
			},
			manageOperators : function(){
				App.addRegions({content : "#librarycontent"});

				var collection = ClientFactory.LibraryOperatorCollection;
				collection.fetch({ success : function() {
					libraryOperatorListView = new LibraryOperatorListView({ collection: collection});
					App.page.show(libraryOperatorListView);
				}});
			}
		}
	});
	return Router;

});
