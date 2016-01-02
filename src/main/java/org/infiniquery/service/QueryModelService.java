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

package org.infiniquery.service;

import org.infiniquery.model.ExecutableQuery;
import org.infiniquery.model.view.*;

import java.util.List;

/**
 * Service empowering the interaction of above layers (e.g. frontend or MVC controllers) with the infiniquery model.
 * @author Daniel Doboga
 * @since 1.0
 */
public interface QueryModelService {
	
	/**
	 * Get the display value of the "Find" keyword the query is starting with.
	 * @return {@link java.lang.String}
	 */
	public String getFindKeyword();
	
    /**
     * Get the names of all available entities.
     * @return {@link java.util.List} of {@link String}
     */
    public List<String> getEntityDisplayNames();

    /**
     * Get the information necessary in the user interface to display the names of all available entities.
     * @return {@link org.infiniquery.model.view.EntityDisplayNamesView}
     */
    public EntityDisplayNamesView getEntityDisplayNamesView();

    /**
     * Get the names of the attributes available for the given entity, to be used as filters.
     * 
     * @param entityDisplayName the name of the entity to get the attributes for
     * @return {@link java.util.List} of {@link String}
     */
    public List<String> getEntityAttributeDisplayNames(String entityDisplayName);

    /**
     * Get the information necessary in the user interface to display the names of the attributes available for a given entity.
     * @param entityDisplayName the name of the entity to get the attributes for
     * @return {@link org.infiniquery.model.view.EntityAttributeDisplayNamesView}
     */
    public EntityAttributeDisplayNamesView getEntityAttributeDisplayNamesView(String entityDisplayName);

    /**
     * Get the names of the operators applicable for filtering the values of a particular entity attribute.
     * @param entityDisplayName the name of the entity
     * @param attributeDisplayName the name of the attribute from the entity
     * @return an array of {@link String}
     */
    public String[] getEntityAttributeOperatorNames(String entityDisplayName, String attributeDisplayName);

    /**
     * Get the information necessary in the user interface to display the names of the operators applicable for filtering the values of a particular entity attribute.
     * @param entityDisplayName the name of the entity
     * @param attributeDisplayName the name of the attribute from the entity
     * @return {@link org.infiniquery.model.view.EntityAttributeOperatorNamesView}
     */
    public EntityAttributeOperatorNamesView getEntityAttributeOperatorNamesView(String entityDisplayName, String attributeDisplayName);

    public PossibleValuesView getEntityAttributeOperatorValue(String entityDisplayName, String attributeDisplayName, String operatorDisplayName);

    /**
     * Get the display names of all available condition separators keywords.
     * @return an array of {@link String}
     */
    public String[] getConditionSeparatorNames();

    /**
     * Get the information necessary to the user interface to display the available condition separator options.
     * @return {@link org.infiniquery.model.view.ConditionSeparatorNamesView}
     */
    public ConditionSeparatorNamesView getConditionSeparatorNamesView();

    /**
     * Execute a query against the database.
     * @param executableQuery an instance of {@link org.infiniquery.model.ExecutableQuery} which needs to have filled in (at least) the logical dimension.
     * @return an instance of {@link org.infiniquery.model.view.QueryResultsView} containing the results of the query execution.
     */
    public QueryResultsView executeQuery(ExecutableQuery executableQuery);

    /**
     * Compiles a query and returns it, translated into the query language supported by the underlying persistence layer.
     * 
     * @param executableQuery an instance of {@link org.infiniquery.model.ExecutableQuery} which needs to have filled in (at least) the logical dimension.
     * @return The compiled query, as String, expressed into the query language supported by the underlying persistence layer.
     */
    public String compileQuery(ExecutableQuery executableQuery);

    /**
     * Register a security service with this query model service.
     * For example, you can register your own implementation of {@link org.infiniquery.service.SecurityService} to tell it how to determine the role of the current logged user.
     * If this method is not used to register a custom security service, the default security service will be used, which always allows the maximum access rights to any user.
     * @param securityService
     */
    public void registerSecurityService(SecurityService securityService);

    /**
     * Reload the query context configuration. This operation should be triggered when we want to change the query settings at runtime.
     */
    public void reloadQueryContext();

    public void setDatabaseAccessService(DatabaseAccessService databaseAccessService);

    public SecurityService getSecurityService();

	public void setSecurityService(SecurityService securityService);

	public DatabaseAccessService getDatabaseAccessService();

}
