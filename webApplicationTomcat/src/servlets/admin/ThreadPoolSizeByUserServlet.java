package servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import utils.ServletUtils;
@WebServlet(name = "ThreadPoolSizeByUserServlet", urlPatterns = "/threadPoolSizeByUser")
public class ThreadPoolSizeByUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String threadPoolSizeByUser = request.getParameter("threadPoolSizeByUser");

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        engineManager.setThreadPoolSizeByUser(Integer.parseInt(threadPoolSizeByUser));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}