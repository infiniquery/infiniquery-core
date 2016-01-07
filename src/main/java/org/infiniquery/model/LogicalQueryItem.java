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

/**
 * Value object intended to represent a logical item (or word) within the query.
 * The logical representation of the query is agnostic of its HTML dimension and its JPQL dimension.
 * It is a completely independent representation of the query, which can be used to transfer it from one dimension to
 * another and consists of a sequence of {@link LogicalQueryItem} where the order
 * is important.
 *
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class LogicalQueryItem {

    /**
     * Keys representing the type of a logical query item.
     * These values stringyfied are equal to the CSS class names of the divs representing the items in the HTML dimension.
     */
    public static enum Type {
        findKeyword,
        havingKeyword,
        operatorKeyword,
        conditionSeparatorKeyword,
        openBracket,
        closeBracket,
        entityName,
        entityAttributeName,
        entityAttributeValue
    }

    /** the type of the logical query item; to be decoded using LogicalQueryItem.Type */
    private String type;

    /** the display value of the query item, as seen in the UI (without styling) */
    private String displayValue;

    /**
     * 
     * @return the type of the logical query item; to be decoded using LogicalQueryItem.Type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type the type of the logical query item; to be decoded using LogicalQueryItem.Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return displayValue
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * 
     * @param displayValue
     */
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
