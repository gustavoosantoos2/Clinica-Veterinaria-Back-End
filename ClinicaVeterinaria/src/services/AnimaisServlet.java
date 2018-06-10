package services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Animal;
import model.dao.AnimalDAO;
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
		
		if (action.equals("listar")) {
			try {
				listar(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (action.equals("buscar")) {
			String id = request.getParameter("id");
			buscar(request, response, Long.getLong(id));
		}
	}

	//doPost será para inclusões, updates e deletes...
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			AnimalService service = new AnimalService(new AnimalDAO());
			List<Animal> animais = service.listar();
			
			Serializer<Animal> serializer = new Serializer<>();
			String json = serializer.serialize(animais);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(json);
		} catch (Exception e) {
			throw e;
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private void buscar(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			AnimalService service = new AnimalService(new AnimalDAO());
			Animal animal = service.buscar(id);
			
			Serializer<Animal> serializer = new Serializer<>();
			String json = serializer.serialize(animal);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(json);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
