package org.joesoft.timetogohomelogic.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReaderImpl implements PropertyReader {

    public static final String PROPERTY_FILE_LOCATION = "main.properties";

    private final Properties properties;

    public PropertyReaderImpl() throws FileNotFoundException, IOException {
        properties = new Properties();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_LOCATION);
        properties.load(resourceAsStream);
        resourceAsStream.close();
    }

    @Override
    public String getProperty(PropertyReader.PropertyName key) {
        return properties.getProperty(key.getPath());
    }
}
