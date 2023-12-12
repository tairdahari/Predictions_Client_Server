package servlets.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.DTOClientChosenSimulation;
import utils.DTOSimulationDetails;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Map;


@WebServlet(name = "AdminAllRequestsServlet", urlPatterns = "/allRequests")
public class AdminAllRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());

        Map<String, DTOClientChosenSimulation> allRequests= engineManager.getRequestsManager().getDtoAllRequests();

        String allRequestsString = gson.toJson(allRequests);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(allRequestsString);
    }
}

