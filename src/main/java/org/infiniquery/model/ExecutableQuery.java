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
 * Value object representing a query as it has been build from the UI.
 *
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class ExecutableQuery {
    private String htmlDimension;
    private String plainTextDimension;
    private LogicalQueryItem[] logicalDimension;
    private String jpqlDimension;
    private List<?> jpqlParams;

    /**
     * 
     * @return the HTML dimension of this ExecutableQuery.
     */
    public String getHtmlDimension() {
        return htmlDimension;
    }

    /**
     * 
     * @param htmlDimension the HTML dimension to set on this ExecutableQuery.
     */
    public void setHtmlDimension(String htmlDimension) {
        this.htmlDimension = htmlDimension;
    }

    /**
     * 
     * @return the plain text dimension of this ExecutableQuery.
     */
    public String getPlainTextDimension() {
        return plainTextDimension;
    }

    /**
     * 
     * @param plainTextDimension the plain text dimension to set on this ExecutableQuery.
     */
    public void setPlainTextDimension(String plainTextDimension) {
        this.plainTextDimension = plainTextDimension;
    }

    /**
     * 
     * @return LogicalQueryItem[] representing the logical representation of this ExecutableQuery
     */
    public LogicalQueryItem[] getLogicalDimension() {
        return logicalDimension;
    }

    /**
     * 
     * @param logicalDimension the logical dimension to set for this ExecutableQuery
     */
    public void setLogicalDimension(LogicalQueryItem[] logicalDimension) {
        this.logicalDimension = logicalDimension;
    }

    /**
     * 
     * @return the JPQL representation of this ExecutableQuery
     */
    public String getJpqlDimension() {
        return jpqlDimension;
    }

    /**
     * 
     * @param jpqlDimension the JPQL representation to set for this ExecutableQuery
     */
    public void setJpqlDimension(String jpqlDimension) {
        this.jpqlDimension = jpqlDimension;
    }

    /**
     * 
     * @return the parameters for executing the prepared statement represented by the JPQL dimension of this ExecutableQuery
     */
    public List<?> getJpqlParams() {
        return jpqlParams;
    }

    /**
     * 
     * @param jpqlParams the parameters for executing the prepared statement represented by the JPQL dimension of this ExecutableQuery
     */
    public void setJpqlParams(List<?> jpqlParams) {
        this.jpqlParams = jpqlParams;
    }
}
