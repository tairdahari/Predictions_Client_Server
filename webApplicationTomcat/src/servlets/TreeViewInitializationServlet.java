package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOSimulationDefinition;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "TreeViewInitializationServlet", urlPatterns = "/initializeTreeView")
public class TreeViewInitializationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        String id = request.getParameter("id");
        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        DTOSimulationDefinition dtoSimulationDefinition = engineManager.getWorldFromFilesById(id).getSimulationDefinition();
        String simulationDefinitionToJSON = gson.toJson(dtoSimulationDefinition);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(simulationDefinitionToJSON);
    }
}

