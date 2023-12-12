package servlets.loginPage;

import constants.Constants;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import prediction.users.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginShortResponse")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        IEngineManager systemEngine = ServletUtils.getEngineManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = systemEngine.getUserManager();

        if (usernameFromSession == null) { //user is not logged in yet

            String usernameFromParameter = request.getParameter("username");
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                //no username in session and no username in parameter - not standard situation. it's a conflict

                // stands for conflict in server state
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();


                synchronized (this) {
                    if (userManager.isUserExists(usernameFromParameter)) {
                        String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";

                        // stands for unauthorized as there is already such user with this name
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getOutputStream().print(errorMessage);
                    }
                    else {
                        //add the new user to the users list
                        userManager.addUser(usernameFromParameter);
                        //systemEngine.getUserFlowsDefinitionMap().put(userManager.getUser(usernameFromParameter), new FlowsDefinitionImpl());
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);

                        System.out.println("On login, request URI is: " + request.getRequestURI());
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                }
            }
        } else {
            //user is already logged in
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}