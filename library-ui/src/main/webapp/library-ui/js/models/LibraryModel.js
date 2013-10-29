define(['backbone'], function(Backbone){
		return Backbone.Model.extend({
			url: function(){
				if(this.get("id"))
					return "../api/library-core/libraries/" + this.get("id");
				else
					return "../api/library-core/libraries";
			}
	});
});
