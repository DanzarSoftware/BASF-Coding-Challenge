package com.alvarozarza.basf.utils;


import com.alvarozarza.basf.exception.XmlDocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public class XmlUtils {


    public static String getStringFromXmlNode(Document document, String nodeName, Boolean removeTags) {
        String result = nodeToString(document.getElementsByTagName(nodeName));
        if (removeTags) {
            return result.replaceAll("<[^>]+>", "");
        }
        return result;
    }

    public static Set<String> getStringFromXmlNodeStringXml(String xml, String nodeName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document xmlDoc = builder.parse(is);
            return nodeListToStringSet(xmlDoc.getElementsByTagName(nodeName));

        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new XmlDocumentException("getStringFromXmlNodeStringXml", e.getMessage());
        }

    }


    public static String getStringFromXmlDocument(Document document) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StringWriter strWriter = new StringWriter();
            StreamResult result = new StreamResult(strWriter);
            transformer.transform(source, result);
            return strWriter.getBuffer().toString().replaceAll("<[^>]+>", "");

        } catch (TransformerException e) {
            throw new XmlDocumentException("getStringFromXmlDocument", e.getMessage());
        }
    }


    public static String getStringFromXmlAttribute(Document document, String attributeName) {
        return document.getDocumentElement().getAttribute(attributeName);
    }

    private static Set<String> nodeListToStringSet(NodeList nodes) {

        Set<String> nodeResults = new HashSet<>();

        for (int i = 0; i < nodes.getLength(); ++i) {
            nodeResults.add(nodes.item(i).getTextContent());
        }
        return nodeResults;
    }

    private static String nodeToString(NodeList nodes) {
        DOMSource source = new DOMSource();
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            for (int i = 0; i < nodes.getLength(); ++i) {
                source.setNode(nodes.item(i));
                transformer.transform(source, result);
            }

            return writer.toString();
        } catch (TransformerException e) {
            throw new XmlDocumentException("nodeToString", e.getMessage());
        }

    }


}
