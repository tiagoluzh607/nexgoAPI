package br.com.caelum.vraptor.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

@Entity
public class Carona extends Model {

	@ManyToOne
	private CaronaMotorista Motorista;
	
	@OneToMany(mappedBy="carona", fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<CaronaPassageiro> Passageiros;
	@Enumerated(EnumType.ORDINAL)
	private EStatusCarona Status;
	
	private double LatInicio;
	private double LatFim;
	private double LongInicio;
	private double LongFim;
	public CaronaMotorista getMotorista() {
		return Motorista;
	}
	public void setMotorista(CaronaMotorista motorista) {
		Motorista = motorista;
	}
	public List<CaronaPassageiro> getPassageiros() {
		return Passageiros;
	}
	public void setPassageiros(List<CaronaPassageiro> passageiros) {
		Passageiros = passageiros;
	}
	public EStatusCarona getStatus() {
		return Status;
	}
	public void setStatus(EStatusCarona status) {
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
