package br.com.caelum.vraptor.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Pessoa extends Model {

	protected String nome;
	protected String email;
	protected String rg;
	protected String telefone;
	protected ESexo sexo;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public ESexo getSexo() {
		return sexo;
	}
	public void setSexo(ESexo sexo) {
		this.sexo = sexo;
	}
	
	
}
