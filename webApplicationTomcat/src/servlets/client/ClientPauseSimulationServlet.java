package servlets.client;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "ClientPauseSimulationServlet", urlPatterns = "/pauseSimulation")
public class ClientPauseSimulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serialNumber = request.getParameter("serialNumber");
        String simulationName = request.getParameter("simulationName");

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        IWorldManager worldManager = engineManager.getWorldFromFilesById(simulationName);
        worldManager.pause(serialNumber);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
