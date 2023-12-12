package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOSimulationDetails;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "ErrorMessageServlet", urlPatterns = "/errorMessage")
public class ErrorMessageServlet extends HttpServlet {
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
        DTOSimulationDetails dtoSimulationDetails = gson.fromJson(jsonBuilder.toString(), DTOSimulationDetails.class);

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String id= request.getParameter("id");
        String errorForDisplay = engineManager.getWorldFromFilesById(id).getErrorMessage(dtoSimulationDetails);


        //String errorMessageString = gson.toJson(errorForDisplay);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(errorForDisplay);
    }

}