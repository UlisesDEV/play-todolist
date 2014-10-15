if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

$(document).ready(function(){
	$('#datetimepicker1').datetimepicker({useSeconds: true,useCurrent: true});
});