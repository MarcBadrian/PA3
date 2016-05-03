/*
 * COMP 6302 - Web Services / Internet
 * PA2: Hotel Reservation System - Web Interface
 * Marc Badrian and Hien Vo - Due 4/7/16
 * 
 */

$(document).ready(function(){
$("#create_customer_information").hide();
$("#reserve").hide();
$("#create_payment").hide();
$("#get_customer_id").hide();
$("#get_customer_name").hide();
$("#get_transactions").hide();

    $("#create_customer_button").click(function(){
        $("#create_customer_information").show();
    });
    $("#reserve_room_button").click(function(){
        $("#reserve").show();
    });
    $("#create_payment_button").click(function(){
        $("#create_payment").show();
    });
    $("#get_customer_id_button").click(function(){
        $("#get_customer_id").show();
    });
    $("#get_customer_name_button").click(function(){
        $("#get_customer_name").show();
    });
    $("#get_transactions_button").click(function(){
        $("#get_transactions").show();
    });

});

//function to Create a new Customer
$("#submit_customer_button").click(function(){
    $("#create_customer_information").hide();   
	console.log('CREATE CUSTOMER');
	var data = new Object();
	data.firstName = $("#first_name").val();
	data.lastName = $("#last_name").val();
	data.number = $("#phone_number").val(); 
	data.billingAddress = $("#billing_address").val();
	data.billingCity = $("#billing_city").val();
	data.billingState = $("#billing_state").val();
	data.billingZip = $("#billing_zip").val();
	data.checkIn = $("#checkin_date").val();
	data.checkOut = $("#checkout_date").val();
	$.ajax({
		type: 'POST',
        contentType: 'application/json',
        url: 'HotelReservationServlet?choice=1',
        data: JSON.stringify(data),
        success: function(data){
			$("#hotelHeader").html(data);
			$("#create_customer_information").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
			console.log(data);
			console.log(status);
			console.log(xhr.responseText);
		},
	});
	
});

//function to Reserve a Room
$("#submit_reserve_room_button").click(function(){
    $("#reserve").hide();
	console.log('RESERVE ROOM');
	var data = new Object();
	data.customer_id = $("#customer_id").val();
	data.room_number = $("#room_number").val();
	$.ajax({
		type: 'POST',
        url: 'HotelReservationServlet?choice=2',
        data: $.param({
        	customer_id: data.customer_id,
        	room_number: data.room_number
        	}),
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
			$("#reserve").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});


//function to Create a Payment
$("#submit_payment_button").click(function(){
	$("#create_payment").hide();
	console.log('CREATE PAYMENT');
	var data = new Object();
	data.custId = $("#trans_customer_id").val();
	data.roomNumber = $("#trans_room_number").val();
	data.amount = $("#amount").val();
	data.creditCardNum = $("#cc_number").val();
	data.expDate = $("#exp_date").val();
	$.ajax({
		type: 'POST',
        contentType: 'application/json',
        url: 'HotelReservationServlet?choice=3',
        data: JSON.stringify(data),
        success: function(data){
			$("#hotelHeader").html(data);
			$("#create_payment").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});

//function that gets the customer with the specified id and prints that customer’s information.
$("#submit_get_customer_id_button").click(function(){
	var id = $("#find_customer_id").val();
	$.ajax({
		url:"HotelReservationServlet?choice=4",
		method:"GET",
		data: $.param({
        	customer_id: id,
        	}),
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
			console.log(data);
			$("#get_customer_id").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});

//function that gets the customer with the specified name and prints that customer’s information.
$("#submit_get_customer_name_button").click(function(){
	$("#get_customer_name").hide();
	var data = new Object();
	data.choice = "3";
	data.first_name = $("#customer_first_name").val();
	data.last_name = $("#customer_last_name").val();
	$.ajax({
		url:"HotelReservationServlet?choice=5",
		method:"GET",
		data: $.param(data),
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
			$("#get_customer_name").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});

//function that gets all the current customers
$("#list_all_theatre_play_button").click(function(){
	$.get("PA3/plays/all", function(data){
		$("#theatreHeader").html(data);
	});
	
});

//function that gets all the transactions for a specified customer (by id)
$("#submit_get_transactions_button").click(function(){
	var id = $("#find_trans_id").val();
	$.ajax({
		url:"HotelReservationServlet?choice=7",
		method:"GET",
		data: $.param({
        	customer_id: id,
        	}),
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
			$("#get_transactions").hide();
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		}
	});
	
});

// function that gets all the current vacancies
$("#get_vacancies_button").click(function(){
	$("#get_vacancies").hide();
	$.ajax({
		url:"HotelReservationServlet?choice=8",
		method:"GET",
		dataType: "html",
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});

// function that gets all the current reservations
$("#get_reservation_button").click(function(){
	$("#get_reservation").hide();
	$.ajax({
		url:"HotelReservationServlet?choice=9",
		method:"GET",
		dataType: "html",
		success: function(data,status,xhr){
			$("#hotelHeader").html(data);
		},
		error: function(data,status,xhr){
			$("#hotelHeader").html("Error occurred.");
		},
	});
	
});