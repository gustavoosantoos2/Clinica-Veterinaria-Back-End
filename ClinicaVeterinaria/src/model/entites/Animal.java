package model;

import java.time.DateTimeException;
import java.util.Date;

import model.Especie;
import model.utils.DateUtils;
import model.utils.StringUtils;

public class Animal {
	private Long id;
	private String nome;
	private Date nascimento;
	private Especie especie;

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		if (especie == null)
			throw new IllegalArgumentException("Espécie não deve ser nula");
		this.especie = especie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if (StringUtils.isNullOrWhiteSpace(nome))
			throw new Exception("O nome não pode ser vazio.");
		if (nome.length() > 50)
			throw new Exception("O nome não pode ter mais de 50 caracteres.");
		this.nome = nome;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) throws DateTimeException {
		if (DateUtils.isAfterToday(nascimento))
			throw new DateTimeException("A data/hora de nascimento do animal não pode ser maior que a atual");
		this.nascimento = nascimento;
	}
}