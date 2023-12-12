package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.ServletUtils;

import java.io.IOException;


@WebServlet(name = "RequestStatusServlet", urlPatterns = "/requestStatus")
public class RequestStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String serialNumber = request.getParameter("serialNumber");
        String clientName = request.getParameter("clientName");
        String status = request.getParameter("status");

        engineManager.getUserManager().getUser(clientName).setRequestStatus(serialNumber, status);

        //String listSimulationsString = gson.toJson(dtoListSimulationDetails);

        response.setStatus(HttpServletResponse.SC_OK);
        //response.getWriter().write(listSimulationsString);
    }
}