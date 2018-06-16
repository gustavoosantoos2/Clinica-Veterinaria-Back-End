package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.TipoAnimalDAO;
import model.entites.TipoAnimal;
import model.utils.Serializer;

/**
 * Servlet implementation class TiposAnimaisService
 */
@WebServlet("/TiposAnimaisService")
public class TiposAnimaisService extends Service<TipoAnimal, String> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractDAO<TipoAnimal, String> createDao() {
		return new TipoAnimalDAO();
	}
	
	@Override
	protected TipoAnimal parseEntityFromParams(HttpServletRequest request) throws Exception {
		Serializer serializer = new Serializer();
		return serializer.desserialize(request.getReader(), TipoAnimal.class);
	}

	@Override
	protected String parsePrimaryKeyFromParams(HttpServletRequest request) {
		return request.getParameter("acr");
	}

}
