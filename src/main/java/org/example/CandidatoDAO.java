package org.example;

import java.sql.*;

public class CandidatoDAO {

    String url = "jdbc:mysql://127.0.0.1:3306/prova";
    String user = "root";
    String senha = "senha";

    public void inserir(CandidatoTO candidatoTO){
        try(Connection connection = DriverManager.getConnection(url, user, senha)){
            String sql = "insert into candidatos (nome, cpf, telefone, email, endereco, cidade, estado, cep) values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, candidatoTO.getNome());
            statement.setString(2, candidatoTO.getCpf());
            statement.setString(3, candidatoTO.getTelefone());
            statement.setString(4, candidatoTO.getEmail());
            statement.setString(5, candidatoTO.getEndereco());
            statement.setString(6, candidatoTO.getCidade());
            statement.setString(7, candidatoTO.getEstado());
            statement.setString(8, candidatoTO.getCep());
            statement.executeUpdate();
            System.out.println("Dados inseridos ao banco");
        }catch(SQLException e){
            System.out.println("Erro na inserção ao banco de dados \n ERRO: " + e.getMessage());
        }
    }

    public boolean testSeExisteCandidato(String cpf){
        try(Connection connection = DriverManager.getConnection(url, user, senha)){
            String sql = "select count(*) from candidatos where cpf = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro na verificação \n ERRO: " + e.getMessage());
        }
        return false;
    }
}

