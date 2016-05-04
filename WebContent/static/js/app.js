/*
 * COMP 6302 - Web Services / Internet
 * PA3: Theatre Mashup Service
 * Marc Badrian and Hien Vo - Due 5/4/16
 * 
 */

$(document).ready(function(){
$("#search_by_name_id").hide();

    $("#search_by_name_button").click(function(){
        $("#search_by_name_id").show();
    });

});



//function that gets all the play names
$("#list_all_theatre_play_button").click(
		function() {		
			$.ajax({
				method : 'GET',
				url : "/PA3/plays/all",
				success : function(data,status,xhr) {
					console.log("Success!");
			    	var line_split = data.split("\\|");
			    	var show_plays = "<h2>Plays: </h2>  <br> <br>";
			    	for(i = 0; i < line_split.length; i++) {
			    		var name = line_split[i];
			    		show_plays += "<a href=\"http://localhost:8080/PA3/REST/plays/" + name + "\">" + name + "</a> <br>";
			    	}
			    	$("#theatreHeader").html(show_plays);
				},
				error : function(data,status,xhr) {
					console.log(data);
				}
			});
		});	


$("#list_genres_button").click(
		function() {		
			$.ajax({
				method : 'GET',
				url : "/PA3/plays/genres",
				success : function(data,status,xhr) {
					console.log("Success!");
			    	var line_split = data.split("\\|");
			    	var show_genres = "<h2>Genres: </h2>  <br> <br>";
			    	for(i = 0; i < line_split.length; i++) {
			    		var genre = line_split[i];
			    		show_genres += "<a href=\"http://localhost:8080/PA3/REST/plays/" + genre + "\">" + genre + "</a> <br>";
			    	}
			    	$("#theatreHeader").html(show_genres);
				},
				error : function(data,status,xhr) {
					console.log(data);
				}
			});
		});	


$("#list_play_review_by_date").click(
		function() {		
			$.ajax({
				method : 'GET',
				url : "/PA3/plays/dates",
				success : function(data,status,xhr) {
					console.log("Success!");
			    	var line_split = data.split("\\|");
			    	var show_dates = "<h1>Dates: </h1>  <br> <br>";
			    	for(i = 0; i < line_split.length; i++) {
			    		var date_or_title = line_split[i];
			    		if(date_or_title.includes("/")) {
			    			show_dates += date_or_title + ": <br><br>";
			    		} else {
			    			show_dates += "<a href=\"http://localhost:8080/PA3/REST/plays/" + date_or_title + "\">" + date_or_title + "</a> <br>";
			    		}
			    	}
			    	$("#theatreHeader").html(show_dates);
				},
				error : function(data,status,xhr) {
					console.log(data);
				}
			});
		});	


$("#list_play_review_by_source").click(
		function() {		
			$.ajax({
				method : 'GET',
				url : "/PA3/plays/source",
				success : function(data,status,xhr) {
					console.log("Success!");
			    	$("#theatreHeader").html(data);
				},
				error : function(data,status,xhr) {
					console.log(data);
				}
			});
		});


$("#submit_search_by_name_button").click(
		function() {	
			var name = $("#name").val();
			name.replaceAll(" ", "_");
			$.ajax({
				method : 'GET',
				url : "/PA3/plays/" + name,
				success : function(data,status,xhr) {
					console.log("Success!");
			    	$("#theatreHeader").html(data);
				},
				error : function(data,status,xhr) {
					console.log(data);
				}
			});
		});
