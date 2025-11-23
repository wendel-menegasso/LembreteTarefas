package br.com.remembertask.business;

public enum Frequency {

    DIARIO(1, "DIARIAS"),
    SEMANAL(7, "SEMANAIS"),
    QUINZENAL(15, "QUINZENAIS"),
    MENSAL(30, "MENSAIS"),
    TRIMESTRAL(90, "TRIMESTRAIS"),
    SEMESTRAL(180, "SEMESTRAIS"),
    ANUAL(365, "ANUAIS");

    private final int numberOfDays;
    private final String description;

    Frequency(int numberOfDays, String description) {
        this.numberOfDays = numberOfDays;
        this.description = description;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public String getDescription() {
        return description;
    }

    public static Frequency fromNumberOfDays(int days) {
        for (Frequency f : Frequency.values()) {
            if (f.numberOfDays == days) {
                return f;
            }
        }
        throw new IllegalArgumentException("Nenhuma frequência encontrada para " + days + " dias.");
    }

    public static Frequency fromDescription(String desc) {
        for (Frequency f : Frequency.values()) {
            if (f.description.equalsIgnoreCase(desc)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Nenhuma frequência encontrada para descrição: " + desc);
    }
}
