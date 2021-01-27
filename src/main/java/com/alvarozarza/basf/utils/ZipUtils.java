package com.alvarozarza.basf.utils;


import com.alvarozarza.basf.exception.UnzipException;
import com.alvarozarza.basf.exception.XmlDocumentException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtils {
    public static List<Document> extractXmlsFromZip(MultipartFile multipartFile) {

        List<Document> documents = new ArrayList<>();
        try {
            File file = convert(multipartFile);
            ZipFile zipFile = new ZipFile(file);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zis.getNextEntry();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            while (zipEntry != null) {
                InputStream in = zipFile.getInputStream(zipEntry);
                Document document = docBuilder.parse(in);
                documents.add(document);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            return documents;


        } catch (IOException e) {
            throw new UnzipException(multipartFile.getOriginalFilename(), e.getMessage());
        } catch (ParserConfigurationException | SAXException e) {
            throw new XmlDocumentException("extractXmlsFromZip", e.getMessage());
        }
    }

    // Method to transform MultipartFile to File
    private static File convert(MultipartFile file) throws IOException {

        File tempFile = File.createTempFile("upload", null);
        file.transferTo(tempFile);
        return tempFile;
    }

}
