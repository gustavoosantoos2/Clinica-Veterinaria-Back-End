package model.services;

import java.util.List;

import model.dao.EspecieDAO;
import model.entites.Especie;

public class EspecieService {
	private EspecieDAO dao;
	
	public EspecieService(EspecieDAO dao) {
		this.dao = dao;
	}
	
	public List<Especie> listar() throws Exception {
		return dao.listar();
	}
	
	public Especie buscar(Long id) throws Exception {
		return dao.buscar(id);
	}
	
	public void salvar(Especie especie) throws Exception {
		dao.persistir(especie);
	}
	
	public void atualizar(Especie especie) throws Exception {
		dao.atualizar(especie);
	}
	
	public void remover(Long id) throws Exception {
		dao.remover(id);
	}
}
