package br.com.joelamalio.brewer.repository.filter;

import br.com.joelamalio.brewer.model.Estado;

public class CidadeFilter {

	private Estado Estado;
	private String nome;

	public Estado getEstado() {
		return Estado;
	}
	
	public void setEstado(Estado estado) {
		Estado = estado;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
