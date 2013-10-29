define(['backbone', "models/StudentModel"],
	function(Backbone, studentModel){ 
		return Backbone.Model.extend(
		{
			parse: function(data) {
				this.students = new Backbone.Collection(data.collection, {model : studentModel, libraryID : this.libraryID});
				return data;
			},
			
			url: function(){
				return "../api/library-core/libraries/" + this.libraryID +"/students";
			}
		});
	});
