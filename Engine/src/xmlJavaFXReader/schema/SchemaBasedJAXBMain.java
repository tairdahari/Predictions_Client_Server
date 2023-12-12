package xmlJavaFXReader.schema;



import xmlJavaFXReader.schema.generated.PRDWorld;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SchemaBasedJAXBMain
{
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "xmlJavaFXReader.schema.generated";

    public PRDWorld createWorldFromXMLFile(File file)  {

        PRDWorld world = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            world = deserializeFrom(inputStream);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }
    private static PRDWorld deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }

    public PRDWorld createWorldFromXMLFileWeb(InputStream inputStreamFile) {
        PRDWorld world = null;
        try {
            world = deserializeFrom(inputStreamFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return world;
    }
}
