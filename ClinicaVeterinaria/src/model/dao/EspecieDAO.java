package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Especie;

public class EspecieDAO extends AbstractDAO<Especie, Long> {

	@Override
	protected void carregarChavesGeradasNoObjeto(ResultSet generatedKeys, Especie objeto) throws Exception {
		objeto.setId(generatedKeys.getLong(1));
	}

	@Override
	protected PreparedStatement criarStatementPersistir(Connection conexao, Especie objeto) throws Exception {
		String sql = "insert into especie (nome, descricao, tipo_animal_acronimo) values (?, ?, ?)";

		PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNome());
		ps.setString(2, objeto.getDescricao());
		ps.setString(3, objeto.getTipoAnimal().getAcronimo());

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementListar(Connection conexao) throws Exception {
		return conexao.prepareStatement("select id,nome,descricao from especie");

	}

	@Override
	protected Especie parseObjeto(ResultSet rs) throws Exception {
		Especie e = new Especie();
		e.setId(rs.getLong(1));
		e.setNome(rs.getString(2));
		e.setDescricao(rs.getString(3));

		return e;
	}

	@Override
	protected PreparedStatement criarStatementBuscar(Connection conexao, Long id) throws Exception {
		String sql = "select nome descricao, tipo_animal_acronimo from especie where id = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, id);

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementAtualizar(Connection conexao, Especie objeto) throws Exception {
		String sql = "update especie set nome = ?, descricao = ?, tipo_animal_acronimo = ? where id = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, objeto.getNome());
		ps.setString(2, objeto.getDescricao());
		ps.setString(3, objeto.getTipoAnimal().getAcronimo());
		ps.setLong(4, objeto.getId());

		return ps;
	}

	@Override
	protected PreparedStatement criarStatementRemover(Connection conexao, Long id) throws Exception {
		String sql = "delete from especie where id = ?";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setLong(1, id);

		return ps;
	}

	public void removerComRelacionamentos(Connection conexao, Long id) throws Exception {
		PreparedStatement psAnimais = null;
		PreparedStatement psEspecie = null;

		Exception ultimaExcecao = null;

		try {
			String sqlRemoveAnimais = "delete from animal where especie_id = ?";
			psAnimais = conexao.prepareStatement(sqlRemoveAnimais);
			psAnimais.setLong(1, id);

			String sqlRemoveEspecie = "delete from tipo_animal where id = ?";
			psEspecie = conexao.prepareStatement(sqlRemoveEspecie);
			psEspecie.setLong(1, id);

			psAnimais.executeUpdate();
			psEspecie.executeUpdate();
			conexao.commit();
		} catch (Exception ex) {
			ultimaExcecao = ex;
		} finally {
			if (conexao != null && !conexao.isClosed())
				conexao.rollback();
			try {
				if (psEspecie != null && !psEspecie.isClosed())
					psEspecie.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}

			try {
				if (psAnimais != null && !psAnimais.isClosed())
					psAnimais.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}

		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}
}
