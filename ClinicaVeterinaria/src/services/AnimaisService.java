package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.AnimalDAO;
import model.entites.Animal;
import model.utils.Serializer;

/**
 * Servlet implementation class AnimaisService
 */
@WebServlet("/AnimaisService")
public class AnimaisService extends Service<Animal, Long> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractDAO<Animal, Long> createDao() {
		return new AnimalDAO();
	}

	@Override
	protected Animal parseEntityFromParams(HttpServletRequest request) throws Exception {
		Serializer serializer = new Serializer();
		return serializer.desserialize(request.getReader(), Animal.class);
	}
	
	@Override
	protected Long parsePrimaryKeyFromParams(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id"));
	}
}
