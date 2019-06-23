package br.com.caelum.vraptor.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class CaronaMotorista extends Model{
	
	@ManyToOne
	private Usuario Motorista;
	
	@Enumerated(EnumType.ORDINAL)
	private EStatusMotorista Status;
	
	private double LatInicio;
	private double LatFim;
	private double LongInicio;
	private double LongFim;
	
	public Usuario getMotorista() {
		return Motorista;
	}
	public void setMotorista(Usuario motorista) {
		Motorista = motorista;
	}
	public EStatusMotorista getStatus() {
		return Status;
	}
	public void setStatus(EStatusMotorista status) {
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
	
	
}
