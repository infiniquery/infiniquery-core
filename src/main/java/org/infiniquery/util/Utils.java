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

package org.infiniquery.util;

import java.lang.annotation.Annotation;

/**
 * Class containing utility static methods.
 * 
 * @author Daniel Doboga
 * @since 1.0.0
 *
 */
public class Utils {

	/**
	 * Get a class by its fully qualified class name.
	 * 
	 * @param fullyQualifiedName the fully qualified name of the Class
	 * @return the class, or null if not found
	 */
    public static Class<?> resolveClass(String fullyQualifiedName) {
        try {
            return Class.forName(fullyQualifiedName);
        } catch(ClassNotFoundException e) {
            return null;
        }
    }

	/**
	 * Check if a given {@link Class} denotes a JPA entity. 
	 * The verification is done by inspecting the class annotations, if any, via reflection.
	 * 
	 * @param attributeType the {@link Class} to be inspected.
	 * @return true if the given class is mapped as a JPA entity, false otherwise.
	 */
	public static boolean isEntity(Class<?> attributeType) {
    	if(attributeType == null) {
    		return false;
    	}
    	Annotation[] annotations = attributeType.getAnnotationsByType(javax.persistence.Table.class);
    	if(annotations.length == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
}
