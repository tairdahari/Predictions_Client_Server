package servlets.resultsScreen;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.DTOPropertyHistogram;
import utils.ServletUtils;

import java.io.IOException;
@WebServlet(name = "SimulationHistogramServlet", urlPatterns = "/simulationHistogram")
public class SimulationHistogramServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String simulationName = request.getParameter("simulationName");
        String serialNumber = request.getParameter("serialNumber");
        String selectedProperty = request.getParameter("selectedProperty");

        DTOPropertyHistogram dtoPropertyHistogram = engineManager.getWorldFromFilesById(simulationName).getHistogram(selectedProperty);
        String propertyHistogram = gson.toJson(dtoPropertyHistogram);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(propertyHistogram);
    }
}