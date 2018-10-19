package br.com.joelamalio.brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class From {

	@Autowired
	private Environment env;
	
	private String address;
	
	public String getAddress() {
		if (address == null) {
			this.address = env.getProperty("email.from");
		}
		return address;
	}

}
