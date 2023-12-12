package utils;

public class DTOFileUpload {
    private String errorMessage;
    private String isValid;
    private String fileName;

    public DTOFileUpload(String fileName) {
        this.fileName = fileName;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIsValid() {
        return this.isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getFileName() {
        return fileName;
    }
}
