package util;


import com.google.gson.Gson;

public class Constants {
    // Server resources locations
    public final static int REFRESH_RATE1 = 1000;
    public final static int REFRESH_RATE = 2000;
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/webApplicationTomcat_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String SIMULATION_DETAILS = FULL_SERVER_PATH + "/simulationDetails";
    public final static String CLIENT_REQUEST = FULL_SERVER_PATH + "/clientRequest";
    public final static String MAP_CLIENT_REQUESTS = FULL_SERVER_PATH + "/allClientRequests";
    public final static String ERROR_MESSAGE = FULL_SERVER_PATH + "/errorMessage";
    public final static String LIST_SIMULATIONS_EXECUTION = FULL_SERVER_PATH + "/listSimulationsExecution";
    public final static String RERUN_EXECUTION = FULL_SERVER_PATH + "/inputForRerunExecution";

    public final static String LIST_WORLDS = FULL_SERVER_PATH + "/listWorlds";
    public final static String SIMULATION_DEFINITION = FULL_SERVER_PATH + "/initializeTreeView";
    public final static String START_EXECUTION = FULL_SERVER_PATH + "/startExecution";
    public final static String PAUSE_SIMULATION = FULL_SERVER_PATH + "/pauseSimulation";
    public final static String RESUME_SIMULATION = FULL_SERVER_PATH + "/resumeSimulation";
    public final static String STOP_SIMULATION = FULL_SERVER_PATH + "/stopSimulation";
    public final static String INIT_TREE_DETAILS = FULL_SERVER_PATH + "/DisplayDetailsInTreeServlet";
    public final static String LOGIN_OUT_CLIENT = FULL_SERVER_PATH + "/logOutClient";
    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
