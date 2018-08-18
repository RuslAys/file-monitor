package org.monitor.xml_parser;

import org.monitor.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class XmlReader {
    public List<User> parse(String filepath){
        List<User> users = new LinkedList<>();
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList logList = document.getElementsByTagName("log");

            for(int i = 0; i < logList.getLength(); i++){
                users.add(getUser(logList.item(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(users, (User o1, User o2) ->
                Long.compare(o1.getTimestamp(), o2.getTimestamp())
        );

        xmlFile.delete();
        return users;
    }

    private User getUser(Node node){
        User user = new User();
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            user.setIdUser(getTagValue("userId", element));
            user.setTimestamp(getTagValue("timestamp", element));
            user.setURL(getTagValue("url", element));
            user.setTime(getTagValue("seconds", element));
        }
        return user;
    }

    private String getTagValue(String tag, Element element){
        NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) list.item(0);
        return node.getNodeValue();
    }
}
