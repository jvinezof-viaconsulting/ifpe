var btnClose = document.getElementById("erroBtnDelete");
	if (btnClose != null) {
	btnClose.addEventListener("click", function() {
		document.getElementById("erroDiv").style.display = "none";
	});
}