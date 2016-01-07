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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing possible values of condition separator keywords.
 * 
 * @author Daniel Doboga
 * @since 1.0.0
 */
public enum ConditionSeparatorKeyword {

    AND ("and", "AND"),
    OR ("or", "OR");

    private String displayName;
    private String value;

    private static final Map<String, ConditionSeparatorKeyword> lookupByDisplayName = new HashMap<String, ConditionSeparatorKeyword>();
    private static final Map<String, ConditionSeparatorKeyword> lookupByValue = new HashMap<String, ConditionSeparatorKeyword>();

    static {
        for(ConditionSeparatorKeyword separator : EnumSet.allOf(ConditionSeparatorKeyword.class)) {
            lookupByDisplayName.put(separator.getDisplayName(), separator);
            lookupByDisplayName.put(separator.getValue(), separator);
        }
    }

    private ConditionSeparatorKeyword(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    /**
     * 
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Get a ConditionSeparatorKeyword by its displayName.
     * @param displayName
     * @return the ConditionSeparatorKeyword having the given displayName.
     */
    public static ConditionSeparatorKeyword getByDisplayName(String displayName) {
        return lookupByDisplayName.get(displayName);
    }

    /**
     * Get a ConditionSeparatorKeyword by its value.
     * @param value
     * @return the ConditionSeparatorKeyword having the given value.
     */
    public static ConditionSeparatorKeyword getByValue(String value) {
        return lookupByValue.get(value);
    }
}
