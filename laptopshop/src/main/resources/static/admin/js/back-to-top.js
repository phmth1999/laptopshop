
$(window).scroll(function() {
	if ($(this).scrollTop()) {
		$('.backtop').fadeIn();
	} else {
		$('.backtop').fadeOut();
	}
});

$('.backtop').click(function() {
	$('html , body').animate({ scrollTop: 0 }, 500);
});