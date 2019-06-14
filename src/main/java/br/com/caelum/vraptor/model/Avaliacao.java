package br.com.caelum.vraptor.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Avaliacao extends Model {
	
	@ManyToOne
	private Aluno aluno;
	private double grauA;
	private double grauB;
	private double grauC;
	
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public double getGrauA() {
		return grauA;
	}
	public void setGrauA(double grauA) {
		this.grauA = grauA;
	}
	public double getGrauB() {
		return grauB;
	}
	public void setGrauB(double grauB) {
		this.grauB = grauB;
	}
	public double getGrauC() {
		return grauC;
	}
	public void setGrauC(double grauC) {
		this.grauC = grauC;
	}
	
	
}
