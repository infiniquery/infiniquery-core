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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the {@link DatabaseAccessService}.
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class DefaultDatabaseAccessService implements DatabaseAccessService {

    private String persistenceUnitName;

    private EntityManagerFactory factory;

    private EntityManager em;

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.DatabaseAccessService#retrieveReferenceData(java.lang.String)
     */
    @Override
    public List retrieveReferenceData(String queryString) {
        initEntityManagerIfNull();

        return executeQuery(queryString, new ArrayList<>());
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.DatabaseAccessService#executeQuery(java.lang.String, java.util.List)
     */
    @Override
    public List executeQuery(String queryString, List<?> params) {
        initEntityManagerIfNull();

        Query query = em.createQuery(queryString);
        if(params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }
        List<?> results = query.getResultList();
        return results;
    }

    private void initEntityManagerIfNull() {
        if (em == null) {
            //set the defaults
            persistenceUnitName = "TestUnit";
            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            em = factory.createEntityManager();
        }
    }
}