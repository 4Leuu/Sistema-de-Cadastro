package org.example;

import org.camunda.bpm.client.ExternalTaskClient;

import javax.mail.MessagingException;

public class InscricaoWorker {

    private static final String camundaServer = "http://localhost:8080/engine-rest";

    public static void main(String [] args){
        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(camundaServer)
                .asyncResponseTimeout(1000)
                .maxTasks(1).build();

        client.subscribe("salvar-dados").handler((externalTask, externalTaskService) -> {
            String nome = (String) externalTask.getVariable("nome");
            String cpf = (String) externalTask.getVariable("cpf");
            String telefone = (String) externalTask.getVariable("telefone");
            String email = (String) externalTask.getVariable("email");
            String endereco = (String) externalTask.getVariable("endereco");
            String cidade = (String) externalTask.getVariable("cidade");
            String estado = (String) externalTask.getVariable("estado");
            String cep = (String) externalTask.getVariable("cep");

            System.out.println("Recebendo informações:");
            System.out.println("Nome:" + nome + "\n" +
                                "CPF: " + cpf + "\n" +
                                "Telefone:" + telefone + "\n" +
                                "Email: " + email + "\n" +
                                "Endereço: " + endereco + "\n" +
                                "Cidade: " + cidade + "\n" +
                                "Estado: " + estado + "\n" +
                                "CEP: " + cep + "\n");
            CandidatoTO candidatoTO = new CandidatoTO(nome, cpf, telefone, email, endereco, cidade, estado, cep);
            CandidatoDAO candidatoDAO = new CandidatoDAO();
            candidatoDAO.inserir(candidatoTO);

            String to = email;
            String subject = "Seja bem-vindo(a)";
            String content = "Obrigado por se inscrever " + nome +" seja bem-vindo(a) ao nosso aplicativo.";

            try{
                JavaMail.sendEmail(to, subject, content);
                System.out.println("Email enviado com succeso para: " + email);
            } catch (MessagingException e) {
                System.out.println("Erro ao enviar email \n ERRO: " + e.getMessage());
            }

            externalTaskService.complete(externalTask);

        }).open();
    }

}