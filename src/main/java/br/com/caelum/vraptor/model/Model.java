package br.com.caelum.vraptor.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class Model {

	@Id
	@Type(type="java.lang.String")
	@GenericGenerator(name = "uuid-gen", strategy = "uuid")
	@GeneratedValue(generator = "uuid-gen")
	protected String ID;
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	
	
	
}
