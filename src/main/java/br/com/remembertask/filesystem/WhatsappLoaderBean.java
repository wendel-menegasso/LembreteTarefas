package br.com.remembertask.filesystem;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton        // EJB singleton
@Startup
public class WhatsappLoaderBean {


    public static String userID;
    public static String token;
    public static String number;

    @PostConstruct
    public static void loadProperties() {
        Properties props = new Properties();

        try (InputStream input = TaskRepository.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Arquivo application.properties não encontrado em src/main/resources.");
                return;
            }

            props.load(input);

            // Substitui ${DATABASE_PATH} pela variável de ambiente
            String rawValue = props.getProperty("userId");
            if (rawValue != null && rawValue.contains("${USER_ID}")) {
                String envValue = System.getenv("USER_ID");
                if (envValue != null) {
                    userID = envValue;
                } else {
                    System.out.println("Variável de ambiente USER_ID não está definida.");
                }
            }

            rawValue = props.getProperty("token");
            if (rawValue != null && rawValue.contains("${TOKEN}")) {
                String envValue = System.getenv("TOKEN");
                if (envValue != null) {
                    token = envValue;
                } else {
                    System.out.println("Variável de ambiente TOKEN não está definida.");
                }
            }

            rawValue = props.getProperty("yourNumber");
            if (rawValue != null && rawValue.contains("${W_NUMBER}")) {
                String envValue = System.getenv("W_NUMBER");
                if (envValue != null) {
                    number = envValue;
                } else {
                    System.out.println("Variável de ambiente W_NUMBER não está definida.");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler application.properties: " + e.getMessage());
        }
    }
}
