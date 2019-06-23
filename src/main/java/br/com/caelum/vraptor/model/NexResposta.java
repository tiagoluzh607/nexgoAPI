package br.com.caelum.vraptor.model;

public class NexResposta {

	private boolean Sucesso = true;
	private String Mensagem;
	private Object Objeto;
	
	
	public boolean isSucesso() {
		return Sucesso;
	}
	public void setSucesso(boolean sucesso) {
		Sucesso = sucesso;
	}
	public String getMensagem() {
		return Mensagem;
	}
	public void setMensagem(String mensagem) {
		Mensagem = mensagem;
	}
	public Object getObjeto() {
		return Objeto;
	}
	public void setObjeto(Object objeto) {
		Objeto = objeto;
	}
	
	
}
