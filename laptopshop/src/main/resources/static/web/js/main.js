
let dec = document.querySelector(".quantity .pro-qty .dec");
let inc = document.querySelector(".quantity .pro-qty .inc");

function decFunction(id){
	let textInput = document.querySelector(".quantity .pro-qty .id-"+id);
	let quanty = parseInt(textInput.value);
	textInput.value = quanty<=1 ? 1 : quanty-1;
}
function incFunction(id){
	let textInput = document.querySelector(".quantity .pro-qty .id-"+id);
	let quanty = parseInt(textInput.value);
	textInput.value = quanty+1;
}
$(function () {
 	const firstPath = location.pathname.split('/')[1];
 	console.log("ok")
	$("#inputSearch").autocomplete({
		source: `/${firstPath}/search`,
		create: function() {
			$(this).data('ui-autocomplete')._renderItem = function(ul, item) {
			var price = item.price;
			price = price.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		    	return $('<li>').append(
									`<a href="/${firstPath}/store/${item.id}" style="display:flex;justify-content: left;align-items: center;">
					        		 	<div style="width:33%">
					        		  		<img style="height: 100px;width: 100px;" class="icon" src="/${firstPath}/web/images/product/${item.thumbnail}" />
					        		  	</div>
					        		  	<div style="margin-left: 20px;font-size: 16px;width:33%">
					        		  		${item.name}
					        		  	</div>
					        		  	<div style="margin-left: 20px;font-size: 16px;width:33%">
					        		  		${price}
					        		  	</div>
					        		 </a>`
					        		  )
		        	  			.appendTo(ul);
		     };
		 }
	});
});
