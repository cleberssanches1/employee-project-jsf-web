/**
 * JavaScript responsável pela Lógica e Organização das Mascaras
 */

$(document).ready(function(){
		
	//Mascaras Gerais
	$('.mascaraNumeroRequisicao').mask('9999999999', {selectOnFocus: true});
	$('.mascaraNumeroCentesimal').mask('999', {selectOnFocus: true});
	$('.mascaraNumeroMilhar').mask('###.###.###', {selectOnFocus: true, reverse:true});
	$('.mascaraNumeroContrato').mask('9999999999', {selectOnFocus: true});
	$('.mascaraDataHora').mask('00/00/0000 00:00:00', {selectOnFocus: true});
	$('.mascaraData').mask('00/00/0000', {selectOnFocus: true});
	$('.mascaraPercentual').mask("000,00", {selectOnFocus: true, reverse:true});
	$('.mascaraMonetaria').mask('0.000.000,00', {selectOnFocus: true, reverse:true});
	$('.mascaraTelefone').mask('(ZZ) ZZZZZZZZZ', {
	    translation: {
	        'Z': {
	          pattern: /[0-9]/, optional: true
	        }
	      }
	    });
	
	$(document).on("keyup", ".mascaraValor", function(event) {
		var valor = $(this).val();
		valor = valor.replace(/\D/g, "");

		var tamanho = valor.length;

		if (tamanho > 11) {
			$(this).val('');
			return false;
		}

		$(this).priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.'
		});
	});
	
	$(document).on("keyup", ".mascaraDocumento", function(event) {
		var retorno;
		var valor = $(this).val();
		valor = valor.replace(/\D/g, "");

		var tamanho = valor.length;

		if (tamanho > 14) {
			$(this).val('');
			return false;
		}

		if (tamanho <= 11) {
			retorno = cpf(valor);
			$(this).val(retorno);
		}

		if (tamanho > 11) {
			retorno = cnpj(valor);
			$(this).val(retorno);
		}
	});
	
	$(document).on("keyup", ".mascaraNumero", function(event) {
		var valor = $(this).val();
		valor = valor.replace(/\D/g, "");
		$(this).val(valor);
	});
	
	 PrimeFaces.locales['pt'] = {
             closeText: 'Fechar',
             prevText: 'Anterior',
             nextText: 'Pr&oacute;ximo',
             currentText: 'Começo',
             monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
             monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
             dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'S&aacute;bado'],
             dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
             dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],
             weekHeader: 'Semana',
             firstDay: 0,
             isRTL: false,
             showMonthAfterYear: false,
             yearSuffix: '',
             timeOnlyTitle: 'Somente Horas',
             timeText: 'Tempo',
             hourText: 'Hora',
             minuteText: 'Minuto',
             secondText: 'Segundo',
             ampm: false,
             month: 'M&ecirc;s',
             week: 'Semana',
             day: 'Dia',
             allDayText: 'Todo o Dia'
         };
});


function bloqueiaEnter(codigoTecla) {
	if (codigoTecla == 13) { return false; } else {return true;}
}