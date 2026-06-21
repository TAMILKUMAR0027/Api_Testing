package com.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

    Properties prop;

    public PropertyFileReader() {

        try {
            FileInputStream fis = new FileInputStream(
                    "src/test/resources/testdata.properties");

            prop = new Properties();

            prop.load(fis);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public String getData(String key) {

        return prop.getProperty(key);
    }
}
