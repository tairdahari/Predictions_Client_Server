package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.clientRequest.ClientRequest;
import prediction.execution.runner.eSimulationState;
import prediction.manager.IEngineManager;
import utils.DTOListSimulationDetails;
import utils.DTOSimulationDetails;
import utils.ServletUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListSimulationsExecutionServlet", urlPatterns = "/listSimulationsExecution")
public class ListSimulationsExecutionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String id = request.getParameter("id");

        DTOListSimulationDetails dtoListSimulationDetails = engineManager.getWorldFromFilesById(id).getAllSimulationsExecution();
        List<DTOSimulationDetails> allSimulations =  dtoListSimulationDetails.getDtoSimulationDetailsList();

        for(DTOSimulationDetails simulationDetails : allSimulations) {
           if(simulationDetails.getInProgress() == eSimulationState.STOPPED) {
               String serialNumber = simulationDetails.getId();
               ClientRequest clientRequest =engineManager.getRequestsManager().getAllRequests().get(Integer.parseInt(serialNumber));

               if(!clientRequest.isStopBoolean()) {
                   clientRequest.setEndedSimulationNumber();
                   clientRequest.setStopBoolean(true);
               }
           }
        }

        String listSimulationsString = gson.toJson(dtoListSimulationDetails);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(listSimulationsString);
    }
}