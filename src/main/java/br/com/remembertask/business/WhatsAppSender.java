package br.com.remembertask.business;

import br.com.remembertask.filesystem.WhatsappLoaderBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.ejb.Stateless;

@Stateless
public class WhatsAppSender {

    // Substitua pelos dados da sua conta Twilio


    public void sendMessage(String tasks) {
        final String ACCOUNT_SID = WhatsappLoaderBean.userID;
        final String AUTH_TOKEN = WhatsappLoaderBean.token;
        final String NUMBER = WhatsappLoaderBean.number;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = Message.creator(
                new PhoneNumber(NUMBER), // número destino
                new PhoneNumber("whatsapp:+14155238886"),   // número da sandbox Twilio
                tasks
        ).create();
        System.out.println(tasks);
    }
}

