package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "DisplayDetailsInTreeServlet", urlPatterns = "/DisplayDetailsInTreeServlet")
public class DisplayDetailsInTreeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String type = request.getParameter("type"); // Retrieve the "type" parameter
        String selectedItem = request.getParameter("selected"); // Retrieve the "selected" parameter
        String id = request.getParameter("id"); // Retrieve the "selected" parameter

        Object selectedDefinition = engineManager.getWorldFromFilesById(id).getCurrentDtoDefinition(type, selectedItem);

        String selectedDefinitionString = gson.toJson(selectedDefinition);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(selectedDefinitionString);
    }

}

