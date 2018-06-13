package services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AnimalDAO;
import model.entites.Animal;
import model.services.AnimalService;
import model.utils.Serializer;

/**
 * Servlet implementation class AnimaisServlet
 */
@WebServlet("/AnimaisServlet")
public class AnimaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// doGet será para listar e buscar por ID...
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("listar")) {
			try {
				listar(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equalsIgnoreCase("buscar")) {
			String id = request.getParameter("id");
			Long parsedId = Long.parseLong(id);
			buscar(request, response, parsedId);
		}
	}

	// doPost será para inclusões, updates e deletes...
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("incluir")) {
			Incluir(request, response);
		} else if (action.equalsIgnoreCase("atualizar")) {
			Atualizar(request, response);
		} else if (action.equalsIgnoreCase("deletar")) {
			Remover(request, response);
		}

	}

	private void Remover(HttpServletRequest request, HttpServletResponse response) {
		Long id = null;
		
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		AnimalService service = new AnimalService(new AnimalDAO());
		Animal a = null;
		try {
			a = service.buscar(id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (a != null && a.getId() >= 0) {
			try {
				service.remover(a.getId());
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void Atualizar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Animal animal = desserializeAnimalFromRequestBody(request);
		AnimalService service = new AnimalService(new AnimalDAO());
		try {
			service.atualizar(animal);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			AnimalService service = new AnimalService(new AnimalDAO());
			List<Animal> animais = service.listar();

			Serializer serializer = new Serializer();
			String json = serializer.serialize(animais);

			response.getWriter().write(json);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw e;
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			AnimalService service = new AnimalService(new AnimalDAO());
			Animal animal = service.buscar(id);

			Serializer serializer = new Serializer();
			String json = serializer.serialize(animal);

			response.getWriter().write(json);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void Incluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Animal animal = desserializeAnimalFromRequestBody(request);
		AnimalService service = new AnimalService(new AnimalDAO());
		try {
			service.salvar(animal);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private Animal desserializeAnimalFromRequestBody(HttpServletRequest request) throws IOException {
		Serializer serializer = new Serializer();
		return serializer.desserialize(request.getReader(), Animal.class);
	}
}
