define(['backbone', 'text!templates/StudentFullViewTemplate.html', 'models/StudentModel', 'collections/LockerCollection', 'views/LockerListView', 'app'], function(Backbone, tpl, studentModel, LockerCollection, LockerListView, App){
	return Marionette.ItemView.extend({
		template: tpl,
		events: {
			'click #leave': 'leave',
			'click #enter': 'enter',
			'click #generateCode' : 'generateCode'
		},
		leave: function(){
			this.model.removeFromCollection();
			$('#operations').html("<div id='lockersList'></div><br/> <a href='javascript:void(0)' id='enter'>Entrar</a>");
			this.populateLockersInfo();
		},

		enter: function(){
			var model = this.model;
			this.model.save({},{
				success: function(model, response){
					model.addToCollection();
					$('#operations').html("Biblioteca : " + model.collection.libraryName + " <a href='javascript:void(0)' id='leave'>Sair</a>");
				},
				error: function(model, response){
					alert("Capacidade Máxima Excedida.");
				}
			});
		},
		initialize : function(){
			this.populateLockersInfo();
		},
		populateLockersInfo : function(){
			App.addRegions({lockers : "#lockersList"});
			lockersCollection = new LockerCollection();
			lockersCollection.studentModel = this.model;
			lockersCollection.libraryID = this.model.collection.libraryID;
			lockersCollection.fetch({ success : function() {
				listView = new LockerListView({collection : lockersCollection});
				App.lockers.show(listView);
			}});
		},
		generateCode : function(){
				this.model.set("generateCode", true);
				var model = this.model;
				this.model.save({},{
					success: function(){
						require(["libs/search"], function(SearchModule){
							SearchModule.search(model.get("id"), model.collection, model.collection.libraryID);
						});	
					}
				});
		}
		});
	}); 
