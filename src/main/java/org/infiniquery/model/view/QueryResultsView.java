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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representation of the results of a dynamic query execution.
 * This bean contains both the results list and the display name of the entity type that has been requested.
 * The latter can be used to identify the functions to be used for handling the presentation of the results in UI.
 * 
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class QueryResultsView {

    /** The entityDisplayName (aka JpaEntity.displayName) representing the type of the entity that has been requested */
    private String entityName;
    
    private List<QueryResultItem> items;
    
    /**
     * 
     * @param size the number of items in this result view
     */
    public QueryResultsView(int size) {
    	this.items = new ArrayList<>(size);
    }
    
    /**
     * 
     * @param item the query result item to add
     */
    public void addEntity(QueryResultItem item) {
    	items.add(item);
    }

    /**
     * 
     * @return entityName the entity (type) display name
     */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * 
	 * @param entityName the entity (type) display name
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 
	 * @return items a List of QueryResultItem objects
	 */
	public List<QueryResultItem> getItems() {
		return items;
	}

	/**
	 * 
	 * @param items a List of QueryResultItem objects
	 */
	public void setItems(List<QueryResultItem> items) {
		this.items = items;
	}
    
}
