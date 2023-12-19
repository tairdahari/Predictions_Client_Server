package servlets.client;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.ServletUtils;


@WebServlet(name = "ClientResumeSimulationServlet", urlPatterns = "/resumeSimulation")
public class ClientResumeSimulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String serialNumber = request.getParameter("serialNumber");
        String simulationName = request.getParameter("simulationName");

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        IWorldManager worldManager = engineManager.getWorldFromFilesById(simulationName);
        worldManager.resume(serialNumber);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}