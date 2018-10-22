package br.com.joelamalio.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.model.Usuario;
import br.com.joelamalio.brewer.repository.Usuarios;
import br.com.joelamalio.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.joelamalio.brewer.service.exception.ImpossivelExcluirEntidadeException;
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
		if (usuarioOptional.isPresent() && !usuarioOptional.get().equals(usuario)) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obirgatória para novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioOptional.get().getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioOptional.get().getAtivo());
		}
 		
		return usuarios.saveAndFlush(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuarios);
	}
	
	@Transactional
	public void excluir(Usuario usuario) {
		try {
			usuarios.delete(usuario);
			usuarios.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar usuario, pois o registro está vinculado a outro no banco de dados.");
		}
	}

}
