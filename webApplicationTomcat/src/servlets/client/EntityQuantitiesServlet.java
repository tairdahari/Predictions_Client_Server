package servlets.client;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOEntityQuantities;
import utils.ServletUtils;

import java.io.IOException;


@WebServlet(name = "EntityQuantitiesServlet", urlPatterns = "/entityQuantity")
public class EntityQuantitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String simulationName = request.getParameter("simulationName");

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        IWorldManager worldManager = engineManager.getWorldFromFilesById(simulationName);
        DTOEntityQuantities entityQuantities = worldManager.getContext().getEntityQuantities();

        String entityQuantitiesJson = gson.toJson(entityQuantities);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(entityQuantitiesJson);
    }
}