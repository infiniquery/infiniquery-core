/*
* Copyright (c) 2015, Daniel Doboga
* All rights reserved.
* 	
* Redistribution and use in source and binary forms, with or without modification, 
* are permitted provided that the following conditions are met:
* 
*   1. Redistributions of source code must retain the above copyright notice, this 
*   list of conditions and the following disclaimer.
*   
*   2. Redistributions in binary form must reproduce the above copyright notice, this 
*   list of conditions and the following disclaimer in the documentation and/or other 
*   materials provided with the distribution.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
* IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
* INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
* NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
* POSSIBILITY OF SUCH DAMAGE.
*/

package org.infiniquery.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.infiniquery.model.EntityAttribute;
import org.infiniquery.model.InfiniqueryContext;
import org.infiniquery.model.JpaEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Connector of the Infiniquery framework for working with Java Persistence API Query Language.
 * 
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class JpaConnector {

	private static final String PERSISTENCE_UNIT_NAME = "TestUnit";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;
	private static final EntityManager em = factory.createEntityManager();

	/**
	 * Retrieve the {@link InfiniqueryContext}, by reading the infiniquery-config.xml configuration file. 
	 * This file is expected to be found in the root of the application's classpath.
	 * 
	 * @return an instance of {@link InfiniqueryContext} representing the content of the configuration file.
	 * @throws ParserConfigurationException if the xml file is invalid
	 */
	public static InfiniqueryContext getDynamicQueryContext() throws ParserConfigurationException {
		InputStream configInputStream = JpaConnector.class.getClassLoader().getResourceAsStream("infiniquery-config.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = null;
	    try {
	        doc = dBuilder.parse(configInputStream);
		    Element documentElement = doc.getDocumentElement();
		    documentElement.normalize();
		    
		    InfiniqueryContext context = new InfiniqueryContext();
		    context.setFindKeyword(extractFindKeyword(doc));
		    context.setEntities(Collections.unmodifiableList(extractEntities(doc)));
		    
		    return context;
	    } catch (Exception e) {
	        throw new RuntimeException(e.getMessage(), e);
	    }

	}
	
	private static String extractFindKeyword(Document doc) {
		NodeList nodeList = doc.getElementsByTagName("findKeyword");
		if(nodeList == null || nodeList.getLength() == 0) {
			throw new RuntimeException("Invalid configuration file! No definition for findKeyword could be found.");
		} else if(nodeList.getLength() != 1) {
			throw new RuntimeException("Invalid configuration file! More than one findKeyword found.");
		} else {
			final String findKeyword = nodeList.item(0).getTextContent();
			if(findKeyword == null || findKeyword.isEmpty()) {
				throw new RuntimeException("Invalid configuration file! Empty findKeyword tag. You need to specify a value.");
			} else {
				return findKeyword;
			}
		}
	}
	
	private static List<JpaEntity> extractEntities(Document doc) {
	    NodeList entityNodes = doc.getElementsByTagName("entity");
	    final int entitiesCount = entityNodes.getLength();
		List<JpaEntity> entities = new ArrayList<>(entitiesCount);
	    for(int i=0; i<entitiesCount; i++) {
	    	Node entityNode = entityNodes.item(i);
	    	NamedNodeMap attributes = entityNode.getAttributes();
	    	JpaEntity entity = new JpaEntity(
	    			attributes.getNamedItem("className").getNodeValue(),
	    			attributes.getNamedItem("displayName").getNodeValue(),
	    			attributes.getNamedItem("roles") == null ? null : attributes.getNamedItem("roles").getNodeValue(),
	    			extractAttributes(entityNode)
	    			);
	    	entities.add(entity);
	    }
	    return entities;
	}

	private static List<EntityAttribute> extractAttributes(Node entityNode) {
		NodeList attributeNodes = ((Element)entityNode).getElementsByTagName("attribute");
		List<EntityAttribute> attributeList = new ArrayList<>(attributeNodes.getLength());
		for(int i=0; i<attributeNodes.getLength(); i++) {
			Node attributeNode = attributeNodes.item(i);
			NamedNodeMap attributes = attributeNode.getAttributes();
			EntityAttribute entityAttribute = new EntityAttribute(
					attributes.getNamedItem("attributeName").getNodeValue(),
					attributes.getNamedItem("displayName").getNodeValue(),
					attributes.getNamedItem("roles") == null ? null : attributes.getNamedItem("roles").getNodeValue(),
					attributes.getNamedItem("possibleValuesQuery") == null ? null : attributes.getNamedItem("possibleValuesQuery").getNodeValue(),
					attributes.getNamedItem("possibleValueLabelAttribute") == null ? null : attributes.getNamedItem("possibleValueLabelAttribute").getNodeValue(),
					attributes.getNamedItem("possibleValueLabelAttributePath") == null ? null : attributes.getNamedItem("possibleValueLabelAttributePath").getNodeValue(),
					new Boolean(attributes.getNamedItem("displayOnly") == null ? null : attributes.getNamedItem("displayOnly").getNodeValue()) 
					);
			attributeList.add(entityAttribute);
		}
		return attributeList;
	}

	private static List<String> extractHardcodedAttributePossibleValues(NamedNodeMap attributes) {
		String[] possibleValuesArray = attributes.getNamedItem("possibleValues").getNodeValue().split(",");
		List<String> possibleValues = new ArrayList<>(possibleValuesArray.length);
		for(String possibleValue : possibleValuesArray) {
			possibleValues.add(possibleValue.trim());
		}
		return possibleValues;
	}

	private static List<?> extractAttributePossibleValuesFromSource(NamedNodeMap attributes) {
		String sourceEntityName = attributes.getNamedItem("possibleValuesSource").getNodeValue().trim();
		Query query = em.createQuery("select e from " + sourceEntityName + " e");
		return query.getResultList();
	}

	private static List<?> extractAttributePossibleValuesFromQuery(NamedNodeMap attributes) {
		String possibleValuesQuery = attributes.getNamedItem("possibleValuesQuery").getNodeValue().trim();
		Query query = em.createQuery(possibleValuesQuery);
		return query.getResultList();
	}

    /**
     * Read the content from an InputStream, assuming it is a stream serving only text.
     * @param inputStream
     * @return the full text from the given InputStream
     */
	private static String readResourceContent(InputStream inputStream) {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			return stringBuilder.toString();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
