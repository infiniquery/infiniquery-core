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

/**
 * Data transfer object encapsulating the information necessary on the user interface to display the operators applicable for a specific entity attribute.
 *
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class EntityAttributeOperatorNamesView {

    private String[] atrributOperatorNames;

    /**
     * 
     */
    public EntityAttributeOperatorNamesView() {

    }

    /**
     * 
     * @param atrributeOperatorNames an array of String values representing the aliases of the attribute operators
     */
    public EntityAttributeOperatorNamesView(String[] atrributeOperatorNames) {
        this.atrributOperatorNames = atrributeOperatorNames;
    }

    /**
     * 
     * @return atrributOperatorNames an array of String values representing the aliases of the attribute operators
     */
    public String[] getEntityAttributeOperatorNames() {
        return atrributOperatorNames;
    }

    /**
     * 
     * @param attributeOperatorNames an array of String values representing the aliases of the attribute operators
     */
    public void setAttributeOperatorNames(String[] attributeOperatorNames) {
        this.atrributOperatorNames = attributeOperatorNames;
    }
}
