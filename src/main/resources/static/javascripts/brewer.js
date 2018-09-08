var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {

	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function() {
		decimal.maskMoney({ decimal: ',', thousands: '.'});
		plain.maskMoney({ precision : 0, thousands: '.' });
	}
	
	return MaskMoney;
}()); // para executar a função é necessário colocar o () no final

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
});