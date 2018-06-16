package services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AbstractDAO;
import model.utils.Serializer;

public abstract class Service<T, U> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected abstract AbstractDAO<T, U> createDao();
	protected abstract T parseEntityFromParams(HttpServletRequest request) throws Exception;
	protected abstract U parsePrimaryKeyFromParams(HttpServletRequest request);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AbstractDAO<T, U> dao = createDao();
			List<T> objects = dao.listar();
			String serializedObjects = new Serializer().serialize(objects);
			
			ok(response, serializedObjects);
		} catch (Exception e) {
			internalServerError(response, "Houve um problema ao listar os objetos.");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AbstractDAO<T, U> dao = createDao();
			T object = parseEntityFromParams(request);
			dao.persistir(object);
			ok(response);
		} catch (Exception e) {
			internalServerError(response, "Houve um problema ao criar o objeto.");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			U primaryKey = parsePrimaryKeyFromParams(request);
			if (primaryKey == null) {
				badRequest(response, "A chave primária deve ser informada.");
				return;
			}
			
			AbstractDAO<T, U> dao = createDao();
			if(dao.buscar(primaryKey) == null) {
				notFound(response, "Objeto não encontrado");
				return;
			}
			
			dao.removerComRelacionamentos(primaryKey);
			ok(response);
		} catch (Exception e) {
			internalServerError(response, "Houve um erro ao remover objeto.");
		}
	}
	
	private void ok(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	private void ok(HttpServletResponse response, String returnMessage) throws IOException {
		ok(response);
		response.getWriter().write(returnMessage);
	}
	
	private void badRequest(HttpServletResponse response, String errorMessage) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().write(errorMessage);
	}
	
	private void notFound(HttpServletResponse response, String errorMessage) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.getWriter().write(errorMessage);
	}
	
	private void internalServerError(HttpServletResponse response, String errorMessage) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(errorMessage);
	}
}
