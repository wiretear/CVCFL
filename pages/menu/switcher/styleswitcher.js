$(document).ready(function($) {

	
	$('#style-selector').animate({
		left: '-205px'
	});

	$('#style-switcher a.close').click(function(e){
		e.preventDefault();
		var div = $('#style-switcher');
		if (div.css('left') === '-235px') {
			$('#style-switcher').animate({
				left: '0'
			});
			$(this).removeClass('icon-chevron-left');
			$(this).addClass('icon-chevron-right');
		} else {
			$('#style-switcher').animate({
				left: '-235px'
			});
			$(this).removeClass('icon-chevron-right');
			$(this).addClass('icon-chevron-left');
		}
	})
	
})