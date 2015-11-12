package ar.com.fusap.tripledes;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by jualmeyda on 11/8/15.
 */
public class PropertiesConfiguration {

    private Properties props;

    public PropertiesConfiguration() {
        try {
			File configFile = new File("resources/application.properties");
            FileReader reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getDESKey() {
        return props.getProperty("3des.key");
    }

	public String getProcessedFolderPath() {
		return props.getProperty("3des.folder.processed");
    }

	public String getNextFolderPath() {
		return props.getProperty("3des.folder.next");
	}

	public String getErrorFolderPath() {
		return props.getProperty("3des.folder.error");
	}
}
