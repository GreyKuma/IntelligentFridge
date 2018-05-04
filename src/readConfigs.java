import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class readConfigs {
    public static Properties getProperties(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
            return properties;

//            Enumeration enuKeys = properties.keys();
//            while (enuKeys.hasMoreElements()) {
//                String key = (String) enuKeys.nextElement();
//                String value = properties.getProperty(key);
//                System.out.println(key + ": " + value);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}