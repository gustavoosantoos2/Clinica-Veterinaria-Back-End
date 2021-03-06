package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.EspecieDAO;
import model.entites.Especie;
import model.utils.Serializer;

/**
 * Servlet implementation class EspeciesService
 */
@WebServlet("/EspeciesService")
public class EspeciesService extends Service<Especie, Long> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractDAO<Especie, Long> createDao() {
		return new EspecieDAO();
	}

	@Override
	protected Especie parseEntityFromParams(HttpServletRequest request) throws Exception {
		Serializer serializer = new Serializer();
		return serializer.desserialize(request.getReader(), Especie.class);	
	}
	
	@Override
	protected Long parsePrimaryKeyFromParams(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id"));
	}
}
