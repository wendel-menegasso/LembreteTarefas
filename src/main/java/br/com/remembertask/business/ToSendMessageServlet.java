package br.com.remembertask.business;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/toSend")
public class ToSendMessageServlet extends HttpServlet {

    @EJB
    private WhatsAppSender sender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String tasks = req.getParameter("tasks");
        sender.sendMessage(tasks);
    }
}
