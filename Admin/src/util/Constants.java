package util;


import com.google.gson.Gson;

public class Constants {

    public static final String USERNAME = "username";
    public final static int REFRESH_RATE = 2000;
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/webApplicationTomcat_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String LIST_SIMULATIONS_EXECUTION = FULL_SERVER_PATH + "/listSimulationsExecution";
    public final static String LIST_ENDED_SIMULATIONS_EXECUTION = FULL_SERVER_PATH + "/listEndedSimulationsExecution";
    public final static String SIMULATION_HISTOGRAM = FULL_SERVER_PATH + "/simulationHistogram";
    public final static String ENTITY_QUANTITY = FULL_SERVER_PATH + "/entityQuantity";
    public final static String CONSISTENCY = FULL_SERVER_PATH + "/consistency";
    public final static String ERROR_MESSAGE = FULL_SERVER_PATH + "/errorMessage";
    public final static String LOAD_FILE = FULL_SERVER_PATH + "/upload_file";
    public final static String REQUEST_STATUS = FULL_SERVER_PATH + "/requestStatus";
    public final static String ALL_REQUESTS = FULL_SERVER_PATH + "/allRequests";
    public final static String QUEUE_DATA = FULL_SERVER_PATH + "/queueData";
    public final static String SIMULATION_DETAILS = FULL_SERVER_PATH + "/simulationDetails";
    public final static String SIMULATION_DEFINITION = FULL_SERVER_PATH + "/initializeTreeView";
    public final static String THREADPOOL_SIZE = FULL_SERVER_PATH + "/threadPoolSizeByUser";
    public static final String ADMIN_LOGIN  = FULL_SERVER_PATH + "/adminLogin";
    public static final String LOG_OUT_ADMIN  = FULL_SERVER_PATH + "/adminLogOut";
    public final static Gson GSON_INSTANCE = new Gson();
}