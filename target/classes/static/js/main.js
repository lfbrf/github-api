function enviarBusca(){
	
	var url = window.location.href;
	
	if ($("#query").val() == ""){
		alert("É preciso informar um valor para busca");
		
	}
	else{
		$("#query").val($("#query").val().replace(/\s/g, ""));
		$("#url").val(url);
		$('#btBuscarApi').trigger('click'); 
	}
		
	
}
function sendSave(id) {
	alert(id);
	$("#idSalvar").val(id);
	$('#saveRepo').trigger('click');
}
$(function() {
	if (window.location.href.startsWith("http://localhost:8080/listarbanco")){
		$("#listarBancoAtivo").show();
		$("#listarBanco").hide();
		$("#listarApi").show();
		$("#listarApiAtivo").hide();
	}
	else{
		$("#listarBancoAtivo").hide();
		$("#listarBanco").show();
		$("#listarApi").hide();
		$("#listarApiAtivo").show();
	}
	$('#selectLinguagens').editableSelect();
	$('tr.lista_repos td.rp').click(function() {
		//$(this).nextUntil('showhide').slideToggle(100);

		$(this).parent().next('tr.showhide').slideToggle(100, function(){  
			$(this).closest('tr').next('tr').slideToggle(100);
		} );
		
	});

});