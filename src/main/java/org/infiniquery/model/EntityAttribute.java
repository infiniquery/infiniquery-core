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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Value object bean representing an attribute of a JPA entity.
 * @author Daniel Doboga
 * @since 1.0
 */
public class EntityAttribute {

	private JpaEntity parentEntity;

	private String attributeName;

	private String displayName;

	private String roles;

	private Set<String> roleSet;

	private Set<String> applicableRoles;

	private String possibleValuesQuery;

	private String possibleValueLabelAttribute;

	private String possibleValueLabelAttributePath;
	
	private boolean displayOnly;

	public EntityAttribute(String attributeName, String displayName, final String roles, String possibleValuesQuery, String possibleValueLabelAttribute, String possibleValueLabelAttributePath, boolean displayOnly) {
		super();
		this.attributeName = attributeName;
		this.displayName = displayName;
		this.roles = roles;
		this.displayOnly = displayOnly;
		this.roleSet = Collections.unmodifiableSet(new HashSet<String>() {
			{
				if (roles != null && !roles.isEmpty()) {
					String[] roleValues = roles.split(",");
					for (String role : roleValues) {
						add(role.trim());
					}
				}
			}
		});
		this.possibleValuesQuery = possibleValuesQuery;
		this.possibleValueLabelAttribute = possibleValueLabelAttribute;
		if(possibleValuesQuery != null) {
			this.possibleValueLabelAttributePath = possibleValueLabelAttributePath != null ? possibleValueLabelAttributePath : attributeName + "." + possibleValueLabelAttribute;
		}
	}

	void setParentEntity(JpaEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public JpaEntity getParentEntity() {
		return parentEntity;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getRoles() {
		return roles;
	}

	public Set<String> getRoleValues() {
		return roleSet;
	}

	public boolean isDisplayOnly() {
		return displayOnly;
	}

	public Set<String> getApplicableRoles() {
		if(applicableRoles == null) {
			applicableRoles = getIntersection(roleSet, parentEntity.getRoleValues());
		}
		return applicableRoles;
	}

	public String getPossibleValuesQuery() {
		return possibleValuesQuery;
	}

	public String getPossibleValueLabelAttribute() {
		return possibleValueLabelAttribute;
	}
	
	public String getPossibleValueLabelAttributePath() {
		return possibleValueLabelAttributePath;
	}

	private static Set<String> getIntersection(Set<String> set1, Set<String> set2) {
	    boolean set1IsLarger = set1.size() > set2.size();
	    Set<String> cloneSet = new HashSet<String>(set1IsLarger ? set2 : set1);
	    cloneSet.retainAll(set1IsLarger ? set1 : set2);
	    return cloneSet;
	}

	private <T> String takeFirstDeclaredStringField(Class<T> clazz) {
		if(String.class.equals(clazz)) {
			return null;
		} else {
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields) {
				if(String.class.equals(field.getClass())) {
					return field.getName();
				}
			}
			//if no String field is found in the class, than look hierarchically into its superclasses
			Class<?> superclass = clazz.getSuperclass();
			if(superclass == null) {
				throw new RuntimeException("No String field has been found within class " + clazz + ", whose instances are used as possible values for attribute " + this.attributeName + " of JPA entity " + this.parentEntity.getClassName() + ". In this case, we don't know what field to use as display label for representing these possible values.");
			} else {
				return takeFirstDeclaredStringField(superclass);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EntityAttribute that = (EntityAttribute) o;

		if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
		if (parentEntity != null ? !parentEntity.equals(that.parentEntity) : that.parentEntity != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = parentEntity != null ? parentEntity.hashCode() : 0;
		result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
		return result;
	}
}
