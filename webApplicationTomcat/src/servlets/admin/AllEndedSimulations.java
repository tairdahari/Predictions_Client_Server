package servlets.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.DTOListSimulationDetails;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "AllEndedSimulationsServlet", urlPatterns = "/listEndedSimulationsExecution")
public class AllEndedSimulations extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();


        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        DTOListSimulationDetails dtoListSimulationDetails = engineManager.getAllEndedSimulations();

        String listSimulationsString = gson.toJson(dtoListSimulationDetails);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(listSimulationsString);
    }
}