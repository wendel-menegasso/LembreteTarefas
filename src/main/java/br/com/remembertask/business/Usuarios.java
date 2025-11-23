package br.com.remembertask.business;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuarios {

    private long id;
    private String username;
    private String senha;
    private Role role;
    private boolean active;
    private boolean expired;
    private Timestamp lastUpdate;

    // Construtor padrão
    public Usuarios() {
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public boolean setSenha(String senha) {
        if (validarSenha(senha)) {
            this.senha = senha;
            return true;
        }
        return false;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    private boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 12 || senha.length() > 24) {
            return false;
        }

        Pattern maiusculas = Pattern.compile("[A-Z]");
        Pattern minusculas = Pattern.compile("[a-z]");
        Pattern numeros = Pattern.compile("[0-9]");
        Pattern especiais = Pattern.compile("[^a-zA-Z0-9]");

        return contarOcorrencias(maiusculas, senha) >= 3 &&
                contarOcorrencias(minusculas, senha) >= 3 &&
                contarOcorrencias(numeros, senha) >= 3 &&
                contarOcorrencias(especiais, senha) >= 1;
    }

    private int contarOcorrencias(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
