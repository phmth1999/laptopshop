
function chooseFile(fileInput) {
	if (fileInput.files && fileInput.files[0]) {
		let reader = new FileReader();
		reader.onload = function(e) {
			$("#image").attr("src", e.target.result);
		}
		reader.readAsDataURL(fileInput.files[0]);
	}
}