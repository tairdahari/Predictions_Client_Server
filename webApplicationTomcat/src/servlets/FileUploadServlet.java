package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import prediction.manager.IEngineManager;
import prediction.worldManager.IWorldManager;
import utils.DTOFileUpload;
import utils.ServletUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Scanner;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload_file")
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        String fileName = null;
        boolean isFirstUpload = Boolean.parseBoolean(request.getParameter("isFirstUpload"));

        Collection<Part> parts = request.getParts();
        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            fileName = part.getSubmittedFileName();
            fileContent.append(readFromInputStream(part.getInputStream()));
        }

        String isValid = "";
        Gson gson = new Gson();
        DTOFileUpload dtoFileUpload = new DTOFileUpload(fileName);

        IEngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        String contentString = fileContent.toString();

        if (contentString.endsWith(".xml")) {
            isValid = "false";
            writeDataToDTO(dtoFileUpload, isValid, "Invalid file type. The file must be an XML file.");
            String dtoFileUploadJAVA = gson.toJson(dtoFileUpload);
            response.getWriter().write(dtoFileUploadJAVA);
            return;
        }

        // Convert the StringBuilder to a byte array
        byte[] byteArray = fileContent.toString().getBytes();

        // Create an InputStream from the byte array
        InputStream inputStreamFile = new ByteArrayInputStream(byteArray);

        IWorldManager worldManager = engineManager.createWorld(fileName);

        try {
            worldManager.readingSystemInformationFromFileWeb(inputStreamFile, isFirstUpload);
            isValid = "true";
            writeDataToDTO(dtoFileUpload, isValid,"");

            String dtoFileUploadJAVA = gson.toJson(dtoFileUpload);
            response.getWriter().write(dtoFileUploadJAVA);
        }
        catch (RuntimeException e) {
            isValid = "false";
            writeDataToDTO(dtoFileUpload, isValid, e.getMessage());
            String dtoFileUploadJAVA = gson.toJson(dtoFileUpload);
            response.getWriter().write(dtoFileUploadJAVA);
        }
        engineManager.getAllFilesInTheSystem().put(worldManager.getFileName(), worldManager);
    }

    public void writeDataToDTO(DTOFileUpload dtoFileUpload, String isValid, String errorMessage)
    {
        dtoFileUpload.setErrorMessage(errorMessage);
        dtoFileUpload.setIsValid(isValid);
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}


