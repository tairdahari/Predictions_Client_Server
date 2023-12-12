package consoleUI;

public enum eMainMenu {
    READ_FILE_XML("Reading system information from file"),
    SHOW_SIMULATION_DETAILS("Display simulation details"),
    SIMULATION_EXECUTION("Simulation execution"),
    PAST_ACTION("Display full details of past simulation"),
    LOAD_DATA_FROM_FILE("Load data from file"),
    STORE_DATA_TO_FILE("Store data to file"),
    LOG_OFF("Log off from system");
    public final String message;
    eMainMenu(String message) { this.message = message;}

    public String toString() {return this.message; }
}
