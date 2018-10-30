package br.com.joelamalio.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.joelamalio.brewer.service.CadastroCervejaService;
import br.com.joelamalio.brewer.storage.FotoStorage;

@Configuration
@ComponentScan(basePackageClasses = { CadastroCervejaService.class, FotoStorage.class })
public class ServiceConfig {
	
}
