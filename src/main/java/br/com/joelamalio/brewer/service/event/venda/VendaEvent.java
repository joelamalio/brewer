package br.com.joelamalio.brewer.service.event.venda;

import br.com.joelamalio.brewer.model.Venda;

public class VendaEvent {

	private Venda venda;

	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}

}
