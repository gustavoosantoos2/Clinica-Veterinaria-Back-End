package services;

import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import model.dao.AbstractDAO;
import model.dao.AnimalDAO;
import model.entites.Animal;
import model.entites.Especie;

/**
 * Servlet implementation class AnimaisService
 */
@WebServlet("/AnimaisService")
public class AnimaisService extends AbstractService<Animal, Long> {
	private static final long serialVersionUID = 1L;

	@Override
	protected Long parsePrimaryKeyFromParams(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id"));
	}

	@Override
	protected Animal parseEntityFromParams(HttpServletRequest request) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Especie especie = new Especie();
		especie.setId(Long.parseLong(request.getParameter("especieid")));
		
		Animal animal = new Animal();
		animal.setNome(request.getParameter("nome"));
		animal.setNascimento(formatter.parse(request.getParameter("nascimento")));
		animal.setEspecie(especie);
		
		return animal;
	}

	@Override
	protected AbstractDAO<Animal, Long> createDao() {
		return new AnimalDAO();
	}

}
