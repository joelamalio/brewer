package br.com.joelamalio.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.joelamalio.brewer.repository.Cervejas;
import br.com.joelamalio.brewer.repository.Clientes;
import br.com.joelamalio.brewer.repository.Vendas;

@Controller
public class DashboardController {
	
	@Autowired
	private Vendas vendas;

	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Cervejas cervejas;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("vendasNoAno", vendas.valorTotalNoAno());
		mv.addObject("vendasNoMes", vendas.valorTotalNoMes());
		mv.addObject("ticketMedio", vendas.valorTicketMedioNoAno());
		
		mv.addObject("totalClientes", clientes.count());
		mv.addObject("estoque", cervejas.obterEstoque());
		
		return mv;
	}
	
}
