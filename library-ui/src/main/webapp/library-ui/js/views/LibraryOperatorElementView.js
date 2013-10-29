define( ['marionette','text!templates/LibraryOperatorElementTemplate.html'], function(Marionette, tpl){
	return Marionette.ItemView.extend({
		template : tpl,
		tagName : 'tr',
		events:{
			'click #openModal' : 'updateModal'
		},
		updateModal : function(){
			$('#userName').html(this.model.get('id'));
		}
		
	});
});
