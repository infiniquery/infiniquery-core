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

package org.infiniquery.model;

import java.util.List;

/**
 * Bean representation of the context in which the queries will be generated and executed. 
 * The context contains the information read from the configuration file.
 *
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class InfiniqueryContext {

	private String findKeyword;
	private List<JpaEntity> entities;

	/**
	 * 
	 * @return findKeyword the alias to be used in UI for the "SELECT" keyword
	 */
	public String getFindKeyword() {
		return findKeyword;
	}

	/**
	 * 
	 * @param findKeyword the alias to be used in UI for the "SELECT" keyword
	 */
	public void setFindKeyword(String findKeyword) {
		this.findKeyword = findKeyword;
	}

	/**
	 * 
	 * @return entities a List of {@link JpaEntity} objects representing the entities existing in this context
	 */
	public List<JpaEntity> getEntities() {
		return entities;
	}

	/**
	 * 
	 * @param entities a List of {@link JpaEntity} objects representing the entities to set.
	 */
	public void setEntities(List<JpaEntity> entities) {
		this.entities = entities;
	}
}
