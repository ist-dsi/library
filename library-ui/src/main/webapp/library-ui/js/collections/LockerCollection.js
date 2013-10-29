define(['backbone', "models/StudentModel"],
	function(Backbone, studentModel){ 
		return Backbone.Collection.extend(
		{
			url: function(){
				return "../api/library-core/libraries/" + this.libraryID +"/availableLockers";
			}
		});
	});
