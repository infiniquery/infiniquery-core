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

package org.infiniquery.model.view;

import java.util.LinkedHashMap;

/**
 * Representation of a query result entity instance.
 */
public class QueryResultItem {

	private LinkedHashMap<String, Object> attributesMap = new LinkedHashMap<>();
	
	/**
	 * Get the value of an instance attribute.
	 * 
	 * @param attributeName the String name of the attribute to get the value for.
	 * @return Object representing the value of the attribute denoted by the given name.
	 */
	public Object get(String attributeName) {
		
		return attributesMap.get(attributeName);
	}
	
	/**
	 * Set the value of an instance attribute.
	 * 
	 * @param attributeName the String name of the attribute
	 * @param value the value to set
	 */
	public void add(String attributeName, Object value) {

		attributesMap.put(attributeName, value);
	}
	
	/**
	 * Remove an attribute of this instance.
	 * 
	 * @param attributeName the name of the attribute to be removed.
	 */
	public void remove(String attributeName) {
		attributesMap.remove(attributeName);
	}
	
}
