jQuery.noConflict();
(function($){
	//csrf token 
	let $token = $("meta[name='_csrf']").attr('content');
	//enabling tootips
	$("[data-toggle='tooltip']").tooltip();
	//fade in and out last navbar link (for lang) 
	$("li.dropdown").hover(function(){
		$(this).find("ul.dropdown-menu").stop(true, true).delay(100).fadeIn(120);
	}, function(){
			$(this).find("ul.dropdown-menu").stop(true, true).delay(100).fadeOut(120);
	});
	//reference links in square brackets [x]
	$("a[href^='#science']").on('click', function(){
		$("a#national").css("text-decoration", "none");
		return $("a#science").css("text-decoration", "underline");
	});
	$("a[href='#wiki']").on('click', function(){
		$("a#wikidna").css("text-decoration", "none");
		return $("a#wiki").css("text-decoration", "underline");
	});
	$("a[href='#wikidna']").on('click', function(){
		$("a#wiki").css("text-decoration", "none");
		return $("a#wikidna").css("text-decoration", "underline");
	});
	$("a[href='#technics']").on('click', function(){
		return $("a#technics").css("text-decoration", "underline");
	});
	
	$("a[href^='#national']").on('click', function(){
		$("a#science").css("text-decoration", "none");
		return $("a#national").css("text-decoration", "underline");
	});
	$("a[href^='#extraction']").on('click', function(){
		$("a#sd").css("text-decoration", "none");
		return $("a#extraction").css("text-decoration", "underline");
	});
	$("a[href^='#banding']").on('click', function(){
		return $("a#banding").css("text-decoration", "underline");
	});
	$("a[href^='#sd']").on('click', function(){
		$("a#extraction").css("text-decoration", "none");
		return $("a#sd").css("text-decoration", "underline");
	});
	$("a[href^='#fish']").on('click', function(){
		$("a#nick").css("text-decoration", "none");
		$("a#technics").css("text-decoration", "none");
		return $("a#fish").css("text-decoration", "underline");
	});
	$("a[href^='#nick']").on('click', function(){
		$("a#fish").css("text-decoration", "none");
		$("a#technics").css("text-decoration", "none");
		return $("a#nick").css("text-decoration", "underline");
	});
	$("a[href^='#pcr']").on('click', () => {
		return $("a#pcr").css("text-decoration", "underline");
	});
	//static url for ajax call
	let name = window.location.pathname.split("/")[1];
	let url = window.location.origin + "/" +  name + "/subscribeEmail";
	//ajax request
	$("#lower_form").on('submit', function(e){
		e.preventDefault();
		$("#success.success").text("");
		let $this = $("input#email");
		let json = {
				"email" : $this.val()
		};
		$.ajax({
			method: "POST",
			url: '/subscribeEmail', //url - for production,
			headers: {
				'Accept': "application/json",
				'Content-Type': "application/json",
				'X-CSRF-TOKEN' : $token
			},
			data: JSON.stringify(json),
			dataType: "json",			
			success: function(data){
						if(data.status === 'success'){
							$("#success.success", "#lower_form").text(data.message);
							$("input#email").on('focus', function(){
								$(this).next("#error.error").text("");
								$(this).val("");
							});
							$this.val("");
						}
					},
			error: function(data){
				if(data.status === 400){
					$this.next("#error.error").text(data.responseJSON.message);
				}
			}
		});
	});
	//search input box handling
	let $input = $("input[type='search']");
	$input.on('focus', function(){
		$(this).removeAttr('placeholder');
	});
	$("#upper_form").on('submit', function(e){	
		if($input.val() === '' || $input.val().match("^\\s+$") || $input.val().match("[^a-zA-Z0-9'\"\\sžćčđš]+")){
			e.preventDefault();	
			$input.addClass('upperborder').focusout(function(){
				$(this).removeClass('upperborder');
			});
		}else {
			$(this).unbind('submit').submit();
		}
	});
	//newsletter input box handling
	$("input#email").on('focus', function(){
		$(this).next("#error.error").text("");
		$(this).removeAttr('placeholder');
		$(this).next().next("#success.success").text("");
	});
	//scroll to top animation
	$("a[href='#top']").on('click', function(){
		$("html, body").animate({ scrollTop : 0 }, 'slow');
	});
	// fading h1 and an image
	$("#image, h1").hide().fadeIn();
})(jQuery);
