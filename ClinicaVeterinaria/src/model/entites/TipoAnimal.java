package model;

import model.utils.StringUtils;

public class TipoAnimal {
	private String acronimo;
	private String nome;
	private String descricao;
	
	public String getAcronimo() {
		return acronimo;
	}
	
	public void setAcronimo(String acronimo) {
		if (StringUtils.isNullOrWhiteSpace(acronimo))
			throw new IllegalArgumentException("Acronimo n�o deve ser vazio.");
		this.acronimo = acronimo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		if (StringUtils.isNullOrWhiteSpace(nome))
			throw new IllegalArgumentException("Nome n�o deve ser vazio.");
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		if (StringUtils.isNullOrWhiteSpace(descricao))
			throw new IllegalArgumentException("Descri��o n�o deve ser vazia.");
		this.descricao = descricao;
	}
}
