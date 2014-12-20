CKEDITOR.editorConfig = function( config ) {  
	config.filebrowserBrowseUrl = '';
	config.filebrowserFlashBrowseUrl = '';
	config.filebrowserFlashUploadUrl = CKEDITOR.basePath + 'custom/upload4Fck.jsp';
	config.filebrowserImageBrowseLinkUrl = '';
	config.filebrowserImageBrowseUrl = '';
	config.filebrowserImageUploadUrl = CKEDITOR.basePath + 'custom/upload4Fck.jsp';
	config.filebrowserUploadUrl = CKEDITOR.basePath + 'custom/upload4Fck.jsp';
	// Define changes to default configuration here. For example:  
	config.language = 'zh-cn'; //配置语言  
	//config.uiColor = '#FFF'; //背景颜色  
	//config.width = 500; //宽度  
	config.height = 400; //高度  
	config.skin='v2';  
	//工具栏 
	/**/
	config.toolbar = [ 
	    ['Image','Smiley','Maximize','-','Source','-','TextColor','BGColor','FontSize','Font'],  
	];
};