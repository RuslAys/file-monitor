package org.monitor.xml_parser;

import org.monitor.model.AvTimeUser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlWriter {
    public void write(List<List<AvTimeUser>> data, String pathname){
        try{
            Document document = createDocument(data);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(pathname));

            transformer.transform(source, result);
            System.out.println("Saved to " + pathname);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Document createDocument(List<List<AvTimeUser>> data) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("output");
        document.appendChild(rootElement);

        for(int i = 0; i < data.size(); i++){
            String dateHeader = data.get(i).get(0).getDateFormat();
            Element logDay = document.createElement("logday");
            rootElement.appendChild(logDay);

            Element day = document.createElement("day");
            logDay.appendChild(day);
            day.appendChild(document.createTextNode(dateHeader));

            Element usersNode = document.createElement("users");
            logDay.appendChild(usersNode);
            for(int j = 0; j < data.get(i).size(); j++){
                Element userNode = document.createElement("user");
                usersNode.appendChild(userNode);

                Element id = document.createElement("id");
                Element url = document.createElement("url");
                Element average = document.createElement("average");

                userNode.appendChild(id);
                userNode.appendChild(url);
                userNode.appendChild(average);

                id.appendChild(document.createTextNode(
                        data.get(i).get(j).getUserID()
                ));
                url.appendChild(document.createTextNode(
                        data.get(i).get(j).getURL()
                ));
                average.appendChild(document.createTextNode(
                        Long.toString(data.get(i).get(j).getAvTime())
                ));
            }
        }
        return document;
    }
}
