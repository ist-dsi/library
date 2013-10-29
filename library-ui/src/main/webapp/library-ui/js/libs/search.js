define(['app', 'models/StudentModel', 'views/StudentFullView', 'models/PersonModel', 'views/PersonView'], function(App, StudentModel, StudentFullView, PersonModel, PersonView){
	return {
		search : function(studentId, collection, libraryID){
			var student = new StudentModel({id: studentId, library:libraryID});
			student.collection = collection;
			student.fetch({ 
				success : function() {
					App.person.show(new StudentFullView({model:student}));
				}
			});
		},

		searchPerson : function(userID, collection){
			var model = new PersonModel({id : userID});
			model.collection = collection;
			model.fetch({
				success: function(){
					App.person.show(new PersonView({model : model}));
				}
			})
		}

	}
});