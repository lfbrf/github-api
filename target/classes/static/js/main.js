function enviarBusca(){
	
	var url = window.location.href;
	
	if ($("#query").val() == ""){
		alert("É preciso informar um valor para busca");
		
	} 
	else{
		$("#query").val($("#query").val().replace(/\s/g, ""));
		$("#url").val(url);
		$("#currentPage").val(1);
		$('#btBuscarApi').trigger('click'); 
	}
		
	 
}

function esconder(id){
	$("#"+id).fadeIn('slow', function(){
        $("#"+id).delay(400).fadeOut(); 
     });
}

function avancar(){
	$("#query").val($("#query").val().replace(/\s/g, ""));
	$("#url").val(url);
	$("#currentPage").val($("#cp").val());
	$('#btBuscarApi').trigger('click'); 
}

function voltar(){
	$("#query").val($("#query").val().replace(/\s/g, ""));
	$("#url").val(url);
	var pg = $("#cp").val() - 1;
	$("#currentPage").val($("#cp").val());
	$('#btBuscarApi').trigger('click'); 
}

function sendSave(id) {
	$("#currentPageSave").val($("#cp").val());
	$("#idSalvar").val(id);
	$('#saveRepo').trigger('click');
}

function sendDelete(id) {

	//$("#idDeletar").val(id);
	//$('#deleteRepo').trigger('click');
	if (confirm("Deseja Realmente deletar o repositório?")) {
		$("#idDeletar").val(id);
		$('#deleteRepo').trigger('click');
	} else {
	  
	}
}
$(function() {
	$("#valPg").text($("#cp").val());
	$("#retornoSaveX").click(function(){
		esconder("retornoSave");
	});
	if (window.location.href.startsWith("http://localhost:8080/listarbanco") ||
			window.location.href.startsWith("https://localhost:8080/listarbanco")||
			window.location.href.startsWith("localhost:8080/listarbanco")){
		$("#listarBancoAtivo").show();
		$("#listarBanco").hide();
		$("#listarApi").show();
		$("#listarApiAtivo").hide();
		$("#lapi").hide();
		$("#lbd").show();
		
	}
	else{
		$("#listarBancoAtivo").hide();
		$("#listarBanco").show();
		$("#listarApi").hide();
		$("#listarApiAtivo").show();
		$("#lapi").show();
		$("#lbd").hide();
	}
	$('#selectLinguagens').editableSelect();
	$('tr.lista_repos td.rp').click(function() {
		//$(this).nextUntil('showhide').slideToggle(100);

		$(this).parent().next('tr.showhide').slideToggle(100, function(){  
			$(this).closest('tr').next('tr').slideToggle(100);
		} );
		
	});

});