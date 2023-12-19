package servlets.client;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.ServletUtils;

import java.io.IOException;
@WebServlet(name = "ClientStopSimulationServlet", urlPatterns = "/stopSimulation")
public class ClientStopSimulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        String serialNumber = request.getParameter("serialNumber");
        String simulationName = request.getParameter("simulationName");

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        IWorldManager worldManager = engineManager.getWorldFromFilesById(simulationName);
        worldManager.stop(serialNumber);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}