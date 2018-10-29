package br.com.joelamalio.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	public String salvar(MultipartFile[] files);

	public byte[] recuperar(String nome);
	
	public byte[] recuperarThumbnail(String fotoCerveja);

	public void excluir(String foto);

	public String getUrl(String foto);

}
