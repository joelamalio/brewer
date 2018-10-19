package br.com.joelamalio.brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.joelamalio.brewer.model.Venda;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailer;
	
	@Autowired
	private From from;
	
	@Async
	public void enviar(Venda venda) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from.getAddress());
		message.setTo(venda.getCliente().getEmail());
		message.setSubject("Venda Efetuada");
		message.setText("Obrigado, sua venda foi processada");
		
		mailer.send(message);
	}
	
}
