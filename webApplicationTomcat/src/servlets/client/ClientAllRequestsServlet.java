package servlets.client;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.DTOClientChosenSimulation;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ClientAllRequestsServlet", urlPatterns = "/allClientRequests")
public class ClientAllRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String userName= request.getParameter("userName");
        Map<String, DTOClientChosenSimulation> allRequests= engineManager.getUserManager().getUser(userName).getAllClientRequestsDto();

        String allRequestsString = gson.toJson(allRequests);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(allRequestsString);
    }
}
