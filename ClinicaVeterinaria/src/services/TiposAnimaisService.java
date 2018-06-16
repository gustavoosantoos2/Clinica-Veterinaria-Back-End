package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.TipoAnimalDAO;
import model.entites.TipoAnimal;

/**
 * Servlet implementation class TiposAnimaisService
 */
@WebServlet("/TiposAnimaisService")
public class TiposAnimaisService extends AbstractService<TipoAnimal, String> {
	private static final long serialVersionUID = 1L;

	@Override
	protected String parsePrimaryKeyFromParams(HttpServletRequest request) {
		return request.getParameter("acr");
	}

	@Override
	protected TipoAnimal parseEntityFromParams(HttpServletRequest request) throws Exception {
		TipoAnimal tipoAnimal = new TipoAnimal();
		tipoAnimal.setNome("nome");
		tipoAnimal.setAcronimo("acr");
		tipoAnimal.setDescricao("descricao");
		
		return tipoAnimal;
	}

	@Override
	protected AbstractDAO<TipoAnimal, String> createDao() {
		return new TipoAnimalDAO();
	}
}
