package com.example.demo.tcp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;


public class XmlTool {
	/**
     * String turn org.dom4j.Document
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Document strToDocument(String xml){
        try {
        	//The xml tag is added to get the outermost tag, and you can remove it if you don't need it
		//	return DocumentHelper.parseText("<xml>"+xml+"</xml>");
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
    }
 
    /**
     * org.dom4j.Document turn  com.alibaba.fastjson.JSONObject
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject documentToJSONObject(String xml){
		return elementToJSONObject(strToDocument(xml).getRootElement());
    }
 
    /**
     * org.dom4j.Element turn  com.alibaba.fastjson.JSONObject
     * @param node
     * @return
     */
    public static JSONObject elementToJSONObject(Element node) {
        JSONObject result = new JSONObject();
        // The name, text content, and attributes of the current node
        List<Attribute> listAttr = node.attributes();// A list of all the attributes of the current node
        for (Attribute attr : listAttr) {// Iterate over all properties of the current node
            result.put(attr.getName(), attr.getValue());
        }
        // Recursively iterate over all the children of the current node
        List<Element> listElement = node.elements();// A list of all level-1 children
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// Iterate over all level-1 child nodes
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // Determine whether a level-1 node has attributes and children
                    result.put(e.getName(), e.getTextTrim());// If not, the current node is treated as a property of the parent node
                else {
                    if (!result.containsKey(e.getName())) // Attribute that determines whether the parent node has the name of the level 1 node
                        result.put(e.getName(), new JSONArray());// If not, create
                    ((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// Put the level-1 node into the value corresponding to the attribute of the node name
                }
            }
        }
        return result;
    }

    public static String addXmlHead(String content) {
		return "<?xml version=\"1.0\" encoding=\" UTF-8\" ?>"+content;
	}
}
