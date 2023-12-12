package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOClientRequest;
import utils.DTOSimulationDefinition;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "clientRequestServlet", urlPatterns = "/clientRequest")
public class clientRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        // Read the JSON data from the request's input stream
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        // Deserialize the JSON data to DTOExecutionLists
        DTOClientRequest dtoClientRequest = gson.fromJson(jsonBuilder.toString(), DTOClientRequest.class);
        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());

        engineManager.getRequestsManager().addRequest(dtoClientRequest);

        response.setStatus(HttpServletResponse.SC_OK);
        //response.getWriter().write();
    }
}
