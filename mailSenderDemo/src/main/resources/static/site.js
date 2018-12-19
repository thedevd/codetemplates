$(document).ready(function() {

	// ajax call to send email
	$(document).on('submit', '#send-email-form', function(e) {
		
		$("#sendemailsubmitbtn").text("Sending Mail....");
		$("#sendemailsubmitbtn").prop("disabled", true);
		
		var email = {};
		email["toEmail"] = $("#inputmail").val();
		email["emailSubjectLine"] = $("#inputsubjectline").val();
		email["emailBody"] = $("#inputemailbody").val();
		e.preventDefault();
		$.ajax({
			type : 'POST',
			url : '/javamailservice/sendMail/',
			contentType : "application/json",
			data : JSON.stringify(email),
			cache : false,
			success : function(result) {
				$("#sendemailsubmitbtn").text("Send Mail");
				$("#sendemailsubmitbtn").prop("disabled", false);
				alert(result);
			},
			error : function(e) {
				$("#sendemailsubmitbtn").text("Send Mail");
				$("#sendemailsubmitbtn").prop("disabled", false);
				alert('Failed');
			}
		});
	});

});