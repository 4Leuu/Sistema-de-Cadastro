package teste;

import org.example.CandidatoDAO;
import org.example.CandidatoTO;
import org.example.CandidatoDAO;
import org.example.CandidatoTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Scanner;

import static org.junit.Assert.*;

import java.sql.*;

public class CandidatoDAOtest {
    String url = "jdbc:mysql://127.0.0.1:3306/prova";
    String user = "root";
    String senha = "senha";
    Connection connection;
    CandidatoDAO candidatoDAO;

    @After
    public void dbd() throws SQLException{
        String cpf = "12312312312";

        try(Connection connection = DriverManager.getConnection(url, user, senha)) {
            String sql = "delete from candidatos where cpf = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cpf);
            statement.executeUpdate();
            System.out.println("Teste removido com sucesso do banco de dados");
        }catch (SQLException e){
            System.out.println("Erro na remoção do teste do banco de dados \n ERRO: " + e.getMessage());
        }
    }


    @Test
    public void testCandidato(){
        CandidatoTO candidato = new CandidatoTO();
        candidatoDAO = new CandidatoDAO();

        candidato.setNome("Teste");
        candidato.setCpf("12312312312");
        candidato.setTelefone("111112222");
        candidato.setEmail("teste@teste.com");
        candidato.setEndereco("rua ali perto");
        candidato.setCidade("São Paulo");
        candidato.setEstado("SP");
        candidato.setCep("12121056");

        candidatoDAO.inserir(candidato);

        boolean existeCandidato = candidatoDAO.testSeExisteCandidato(candidato.getCpf());

        assertTrue("O candidato deveria existir no banco de dados", existeCandidato);

    }

}