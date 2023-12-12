//package consoleUI;
//
//import exception.InvalidSelectingCommandException;
//import exception.InvalidSelectingCommandOrderException;
//import prediction.engineManager.IEngineManager;
//import prediction.engineManager.EngineManager;
//import utils.*;
//import exception.InvalidChoiceException;
//import exception.InvalidFileException;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//
//public class ConsoleUI {
//    private final IEngineManager iEngineManager;
//    private boolean runSystem;
//
//    public ConsoleUI(){
//        this.iEngineManager = new EngineManager();
//        this.runSystem = true;
//    }
//    public static void DisplayMenu() {
//        System.out.println("-----------------------------------------------------------");
//        System.out.println("Please select the number of the action you would like to perform from the following menu:");
//        System.out.println("-----------------------------------------------------------");
//        for (eMainMenu option : eMainMenu.values()) {
//            System.out.println((option.ordinal() + 1) + ". " + option);
//        }
//        System.out.println("-----------------------------------------------------------");
//    }
//
//    public void Run() {
//        BooleanWrapper flagCommandOne = new BooleanWrapper(false);
//        BooleanWrapper flagCommandThree = new BooleanWrapper(false);
//        BooleanWrapper flagCommandFive = new BooleanWrapper(false);
//
//
//        System.out.println("Hello and Welcome to Shahar and Tair's predictions project!");
//
//        do {
//            DisplayMenu();
//            Scanner scanner = new Scanner(System.in);
//            int choice;
//            String choiceString;
//            try {
//                choiceString = scanner.nextLine();
//                choice = Integer.parseInt(choiceString);
//
//                while (!(choice >= 1 && choice <= 7)) {
//                    System.out.println("Invalid choice. Please enter a number from 1 to 7: ");
//                    DisplayMenu();
//                    choiceString = scanner.nextLine();
//                    choice = Integer.parseInt(choiceString);
//                }
//                this.userChoiceHandler(choice, flagCommandOne, flagCommandThree, flagCommandFive);
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a number from 1 to 7: ");
//                scanner.nextLine();
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a number from 1 to 7: ");
//            }
//        } while (runSystem);
//    }
//
//    public void userChoiceHandler(int userChoice, BooleanWrapper flagCommandOne, BooleanWrapper flagCommandThree, BooleanWrapper flagCommandFive) {
//        boolean isValidData = false;
//        int choice;
//        String choiceString;
//
//        while (!isValidData) {
//            try {
//                switch (userChoice) {
//                    case 1:
//                        loadingDataFromAnXMLaFile();
//                        flagCommandOne.setValue(true);
//                        break;
//                    case 2:
//                        if(flagCommandOne.isValue() || flagCommandFive.isValue())
//                            displaySimulationDetails();
//                        else
//                            throw new InvalidSelectingCommandOrderException();
//                        break;
//                    case 3:
//                        if(flagCommandOne.isValue() || flagCommandFive.isValue()) {
//                            boolean flag = simulationExecution();
//                            flagCommandThree.setValue(flag);
//                        }
//                        else
//                            throw new InvalidSelectingCommandOrderException();
//                        break;
//                    case 4:
//                        if((flagCommandOne.isValue() && flagCommandThree.isValue()) || flagCommandFive.isValue())
//                            DisplayingFullDetailsOfPastSimulation();
//                        else
//                            throw new InvalidSelectingCommandException();
//                        break;
//                    case 5:
//                        loadDataFromFile();
//                        flagCommandFive.setValue(true);
//                        break;
//                    case 6:
//                        if((flagCommandOne.isValue() || flagCommandFive.isValue()))
//                            storeDataToFile();
//                        else
//                            throw new InvalidSelectingCommandOrderException();
//                        break;
//                    case 7:
//                        System.out.println("You are logged out, goodbye.");
//                        runSystem = false;
//                        break;
//                    default:
//                        System.out.println("Invalid choice");
//                }
//                isValidData = true;
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid choice: " + e.getMessage());
//            }
//            catch (InvalidChoiceException e) {
//                System.out.println("Invalid choice: " + e.getMessage());
//            } catch (InvalidSelectingCommandOrderException | InvalidSelectingCommandException e) {
//                System.out.println(e.getMessage());
//                return;
//            }
//            catch (RuntimeException | IOException | ClassNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//
//            if (!isValidData) {
//                try {
//                    Scanner scanner = new Scanner(System.in);
//                    System.out.println("Type 1 if you want to keep trying or type 0 if you want to go back to the menu: ");
//                    choiceString = scanner.nextLine();
//                    choice = Integer.parseInt(choiceString);
//                    if (choice == 0)
//                        return;
//                    else if (choice != 1 && choice != 0)
//                        throw new InvalidChoiceException("Invalid choice: " + choice);
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid choice: " + e.getMessage());
//                } catch (InvalidChoiceException e) {
//                    System.out.println("Invalid choice: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    private void storeDataToFile() throws IOException {
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the path of a file to which you would like to save the information");
//        String path = scanner.nextLine();
//        iEngineManager.saveToFile(path);
//        System.out.println("File saved successfully");
//    }
//
//    private void loadDataFromFile() throws IOException, ClassNotFoundException{
//        System.out.println("Please enter the path of a file that you would like to upload to the system");
//        Scanner scanner = new Scanner(System.in);
//        String path = scanner.nextLine();
//        File file = new File(path);
//
//        if (!file.exists()) {
//            throw new InvalidFileException("Invalid path. file does not exist.");
//        }
//        iEngineManager.loadFromFile(path);
//        System.out.println("Data loaded successfully");
//    }
//
//    private void DisplayingFullDetailsOfPastSimulation() {
//
//        DTOListSimulationDetails runDetailsList = new DTOListSimulationDetails(iEngineManager.getAllSimulations());
//
//        System.out.println("List of Past Simulation Runs:");
//        for (int i = 0; i < runDetailsList.getPastRunsList().size(); i++) {
//            DTOSimulationDetails runDetails = runDetailsList.getPastRunsList().get(i);
//            System.out.println((i + 1) + ". " + "Run date: " + runDetails.getFormattedRunDate());
//            System.out.println("The unique identifier of the run is: " + runDetails.getUniqueIdentifier());
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        int selectedRunIndex;
//        do {
//            System.out.print("Select the number of the relevant run: ");
//            selectedRunIndex = scanner.nextInt();
//            if (selectedRunIndex < 1 || selectedRunIndex > runDetailsList.getPastRunsList().size()) {
//                System.out.println("Invalid selection. Please enter a valid number.");
//            }
//        } while (selectedRunIndex < 1 || selectedRunIndex > runDetailsList.getPastRunsList().size());
//
//        DTOSimulationDetails selectedRunDetails = runDetailsList.getPastRunsList().get(selectedRunIndex - 1);
//
//        System.out.println("Selected Run: " + selectedRunDetails.getFormattedRunDate());
//        System.out.println("Select Display Mode:");
//        System.out.println("1. Amount of Entities");
//        System.out.println("2. Characteristic Histogram");
//        int displayModeChoice = scanner.nextInt();
//
//        switch (displayModeChoice) {
//            case 1:
//                displayAmountOfEntities(selectedRunDetails);
//                break;
//            case 2:
//                displayCharacteristicHistogram(selectedRunDetails);
//                break;
//            default:
//                System.out.println("Invalid choice.");
//        }
//    }
//
//    private void displayAmountOfEntities(DTOSimulationDetails selectedRunDetails) {
//        System.out.println("Displaying Amount of Entities for run number " + selectedRunDetails.getUniqueIdentifier() + ":");
//
//        DTOEntityExecution EntityExecution = this.iEngineManager.getEntityExecution();
//
//        System.out.println("Entity name: " + EntityExecution.getEntityInstanceName());
//        System.out.println("Initial Quantity: " + EntityExecution.getInitialQuantity());
//        System.out.println("Final Quantity: " + EntityExecution.getFinalQuantity());
//        System.out.println();
//    }
//
//    private void displayCharacteristicHistogram(DTOSimulationDetails selectedRunDetails) {
//        System.out.println("Displaying Characteristic Histogram for run number " + selectedRunDetails.getUniqueIdentifier() + ":");
//        List<DTOEntityDefinition> entityDefinitionsList = this.iEngineManager.getSimulationDefinition().getDtoEntityDefinition();
//
//        System.out.println("List of Entities:");
//        for (int i = 0; i < entityDefinitionsList.size(); i++) {
//            System.out.println((i + 1) + ". " + entityDefinitionsList.get(i).getEntityName());
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        int selectedEntityIndex;
//        do {
//            System.out.print("Select the number of the relevant entity: ");
//            selectedEntityIndex = scanner.nextInt();
//            if (selectedEntityIndex < 1 || selectedEntityIndex > entityDefinitionsList.size()) {
//                System.out.println("Invalid selection. Please enter a valid number.");
//            }
//        } while (selectedEntityIndex < 1 || selectedEntityIndex > entityDefinitionsList.size());
//
//        DTOEntityDefinition dtoEntityDefinition= entityDefinitionsList.get(selectedEntityIndex - 1);
//
//        System.out.println("Properties of selected entity:");
//        List<DTOPropertyDefinition> properties = dtoEntityDefinition.getEntityProperties();
//        for (int i = 0; i < properties.size(); i++) {
//            System.out.println((i + 1) + ". " + properties.get(i).getName());
//        }
//
//        int selectedPropertyIndex;
//        do {
//            System.out.print("Select the number of the relevant property: ");
//            selectedPropertyIndex = scanner.nextInt();
//            if (selectedPropertyIndex < 1 || selectedPropertyIndex > properties.size()) {
//                System.out.println("Invalid selection. Please enter a valid number.");
//            }
//        } while (selectedPropertyIndex < 1 || selectedPropertyIndex > properties.size());
//
//        DTOPropertyDefinition selectedProperty = properties.get(selectedPropertyIndex - 1);
//        String selectedPropertyName = selectedProperty.getName();
//
//
//        DTOPropertyHistogram dtoPropertyHistogram = iEngineManager.getHistogram(selectedPropertyName);
//        System.out.println("Histogram of the Property: " + selectedPropertyName);
//        for (Map.Entry<String, Integer> entry : dtoPropertyHistogram.getValues().entrySet()) {
//            System.out.println("Value: " + entry.getKey() + ", Count: " + entry.getValue());
//        }
//    }
//
//    private boolean simulationExecution() {
//        List<Object> userEnvVarChoices;
//        userEnvVarChoices = displayAndSetEnv();
//        this.iEngineManager.initialization(userEnvVarChoices);
//        displayUpdateEnv();
//        return this.iEngineManager.simulationExecute();
//    }
//
//    public void displayUpdateEnv() {
//        DTOSimulationExecution dtoSimulationExecution = iEngineManager.getSimulationExecution();
//
//        for (DTOPropertyExecution dtoEnvironmentVariable: dtoSimulationExecution.getDtoEnvironmentVariables()) {
//            printDTOInstanceEnvironmentVariable(dtoEnvironmentVariable);
//        }
//    }
//
//    private void printDTOInstanceEnvironmentVariable(DTOPropertyExecution dtoEnvironmentVariable) {
//        System.out.println("Property Instance Name: " + dtoEnvironmentVariable.getPropertyInstanceName());
//        System.out.println("Value: " + dtoEnvironmentVariable.getValue());
//        System.out.println();
//    }
//
//    private List<Object> displayAndSetEnv() {
//        Scanner scanner = new Scanner(System.in);
//        List<Object> userChoices = new ArrayList<>();
//
//        DTOSimulationDefinition dtoSimulationDefinition = iEngineManager.getSimulationDefinition();
//        List<DTOPropertyDefinition> dtoEnvironmentVariables = dtoSimulationDefinition.getDtoEnvironmentVariables();
//
//        for (DTOPropertyDefinition dtoEnvironmentVariable : dtoEnvironmentVariables) {
//            userChoices.add(dtoEnvironmentVariable.getValue());
//        }
//
//        int choice;
//        do {
//            System.out.println("Choose an environment variable to update (or 0 to finish):");
//            for (int i = 0; i < dtoEnvironmentVariables.size(); i++) {
//                System.out.println((i + 1) + ". " + dtoEnvironmentVariables.get(i).getName());
//            }
//            System.out.print("Enter choice: ");
//            choice = Integer.parseInt(scanner.nextLine());
//
//            if (choice >= 1 && choice <= dtoEnvironmentVariables.size()) {
//                DTOPropertyDefinition chosenVariable = dtoEnvironmentVariables.get(choice - 1);
//                updateUserChoice(choice, chosenVariable, userChoices);
//            }
//            else if (choice != 0) {
//                System.out.println("Invalid choice. Please enter a number between 1 and " + dtoEnvironmentVariables.size() + ":");
//            }
//        } while (choice != 0);
//
//        return userChoices;
//    }
//
//    private void updateUserChoice(int choice, DTOPropertyDefinition chosenVariable, List<Object> userChoices) {
//        printDTOEnvironmentVariable(chosenVariable);
//        System.out.print("Enter value (or leave empty for default): ");
//        String userInput = getUserInputFromConsole();
//
//        if (!userInput.isEmpty()) {
//            userChoices.set(choice -1 , convertUserInputToPropertyTypeWithRetry(userInput, chosenVariable.getType(), chosenVariable));
//        }
//    }
//
//    private Object convertUserInputToPropertyTypeWithRetry(String userInput, String type, DTOPropertyDefinition dtoEnvironmentVariable) {
//        Object returnValue;
//
//        if (userInput.isEmpty()) {
//            returnValue = dtoEnvironmentVariable.getValue();
//        } else {
//            while (true) {
//                try {
//                    switch (type) {
//                        case "decimal":
//                            int intValue = Integer.parseInt(userInput);
//                            validateInRange(intValue, Float.parseFloat(dtoEnvironmentVariable.getFrom()), Float.parseFloat(dtoEnvironmentVariable.getTo()));
//                            returnValue = intValue;
//                            break;
//                        case "float":
//                            float floatValue = Float.parseFloat(userInput);
//                            validateInRange(floatValue, Float.parseFloat(dtoEnvironmentVariable.getFrom()), Float.parseFloat(dtoEnvironmentVariable.getTo()));
//                            returnValue = floatValue;
//                            break;
//                        case "boolean":
//                           // returnValue = Boolean.parseBoolean(userInput);
//                            ckValid(userInput);
//                            returnValue = userInput;
//                            break;
//                        case "string":
//                            returnValue = userInput;
//                            break;
//                        default:
//                            throw new IllegalArgumentException("Unsupported property type: " + type);
//                    }
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input for " + type + ". Please enter a valid value:");
//                    userInput = getUserInputFromConsole();
//                } catch (IllegalArgumentException e) {
//                    System.out.println(e.getMessage());
//                    userInput = getUserInputFromConsole();
//                }
//            }
//        }
//        return returnValue;
//    }
//
//    private void ckValid(String userInput) {
//        if(!userInput.equals("true") && !userInput.equals("false"))
//            throw new IllegalArgumentException("Value must be true or false");
//    }
//
//    private void validateInRange(int value, float min, float max) {
//        if (value < min || value > max) {
//            throw new IllegalArgumentException("Value must be between " + min + " and " + max);
//        }
//    }
//
//    private void validateInRange(float value, float min, float max) {
//        if (value < min || value > max) {
//            throw new IllegalArgumentException("Value must be between " + min + " and " + max);
//        }
//    }
//    public String getUserInputFromConsole() {
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextLine();
//    }
//
//
//
//    private void printDTOEnvironmentVariable(DTOPropertyDefinition dtoEnvironmentVariable) {
//        System.out.println("Variable Name: " + dtoEnvironmentVariable.getName());
//        System.out.println("Variable Type: " + dtoEnvironmentVariable.getType());
//
//        if(dtoEnvironmentVariable.getFrom() != null)
//            System.out.println("Variable Range: " + dtoEnvironmentVariable.getFrom() + "-" + dtoEnvironmentVariable.getTo());
//        System.out.println();
//    }
//
//
//    public void loadingDataFromAnXMLaFile() {
//        boolean flag;
//        StringBuilder path = new StringBuilder();
//
//        flag = validationXMLFile(path);
//        String pathString = path.toString();
//        if(flag)
//        {
//            File file = new File(pathString);
//            if (!file.canRead()) {
//                throw  new InvalidFileException("The file is unreadable");
//            } else {
//                DTOString result = iEngineManager.readingSystemInformationFromFile(file);
//                System.out.println(result.getValidResult() + "\n");
//            }
//        }
//        else
//            throw  new InvalidChoiceException("The number entered is not one of the possible options");
//    }
//    public static boolean validationXMLFile(StringBuilder pathBuilder) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Please enter the full path to the XML file you want to load:");
//        try {
//            String path = reader.readLine();
//            pathBuilder.append(path);
//
//            File file = new File(path);
//            if (!file.exists()) {
//                throw new IOException("Invalid path. File does not exist.");
//            }
//
//            if (!file.getName().endsWith(".xml")) {
//                throw new IOException("Invalid file type. The file must be an XML file.");
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public void displaySimulationDetails() {
//        DTOSimulationDefinition dtoSimulationDefinition = iEngineManager.getSimulationDefinition();
//        //entities
//        for(DTOEntityDefinition dtoEntityDefinition : dtoSimulationDefinition.getDtoEntityDefinition()) {
//            printDTOEntityDefinition(dtoEntityDefinition);
//        }
//        //rules
//        for(DTORuleDefinition dtoRuleDefinition : dtoSimulationDefinition.getDtoRulesDefinition()) {
//            printDTORuleDefinition(dtoRuleDefinition);
//        }
//        //termination
//        for(DTOTerminationDefinition dtoTerminationDefinition : dtoSimulationDefinition.getDtoTerminationDefinition()) {
//            printDTOTerminationDefinition(dtoTerminationDefinition);
//        }
//    }
//
//    private void printDTOTerminationDefinition(DTOTerminationDefinition dtoTerminationDefinition) {
//        System.out.println("Termination Definition:");
//        System.out.println(dtoTerminationDefinition.getTerminationName() + " : " + dtoTerminationDefinition.getCount());
//        System.out.println();
//    }
//
//    private void printDTORuleDefinition(DTORuleDefinition dtoRuleDefinition) {
//        System.out.println("Rule Name: " + dtoRuleDefinition.getName());
//        System.out.println("Activate Time:");
//        System.out.println("  Ticks: " + dtoRuleDefinition.getTicks());
//        System.out.println("  Probability: " + dtoRuleDefinition.getProbability());
//        System.out.println("Number of Actions: " + dtoRuleDefinition.getNumOfActions());
//        System.out.println("Actions:");
//
//        List<String> actions = dtoRuleDefinition.getActions();
//        for (String action : actions) {
//            System.out.println("- " + action);
//        }
//        System.out.println();
//    }
//
//    private static void printDTOEntityDefinition(DTOEntityDefinition dtoEntityDefinition) {
//        System.out.println("Entity Name: " + dtoEntityDefinition.getEntityName());
//        System.out.println("Population: " + dtoEntityDefinition.getNumOfPopulation());
//        System.out.println("Entity Properties:");
//
//        for (DTOPropertyDefinition property : dtoEntityDefinition.getEntityProperties()) {
//            System.out.println("  Property Name: " + property.getName());
//            System.out.println("  Property Type: " + property.getType());
//            System.out.println("  Random Init: " + property.getInitRandom());
//
//            if(property.getFrom() != null) {
//                System.out.println("  Range: " + property.getFrom() + "-" + property.getTo());
//            }
//            System.out.println();
//        }
//    }
//}