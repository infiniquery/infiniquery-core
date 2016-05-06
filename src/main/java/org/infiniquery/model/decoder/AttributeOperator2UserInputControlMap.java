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

package org.infiniquery.model.decoder;

import static org.infiniquery.util.Utils.isEntity;
import static org.infiniquery.util.Utils.resolveClass;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.infiniquery.model.EntityAttributeOperator;
import org.infiniquery.model.EntityAttributeOperatorType;
import org.infiniquery.model.UserInputControlType;

/**
 * Decoder class intended to retrieve user input control types that are applicable for a given entity attribute type and entity attribute operator.
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class AttributeOperator2UserInputControlMap {

    /**
     * Cases where the attribute type is enough for determining the type of input control.
     */
    private static Map<Class, UserInputControlType> mappedByType = new HashMap<Class, UserInputControlType>() {{
        put(int.class, UserInputControlType.NUMBER_INPUT);
        put(long.class, UserInputControlType.NUMBER_INPUT);
        put(short.class, UserInputControlType.NUMBER_INPUT);
        put(float.class, UserInputControlType.NUMBER_INPUT);
        put(double.class, UserInputControlType.NUMBER_INPUT);
        put(Integer.class, UserInputControlType.NUMBER_INPUT);
        put(Long.class, UserInputControlType.NUMBER_INPUT);
        put(Short.class, UserInputControlType.NUMBER_INPUT);
        put(Float.class, UserInputControlType.NUMBER_INPUT);
        put(Double.class, UserInputControlType.NUMBER_INPUT);
        put(BigInteger.class, UserInputControlType.NUMBER_INPUT);
        put(BigDecimal.class, UserInputControlType.NUMBER_INPUT);
        put(java.util.Date.class, UserInputControlType.DATE_TIME_INPUT);
        put(java.sql.Date.class, UserInputControlType.DATE_INPUT);
        put(java.time.LocalDate.class, UserInputControlType.DATE_INPUT);
        put(java.sql.Timestamp.class, UserInputControlType.DATE_TIME_INPUT);
        Class<?> jodaDateTimeClass = resolveClass("org.joda.time.DateTime");
        if(jodaDateTimeClass != null) {
            put(jodaDateTimeClass, UserInputControlType.DATE_TIME_INPUT);
        }
    }};

    /**
     * Cases where besides the attribute type, we also need the operator type for determining the type of input control.
     */
    private static Map<AttributeTypeOperatorKey, UserInputControlType> mappedByTypeAndOperator = new HashMap<AttributeTypeOperatorKey, UserInputControlType>() {{
        put(new AttributeTypeOperatorKey(String.class, EntityAttributeOperatorType.SINGLE_VALUE), UserInputControlType.FREE_TEXT_INPUT_SINGLE_VALUE);
        put(new AttributeTypeOperatorKey(String.class, EntityAttributeOperatorType.MULTI_VALUE), UserInputControlType.FREE_TEXT_INPUT_MULTIPLE_VALUES);

        put(new AttributeTypeOperatorKey(Collection.class, EntityAttributeOperatorType.SINGLE_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_SINGLE_VALUE);
        put(new AttributeTypeOperatorKey(List.class, EntityAttributeOperatorType.SINGLE_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_SINGLE_VALUE);
        put(new AttributeTypeOperatorKey(Set.class, EntityAttributeOperatorType.SINGLE_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_SINGLE_VALUE);
        put(new AttributeTypeOperatorKey(Collection.class, EntityAttributeOperatorType.MULTI_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_MULTIPLE_VALUES);
        put(new AttributeTypeOperatorKey(List.class, EntityAttributeOperatorType.MULTI_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_MULTIPLE_VALUES);
        put(new AttributeTypeOperatorKey(Set.class, EntityAttributeOperatorType.MULTI_VALUE), UserInputControlType.REFERENCE_DATA_INPUT_MULTIPLE_VALUES);
    }};

    /**
     * Get the applicable {@link org.infiniquery.model.UserInputControlType} for a given entity attribute type and {@link org.infiniquery.model.EntityAttributeOperator}.
     * @param attributeType the {@link java.lang.Class} representing the type of an entity attribute.
     * @param operator the {@link org.infiniquery.model.EntityAttributeOperator} representing the operator to apply for filtering the given attribute by values.
     * @return the applicable {@link org.infiniquery.model.UserInputControlType} for given attributeType and operator.
     */
    public static UserInputControlType decode(Class<?> attributeType, EntityAttributeOperator operator) {
        UserInputControlType decoded = mappedByType.get(attributeType);
        if(decoded == null) {
            decoded = mappedByTypeAndOperator.get(new AttributeTypeOperatorKey(attributeType, operator.getType()));
        }
        if(decoded == null && isEntity(attributeType)) {
        	//if this is an entity, then the same applies as for collections of entities
        	decoded = mappedByTypeAndOperator.get(new AttributeTypeOperatorKey(Collection.class, operator.getType()));
        }
        return decoded;
    }

    private static class AttributeTypeOperatorKey {
        Class<?> attributeType;
        EntityAttributeOperatorType operator;

        public AttributeTypeOperatorKey(Class<?> attributeType, EntityAttributeOperatorType operator) {
            this.attributeType = attributeType;
            this.operator = operator;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AttributeTypeOperatorKey)) return false;

            AttributeTypeOperatorKey that = (AttributeTypeOperatorKey) o;

            if (attributeType != null ? !attributeType.equals(that.attributeType) : that.attributeType != null)
                return false;
            if (operator != that.operator) return false;

            return true;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            int result = attributeType != null ? attributeType.hashCode() : 0;
            result = 31 * result + (operator != null ? operator.hashCode() : 0);
            return result;
        }
    }

}
