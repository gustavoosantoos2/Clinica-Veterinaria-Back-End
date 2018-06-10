package model.entites;

import model.utils.StringUtils;

public class Especie {
	private Long id;
	private String nome;
	private String descricao;
	private TipoAnimal tipoAnimal;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		if (StringUtils.isNullOrWhiteSpace(nome))
			throw new IllegalArgumentException("Nome não deve ser vazio");
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		if (StringUtils.isNullOrWhiteSpace(descricao))
			throw new IllegalArgumentException("Descrição não deve ser vazia.");
		this.descricao = descricao;
	}
	
	public TipoAnimal getTipoAnimal() {
		return tipoAnimal;
	}
	
	public void setTipoAnimal(TipoAnimal tipoAnimal) {
		if (tipoAnimal == null)
			throw new IllegalArgumentException("O tipo de animal não deve ser nulo.");
		this.tipoAnimal = tipoAnimal;
	}
}