package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOAllFiles;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListWorldsServlet", urlPatterns = "/listWorlds")
public class ListWorldsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

            IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
            DTOAllFiles allFilesInTheSystem = engineManager.getDTOFilesList();

            // Convert the list to JSON
            Gson gson = new Gson();
            String worldListJson = gson.toJson(allFilesInTheSystem.getAllFiles());

            // Set the response content type to JSON
            response.setContentType("application/json");

            // Write the JSON response to the client
            response.getWriter().write(worldListJson);
        }
    }
}
