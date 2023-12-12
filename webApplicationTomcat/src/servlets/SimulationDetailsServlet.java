package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOSimulationDetails;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "SimulationDetailsServlet", urlPatterns = "/simulationDetails")
public class SimulationDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String simulationId = request.getParameter("simulationId");
        String id = request.getParameter("id");

        DTOSimulationDetails simulationDetails = engineManager.getWorldFromFilesById(id).getDtoSimulationDetailsByID(Integer.parseInt(simulationId));

        String simulationDetailsString = gson.toJson(simulationDetails);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(simulationDetailsString);
    }
}