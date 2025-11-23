package br.com.remembertask.business;

public class Message {

    private String token;
    private String phoneNumberId;
    private String celPhone;
    private String message;

    public Message() {
    }

    public Message(String token, String phoneNumberId, String celPhone, String message) {
        this.token = token;
        this.phoneNumberId = phoneNumberId;
        this.celPhone = celPhone;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter e Setter para phoneNumberId
    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(String phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    // Getter e Setter para celPhone
    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    // Getter e Setter para message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
