package servlets.loginPage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import prediction.users.User;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ClientLogOutServlet", urlPatterns = "/logOutClient")
public class ClientLogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String usernameFromParameter = request.getParameter("username");
        if (usernameFromParameter == null) {
            return;
        }

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        if(!engineManager.getUserManager().isUserExists(usernameFromParameter))
            return;

        engineManager.removeUserFromTheUserManager(usernameFromParameter);

    }

    public User getUserByName(Map<String, User> usersMap, String name) {
        for (Map.Entry<String, User> entry : usersMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals(name)) {
                return entry.getValue();
            }
        }
        return null; // User not found
    }
}

