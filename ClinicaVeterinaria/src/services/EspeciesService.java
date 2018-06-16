package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.EspecieDAO;
import model.entites.Especie;
import model.entites.TipoAnimal;

/**
 * Servlet implementation class EspeciesService
 */
@WebServlet("/EspeciesService")
public class EspeciesService extends AbstractService<Especie, Long> {
	private static final long serialVersionUID = 1L;

	@Override
	protected Long parsePrimaryKeyFromParams(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id"));
	}

	@Override
	protected Especie parseEntityFromParams(HttpServletRequest request) throws Exception {
		TipoAnimal tipoAnimal = new TipoAnimal();
		tipoAnimal.setAcronimo(request.getParameter("acr"));
		
		Especie especie = new Especie();
		especie.setNome("nome");
		especie.setDescricao("descricao");
		especie.setTipoAnimal(tipoAnimal);
		
		return especie;
	}

	@Override
	protected AbstractDAO<Especie, Long> createDao() {
		return new EspecieDAO();
	}
}
