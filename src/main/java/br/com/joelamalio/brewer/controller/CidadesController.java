package br.com.joelamalio.brewer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.joelamalio.brewer.model.Cidade;
import br.com.joelamalio.brewer.repository.Cidades;
import br.com.joelamalio.brewer.repository.Estados;
import br.com.joelamalio.brewer.service.CadastroCidadeService;
import br.com.joelamalio.brewer.service.exception.NomeCidadeJaCadastradoException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private Cidades cidades;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {			
			return nova(cidade);
		}
		
		try {
			cadastroCidadeService.salvar(cidade);
		} catch (NomeCidadeJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(cidade);
		}
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/nova");
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {	}
		return cidades.findByEstadoCodigo(codigoEstado);
	}
}
