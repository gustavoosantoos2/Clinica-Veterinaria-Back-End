package model.services;

import java.util.List;
import model.dao.TipoAnimalDAO;
import model.entites.TipoAnimal;

public class TipoAnimalService {
	private TipoAnimalDAO dao;
	
	public TipoAnimalService(TipoAnimalDAO dao) {
		this.dao = dao;
	}
	
	public List<TipoAnimal> listar() throws Exception {
		return dao.listar();
	}
	
	public TipoAnimal buscar(Long id) throws Exception {
		return dao.buscar(id);
	}
	
	public void salvar(TipoAnimal tipoAnimal) throws Exception {
		dao.persistir(tipoAnimal);
	}
	
	public void atualizar(TipoAnimal tipoAnimal) throws Exception {
		dao.atualizar(tipoAnimal);
	}
	
	public void remover(Long id) throws Exception {
		dao.remover(id);
	}
}
