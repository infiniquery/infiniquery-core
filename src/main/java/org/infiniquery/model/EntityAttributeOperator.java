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
 * Enum representing possible operators that can be used between an entity attribute and the values we filter it by.
 * @author Daniel Doboga
 * @since 1.0.0
 */
public enum EntityAttributeOperator {

    EQUALS ("=", "=", EntityAttributeOperatorType.SINGLE_VALUE),
    LIKE ("like", "LIKE", EntityAttributeOperatorType.SINGLE_VALUE),
    IN ("in", "IN", EntityAttributeOperatorType.MULTI_VALUE),
    GREATER_THAN ("greater than", ">", EntityAttributeOperatorType.SINGLE_VALUE),
    LOWER_THAN ("lower than", "<", EntityAttributeOperatorType.SINGLE_VALUE),
    GREATER_THAN_OR_EQUALS ("greater than or equal to", ">=", EntityAttributeOperatorType.SINGLE_VALUE),
    LOWER_THAN_OR_EQUALS ("lower than or equal to", "<=", EntityAttributeOperatorType.SINGLE_VALUE),
    BEFORE ("before", "<", EntityAttributeOperatorType.SINGLE_VALUE),
    AFTER ("after", ">", EntityAttributeOperatorType.SINGLE_VALUE);

    private String displayName;
    private String value;
    private EntityAttributeOperatorType type;

    private static final Map<String, EntityAttributeOperator> lookupByDisplayName = new HashMap<String, EntityAttributeOperator>();
    private static final Map<String, EntityAttributeOperator> lookupByValue = new HashMap<String, EntityAttributeOperator>();

    static {
        for(EntityAttributeOperator operator : EnumSet.allOf(EntityAttributeOperator.class)) {
            lookupByDisplayName.put(operator.getDisplayName(), operator);
            lookupByDisplayName.put(operator.getValue(), operator);
        }
    }

    private EntityAttributeOperator(String displayName, String value, EntityAttributeOperatorType type) {
        this.displayName = displayName;
        this.value = value;
        this.type = type;
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
     * 
     * @return type
     */
    public EntityAttributeOperatorType getType() {
        return type;
    }

    /**
     * 
     * @param displayName the displayable alias
     * @return the EntityAttributeOperator having the given displayName
     */
    public static EntityAttributeOperator getByDisplayName(String displayName) {
        return lookupByDisplayName.get(displayName);
    }

    /**
     * 
     * @param value the value of the operator that represents it in the query language
     * @return the EntityAttributeOperator having the given value
     */
    public static EntityAttributeOperator getByValue(String value) {
        return lookupByValue.get(value);
    }

}
