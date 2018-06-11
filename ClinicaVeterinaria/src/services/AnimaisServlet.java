package services;

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
       
	//doGet será para listar e buscar por ID...
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action.equalsIgnoreCase("listar")) {
			try {
				listar(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (action.equalsIgnoreCase("buscar")) {
			String id = request.getParameter("id");
			Long parsedId = Long.parseLong(id);
			buscar(request, response, parsedId);
		}
	}

	//doPost será para inclusões, updates e deletes...
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("inclusao")) {
			Serializer serializer = new Serializer();
			Animal animal = serializer.desserialize(request.getReader(), Animal.class);
			AnimalService service = new AnimalService(new AnimalDAO());
			try {
				service.salvar(animal);
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			AnimalService service = new AnimalService(new AnimalDAO());
			List<Animal> animais = service.listar();
			
			Serializer serializer = new Serializer();
			String json = serializer.serialize(animais);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(json);
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
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(json);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
