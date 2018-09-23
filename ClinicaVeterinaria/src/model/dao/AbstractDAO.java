package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T, U> implements IGenericDAO<T, U> {

	@Override
	public List<T> listar() throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<T> retorno = new ArrayList<T>();
		Exception ultimaExcecao = null;

		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statement = this.criarStatementListar(con);
			rs = statement.executeQuery();

			while (rs.next()) {
				retorno.add(this.parseObjeto(rs));
			}

			return retorno;
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		
		throw ultimaExcecao;
	}

	@Override
	public void persistir(T objeto) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;

		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statement = this.criarStatementPersistir(con, objeto);
			statement.executeUpdate();

			con.commit();
			generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();

			this.carregarChavesGeradasNoObjeto(generatedKeys, objeto);
			return;
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}

	@Override
	public T buscar(U id) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		T retorno = null;
		Exception ultimaExcecao = null;

		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statement = this.criarStatementBuscar(con, id);
			rs = statement.executeQuery();

			if (rs.next())
				retorno = this.parseObjeto(rs);

			return retorno;
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}

		throw ultimaExcecao;
	}

	@Override
	public void atualizar(T objeto) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;

		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statement = this.criarStatementAtualizar(con, objeto);
			statement.executeUpdate();
			con.commit();
			return;
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			if (ultimaExcecao != null)
				throw ultimaExcecao;
	}

	@Override
	public void remover(U id) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;

		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statement = this.criarStatementRemover(con, id);
			statement.executeUpdate();
			con.commit();
			return;
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}
	
	//1616700 Diego Seronato - Alteracao do remover com relacionamentos, abstraido na classe AbstractDAO
	@Override
	public void removerComRelacionamentos(U acronimo) throws Exception{
		int statementsUpdated = 0;
		Connection con = null;
		List<PreparedStatement> statements = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;
		try {
			con = ConnectionSingleton.getInstance().getConnection();
			statements = this.criarStatementRemoverComRelacionamento(con, acronimo);
			try {
				for (PreparedStatement statement : statements) {
					statement.executeUpdate();
					statementsUpdated++;
				}
				if(statementsUpdated == statements.size()) {
					con.commit();
				}else {
					con.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ultimaExcecao = e;
				if (con != null)
					con.rollback();
			}

		} catch (Exception e) {
			ultimaExcecao = e;
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statements != null)
					for (PreparedStatement statement : statements) {
						try {
							statement.close();
						} catch (SQLException e) {
							ultimaExcecao = e;
						} catch (Exception e) {
							ultimaExcecao = e;
						}
					}

			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}
	

	protected abstract PreparedStatement criarStatementBuscar(Connection conexao, U id) throws Exception;

	protected abstract PreparedStatement criarStatementRemover(Connection conexao, U id) throws Exception;
	
	//1616700 Diego Seronato - Alteracao do remover com relacionamentos, abstraido na classe AbstractDAO
	protected abstract List<PreparedStatement> criarStatementRemoverComRelacionamento(Connection conexao, U id) throws Exception;

	protected abstract void carregarChavesGeradasNoObjeto(ResultSet generatedKeys, T objeto) throws Exception;

	protected abstract PreparedStatement criarStatementPersistir(Connection conexao, T objeto) throws Exception;

	protected abstract PreparedStatement criarStatementAtualizar(Connection conexao, T objeto) throws Exception;

	protected abstract PreparedStatement criarStatementListar(Connection conexao) throws Exception;

	protected abstract T parseObjeto(ResultSet rs) throws Exception;
}
