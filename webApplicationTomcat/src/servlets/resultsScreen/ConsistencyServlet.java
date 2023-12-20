package servlets.resultsScreen;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "ConsistencyServlet", urlPatterns = "/consistency")
public class ConsistencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String simulationName = request.getParameter("simulationName");
        //String serialNumber = request.getParameter("serialNumber");
        String propertyName = request.getParameter("propertyName");
        float consistency =  engineManager.getWorldFromFilesById(simulationName).getPropertyConsistency(propertyName);

        String consistencyJson = gson.toJson(consistency);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(consistencyJson);
    }
}