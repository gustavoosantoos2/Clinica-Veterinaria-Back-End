package model.services;

import java.util.List;

import model.dao.AnimalDAO;
import model.entites.Animal;

public class AnimalService {
	private AnimalDAO dao;
	
	public AnimalService(AnimalDAO dao) {
		this.dao = dao;
	}
	
	public List<Animal> listar() throws Exception {
		return dao.listar();
	}
	
	public Animal buscar(Long id) throws Exception {
		return dao.buscar(id);
	}
	
	public void salvar(Animal animal) throws Exception {
		dao.persistir(animal);
	}
	
	public void atualizar(Animal animal) throws Exception {
		dao.atualizar(animal);
	}
	
	public void remover(Long id) throws Exception {
		dao.remover(id);
	}
}
