package br.com.joelamalio.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.joelamalio.brewer.thymeleaf.processor.ClassForErrorAttributePorcessor;
import br.com.joelamalio.brewer.thymeleaf.processor.MenuAttributePorcessor;
import br.com.joelamalio.brewer.thymeleaf.processor.MessageElementTagProcessor;
import br.com.joelamalio.brewer.thymeleaf.processor.OrderElementTagProcessor;
import br.com.joelamalio.brewer.thymeleaf.processor.PaginationElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("JA Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<IProcessor>();
		processadores.add(new ClassForErrorAttributePorcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributePorcessor(dialectPrefix));
		return processadores;
	}

}