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
 * @since 1.0
 */
public class ExecutableQuery {
    private String htmlDimension;
    private String plainTextDimension;
    private LogicalQueryItem[] logicalDimension;
    private String jpqlDimension;
    private List<?> jpqlParams;

    public String getHtmlDimension() {
        return htmlDimension;
    }

    public void setHtmlDimension(String htmlDimension) {
        this.htmlDimension = htmlDimension;
    }

    public String getPlainTextDimension() {
        return plainTextDimension;
    }

    public void setPlainTextDimension(String plainTextDimension) {
        this.plainTextDimension = plainTextDimension;
    }

    public LogicalQueryItem[] getLogicalDimension() {
        return logicalDimension;
    }

    public void setLogicalDimension(LogicalQueryItem[] logicalDimension) {
        this.logicalDimension = logicalDimension;
    }

    public String getJpqlDimension() {
        return jpqlDimension;
    }

    public void setJpqlDimension(String jpqlDimension) {
        this.jpqlDimension = jpqlDimension;
    }

    public List<?> getJpqlParams() {
        return jpqlParams;
    }

    public void setJpqlParams(List<?> jpqlParams) {
        this.jpqlParams = jpqlParams;
    }
}
