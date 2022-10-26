package com.kta.newrelic.phrases;

import com.kta.newrelic.exceptions.FileLoadingException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class FileLoader {

    /**
     * loads file using default charset and 64k byte buffer
     *
     * @param filename path of file that will be loaded
     * @return file content
     */
    public String loadFile(String filename) throws FileLoadingException {
        String fileContent;

        try (
                FileInputStream fis = new FileInputStream(filename);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                BufferedInputStream bfis = new BufferedInputStream(fis)
        ) {
            int len;
            byte[] buffer = new byte[65536];
            while ((len = bfis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            fileContent = os.toString(Charset.defaultCharset());
        } catch (IOException e) {
            String errorMsg = String.format("unable to load file %s", filename);
            throw new FileLoadingException(errorMsg, e);
        }

        return fileContent;
    }
}
