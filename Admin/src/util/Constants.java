package util;


import com.google.gson.Gson;

public class Constants {

    public static final String USERNAME = "username";
    public final static int REFRESH_RATE = 2000;
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/webApplicationTomcat_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String LOAD_FILE = FULL_SERVER_PATH + "/upload_file";
    public final static String REQUEST_STATUS = FULL_SERVER_PATH + "/requestStatus";
    public final static String ALL_REQUESTS = FULL_SERVER_PATH + "/allRequests";

    public static final String ADMIN_LOGIN  = FULL_SERVER_PATH + "/adminLogin";
    public static final String LOG_OUT_ADMIN  = FULL_SERVER_PATH + "/adminLogOut";
    public final static Gson GSON_INSTANCE = new Gson();
}