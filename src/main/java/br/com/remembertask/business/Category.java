package br.com.remembertask.business;

public enum Category {

    SAUDE("Saude"),
    HIGIENE("Higiene"),
    ESTUDOS("Estudos"),
    TRABALHO("Trabalho"),
    FAMILIA("Familia"),
    FINANCEIRO("Financeiro"),
    ALIMENTACAO("Alimentacao"),
    ENTRETENIMENTO("Entretenimento"),
    ESPIRITUAL("Espiritual");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Category fromDescription(String desc) {
        for (Category c : Category.values()) {
            if (c.description.equalsIgnoreCase(desc)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para descrição: " + desc);
    }
}
