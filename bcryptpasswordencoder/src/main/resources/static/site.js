$(document).ready(function() {

	// api to verify user
	$(document).on('submit', '#loginForm', function(e) {
		var user = {};
		user["userEmail"] = $("#email-verify").val();
		user["userPassword"] = $("#password-verify").val();
		e.preventDefault();
		$.ajax({
			type : 'POST',
			url : '/bcrypt/verify/',
			contentType : "application/json",
			data : JSON.stringify(user),
			cache : false,
			success : function(result) {
				alert(result);
			},
			error : function(e) {
				alert('Failed');
			}
		});
	});
	
	
	// api to add user
	$(document).on('submit', '#signupForm', function(e) {
		var user = {};
		user["userEmail"] = $("#email-add").val();
		user["userPassword"] = $("#password-add").val();
		e.preventDefault();
		$.ajax({
			type : 'POST',
			url : '/bcrypt/add/',
			contentType : "application/json",
			data : JSON.stringify(user),
			cache : false,
			success : function(result) {
				alert(result);
			},
			error : function(e) {
				alert('Failed');
			}
		});
	});

});