package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.entites.TipoAnimal;

public class TipoAnimalDAO extends AbstractDAO<TipoAnimal, String> {

	@Override
	protected PreparedStatement criarStatementBuscar(Connection conexao, String id) throws Exception {
		String sql = "select acronimo, nome, descricao from tipo_animal where acronimo = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, id);

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementRemover(Connection conexao, String id) throws Exception {
		String sql = "delete from tipo_animal where acronimo = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, id);

		return ps;
	}

	@Override
	protected void carregarChavesGeradasNoObjeto(ResultSet generatedKeys, TipoAnimal objeto) throws Exception { }

	@Override
	protected PreparedStatement criarStatementPersistir(Connection conexao, TipoAnimal objeto) throws Exception {
		String sql = "insert into tipo_animal (acronimo, nome, descricao) values (?, ?, ?)";
		PreparedStatement ps = conexao.prepareStatement(sql);

		ps.setString(1, objeto.getAcronimo());
		ps.setString(2, objeto.getNome());
		ps.setString(3, objeto.getDescricao());

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementAtualizar(Connection conexao, TipoAnimal objeto) throws Exception {
		String sql = "update tipo_animal set nome = ?, descricao = ? where acronimo = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, objeto.getNome());
		ps.setString(2, objeto.getDescricao());
		ps.setString(3, objeto.getAcronimo());

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementListar(Connection conexao) throws Exception {
		String sql = "select acronimo, nome, descricao from tipo_animal";
		PreparedStatement ps = conexao.prepareStatement(sql);
		return ps;
	}

	@Override
	protected TipoAnimal parseObjeto(ResultSet rs) throws Exception {
		TipoAnimal tipoAnimal = new TipoAnimal();
		tipoAnimal.setAcronimo(rs.getString("acronimo"));
		tipoAnimal.setNome(rs.getString("nome"));
		tipoAnimal.setDescricao(rs.getString("descricao"));

		return tipoAnimal;
	}

	public void removerComRelacionamentos(Connection conexao, String acronimo) throws Exception {
		EspecieDAO especieDao = new EspecieDAO();
		PreparedStatement psEspecies = null;
		ResultSet rsEspecies = null;

		PreparedStatement psTipoAnimal = null;

		Exception ultimaExcecao = null;

		try {
			String queryEspecies = "select id from especie where tipo_animal_acronimo = ?";
			psEspecies = conexao.prepareStatement(queryEspecies);
			psEspecies.setString(1, acronimo);
			rsEspecies = psEspecies.executeQuery();

			ArrayList<Long> especiesIds = new ArrayList<>();
			while (rsEspecies.next()) {
				especiesIds.add(rsEspecies.getLong(1));
			}

			especiesIds.forEach(especieId -> {
				try {
					especieDao.removerComRelacionamentos(conexao, especieId);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

			String deleteTipoAnimal = "delete from tipo_animal where acronimo = ?";
			psTipoAnimal = conexao.prepareStatement(deleteTipoAnimal);
			psTipoAnimal.setString(1, acronimo);
			psTipoAnimal.execute();
			conexao.commit();
		} catch (Exception ex) {
			ultimaExcecao = ex;
		} finally {
			try {
				if (psEspecies != null && !psEspecies.isClosed())
					psEspecies.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}

			try {
				if (psTipoAnimal != null && !psTipoAnimal.isClosed())
					psTipoAnimal.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}

			try {
				if (conexao != null && !conexao.isClosed())
					conexao.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}
}
