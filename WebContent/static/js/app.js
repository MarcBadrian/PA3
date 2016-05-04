/*
 * COMP 6302 - Web Services / Internet
 * PA3: Theatre Mashup Service
 * Marc Badrian and Hien Vo - Due 5/4/16
 * 
 */


//function that gets all the current customers
//$("#list_all_theatre_play_button").click(function(){
//	$.get("/PA3/plays/all", function(data){
//		$("#theatreHeader").html(data);
//	});
	
//});

//function that gets all the transactions for a specified customer (by id)


$("#list_all_theatre_play_button").click(
		function() {		
			$.ajax({
				method : 'GET',
				url : "/PA3/REST/plays",
				success : function(data) {
					console.log("Success!");

				},
				error : function(data) {
					console.log(data);
				}
			});
		});	
