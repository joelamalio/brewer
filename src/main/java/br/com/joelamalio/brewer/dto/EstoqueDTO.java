package br.com.joelamalio.brewer.dto;

import java.math.BigDecimal;

public class EstoqueDTO {

	private BigDecimal valorItens;
	private Long quantidadeItens;

	public EstoqueDTO() {
	}

	public EstoqueDTO(BigDecimal valorItens, Long quantidadeItens) {
		this.valorItens = valorItens;
		this.quantidadeItens = quantidadeItens;
	}

	public BigDecimal getValorItens() {
		return valorItens;
	}

	public void setValorItens(BigDecimal valorItens) {
		this.valorItens = valorItens;
	}

	public Long getQuantidadeItens() {
		return quantidadeItens;
	}

	public void setQuantidadeItens(Long quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}

}
