package br.com.joelamalio.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.model.Usuario;
import br.com.joelamalio.brewer.repository.Usuarios;
import br.com.joelamalio.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.joelamalio.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioOptional = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		if (usuarioOptional.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obirgatória para novo usuário");
		}
		
		if (usuario.isNovo()) {
			String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);
			usuario.setConfirmacaoSenha(senhaCriptografada);
		}
 		
		return usuarios.saveAndFlush(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuarios);
	}

}
