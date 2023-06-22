
$(".product-owl-carousel").owlCarousel({
	loop: true,
	margin: 10,
	nav: true,
	items: 4,
	dots: false,
	animateOut: 'fadeOut',
	animateIn: 'fadeIn',
	navText: ['<i class="fa fa-angle-left"></i>', '<i class="fa fa-angle-right"></i>'],
	smartSpeed: 1000,
	autoHeight: false,
	autoplay: true,
	responsive: {
		0: {
			items: 1,
		},
		360: {
			items: 2,
		},
		576: {
			items: 2,
		},
		992: {
			items: 4,
		},
		1200: {
			items: 4,
		}
	}
});