package servlets.newExecutionScreen;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.clientRequest.ClientRequest;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOExecutionLists;
import utils.DTOSimulationDefinition;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "startExecutionServlet", urlPatterns = "/startExecution")
public class startExecutionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        // Read the JSON data from the request's input stream
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        // Deserialize the JSON data to DTOExecutionLists
        DTOExecutionLists dtoExecutionLists = gson.fromJson(jsonBuilder.toString(), DTOExecutionLists.class);

        // Now you have the DTOExecutionLists object in your servlet
        List<Integer> populationList = dtoExecutionLists.getPopulationList();
        List<Object> userEnvVarChoices = dtoExecutionLists.getEnvList();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String id = request.getParameter("id");
        String serialNumber = request.getParameter("serialNumber");
        ClientRequest clientRequest = engineManager.getRequestsManager().getAllRequests().get(Integer.parseInt(serialNumber));

        IWorldManager worldManager = engineManager.getWorldFromFilesById(id);
        worldManager.initPopulation(populationList);
        worldManager.initialization(userEnvVarChoices, clientRequest.getTerminationManager());
        worldManager.start(populationList, userEnvVarChoices, engineManager.getThreadPoolExecutor());

        DTOSimulationDefinition dtoSimulationDefinition = worldManager.getSimulationDefinition();
        String simulationDefinitionToJSON = gson.toJson(dtoSimulationDefinition);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(simulationDefinitionToJSON);
    }
}


