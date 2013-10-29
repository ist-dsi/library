define([
    'collections/LibraryCollection', 'collections/StudentCollection', 'collections/LibraryOperatorCollection'
], function(LibraryCollection, StudentCollection, LibraryOperatorCollection) {
	
	var LibraryManager = LibraryManager || {};

	LibraryManager.LibraryCollection = new LibraryCollection || {};
	LibraryManager.StudentCollection = new StudentCollection || {};
	LibraryManager.LibraryOperatorCollection = new LibraryOperatorCollection || {};

	return LibraryManager;
});
