package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOListSimulationDetails;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "ListSimulationsExecutionServlet", urlPatterns = "/listSimulationsExecution")
public class ListSimulationsExecutionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String type = request.getParameter("userName"); // Retrieve the "type" parameter
        String id = request.getParameter("id"); // Retrieve the "type" parameter

        DTOListSimulationDetails dtoListSimulationDetails = engineManager.getWorldFromFilesById(id).getAllSimulationsExecution();

        String listSimulationsString = gson.toJson(dtoListSimulationDetails);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(listSimulationsString);
    }
}
