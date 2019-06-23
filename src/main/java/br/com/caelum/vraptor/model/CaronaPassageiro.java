package br.com.caelum.vraptor.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import br.com.caelum.vraptor.serialization.SkipSerialization;

@Entity
public class CaronaPassageiro extends Model {
	
	@ManyToOne
	private Usuario Passageiro;
	
	@SkipSerialization
	@ManyToOne
	private Carona carona;
	
	@Enumerated(EnumType.ORDINAL)
	private EStatusPassageiro Status;
	
	private double LatInicio;
	private double LatFim;
	private double LongInicio;
	private double LongFim;
	
	
	
	public Usuario getPassageiro() {
		return Passageiro;
	}
	public void setPassageiro(Usuario passageiro) {
		Passageiro = passageiro;
	}
	public EStatusPassageiro getStatus() {
		return Status;
	}
	public void setStatus(EStatusPassageiro status) {
		Status = status;
	}
	public double getLatInicio() {
		return LatInicio;
	}
	public void setLatInicio(double latInicio) {
		LatInicio = latInicio;
	}
	public double getLatFim() {
		return LatFim;
	}
	public void setLatFim(double latFim) {
		LatFim = latFim;
	}
	public double getLongInicio() {
		return LongInicio;
	}
	public void setLongInicio(double longInicio) {
		LongInicio = longInicio;
	}
	public double getLongFim() {
		return LongFim;
	}
	public void setLongFim(double longFim) {
		LongFim = longFim;
	}
	public Carona getCarona() {
		return carona;
	}
	public void setCarona(Carona carona) {
		this.carona = carona;
	}
	
	
	
	
}
