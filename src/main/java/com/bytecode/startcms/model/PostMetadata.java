package com.bytecode.startcms.model;

public class PostMetadata {
	private long IdPostMetadata;
	private String Clave;
	private String Valor;
	private String Tipo;
	private long IdPost;
	
	public long getIdPostMetadata() {
		return IdPostMetadata;
	}
	public void setIdPostMetadata(long idPostMetadata) {
		IdPostMetadata = idPostMetadata;
	}
	public String getClave() {
		return Clave;
	}
	public void setClave(String clave) {
		Clave = clave;
	}
	public String getValor() {
		return Valor;
	}
	public void setValor(String valor) {
		Valor = valor;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public long getIdPost() {
		return IdPost;
	}
	public void setIdPost(long idPost) {
		IdPost = idPost;
	}
}
