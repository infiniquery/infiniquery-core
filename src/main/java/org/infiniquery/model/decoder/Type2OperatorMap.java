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
import java.util.Map;

import org.infiniquery.model.EntityAttributeOperator;

/**
 * Decoder class intended to map the relationship between JPA entity attribute types and their applicable operators.
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class Type2OperatorMap {

    @SuppressWarnings({ "serial", "rawtypes"})
	private static Map<Class, EntityAttributeOperator[]> map = new HashMap<Class, EntityAttributeOperator[]>() {{
        put(int.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(long.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(short.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(float.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(double.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(Integer.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(Long.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(Short.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(Float.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(Double.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(BigInteger.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(BigDecimal.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.GREATER_THAN,
                EntityAttributeOperator.LOWER_THAN,
                EntityAttributeOperator.GREATER_THAN_OR_EQUALS,
                EntityAttributeOperator.LOWER_THAN_OR_EQUALS
        } );
        put(String.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.EQUALS,
                EntityAttributeOperator.CONTAINS,
                EntityAttributeOperator.LIKE,
                EntityAttributeOperator.IN
        } );
        put(java.util.Date.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.BEFORE,
                EntityAttributeOperator.AFTER,
                EntityAttributeOperator.EQUALS
        } );
        put(java.sql.Date.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.BEFORE,
                EntityAttributeOperator.AFTER,
                EntityAttributeOperator.EQUALS
        } );
        put(java.time.LocalDate.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.BEFORE,
                EntityAttributeOperator.AFTER,
                EntityAttributeOperator.EQUALS
        } );
        put(java.sql.Timestamp.class, new EntityAttributeOperator[] {
                EntityAttributeOperator.BEFORE,
                EntityAttributeOperator.AFTER,
                EntityAttributeOperator.EQUALS
        } );
        Class<?> jodaDateTimeClass = resolveClass("org.joda.time.DateTime");
        if(jodaDateTimeClass != null) {
            put(jodaDateTimeClass, new EntityAttributeOperator[]{
                    EntityAttributeOperator.BEFORE,
                    EntityAttributeOperator.AFTER,
                    EntityAttributeOperator.EQUALS
            });
        }
        put(Collection.class, new EntityAttributeOperator[] {
                //EntityAttributeOperator.EQUALS,
                //EntityAttributeOperator.LIKE,
                EntityAttributeOperator.IN
        } );
    }};

    /**
     * Get the applicable operators for an entity attribute of the given type.
     * @param attributeType the java {@link java.lang.Class} representing the type of the entity attribute to reolve applicable operators for.
     * @return an array of {@link org.infiniquery.model.EntityAttributeOperator} representing the applicable operators that can be used between an attribute of the given type and the values we want to filter it for.
     */
    public static EntityAttributeOperator[] getApplicableOperatorsForType(Class<?> attributeType) {
        if(map.containsKey(attributeType)) {
            return map.get(attributeType);
        } else if(Collection.class.isAssignableFrom(attributeType)) {
            //this covers Collection and any subclass of it
            return map.get(Collection.class);
        } else if(isEntity(attributeType)){
            //any possible entity; 
        	//the same operators are applicable as for collections of entities
        	return map.get(Collection.class);
        } else {
            throw new RuntimeException("Not implemented entity types yet");
        }
    }

}
