package servlets.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.DTOQueue;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "QueueDataServlet", urlPatterns = "/queueData")
public class QueueDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        DTOQueue dtoQueue = engineManager.getDtoQueue();

        String dtoQueueJson = gson.toJson(dtoQueue);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(dtoQueueJson);
    }
}