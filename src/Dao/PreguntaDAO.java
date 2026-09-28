package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class PreguntaDAO {

    public boolean guardarOpcionMultiple(PreguntaOpcionMultiple p) throws SQLException {
        String sqlBase = "INSERT INTO preguntas (id, tipo, enunciado, puntaje_base, id_categoria) VALUES (?, ?, ?, ?, ?)";
        String sqlSub = "INSERT INTO preguntas_opcion_multiple (id_pregunta, opcion_1, opcion_2, opcion_3, opcion_4, indice_correcto) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBaseDatos.getConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtBase = conn.prepareStatement(sqlBase);
                 PreparedStatement stmtSub = conn.prepareStatement(sqlSub)) {

                stmtBase.setInt(1, p.getId());
                stmtBase.setString(2, "OPCION_MULTIPLE");
                stmtBase.setString(3, p.getEnunciado());
                stmtBase.setInt(4, p.getPuntajeBase());
                stmtBase.setInt(5, p.getIdCategoria());
                stmtBase.executeUpdate();

                stmtSub.setInt(1, p.getId());
                stmtSub.setString(2, p.getOpciones().get(0));
                stmtSub.setString(3, p.getOpciones().get(1));
                stmtSub.setString(4, p.getOpciones().get(2));
                stmtSub.setString(5, p.getOpciones().get(3));
                stmtSub.setInt(6, p.getIndiceCorrecto());
                stmtSub.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public Pregunta obtenerPorId(int id) throws SQLException {
        String sql = "SELECT p.*, pom.opcion_1, pom.opcion_2, pom.opcion_3, pom.opcion_4, pom.indice_correcto " +
                     "FROM preguntas p " +
                     "JOIN preguntas_opcion_multiple pom ON p.id = pom.id_pregunta " +
                     "WHERE p.id = ?";

        try (Connection conn = ConexionBaseDatos.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                
                List<String> opcs = new ArrayList<>();
                opcs.add(rs.getString("opcion_1"));
                opcs.add(rs.getString("opcion_2"));
                opcs.add(rs.getString("opcion_3"));
                opcs.add(rs.getString("opcion_4"));

                return new PreguntaOpcionMultiple(
                    rs.getInt("id"),
                    rs.getString("enunciado"),
                    rs.getInt("puntaje_base"),
                    rs.getInt("id_categoria"),
                    opcs,
                    rs.getInt("indice_correcto")
                );
            }
        }
        return null;
    }
}