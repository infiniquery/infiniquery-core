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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Value object representing a JPA entity.
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class JpaEntity {

	private String className;

	private String displayName;

	private String roles;

	private Set<String> roleSet;

	private String additionalFilter;

	private List<EntityAttribute> attributes;

	private JpaEntity() {
		super();
	}

	/**
	 * 
	 * @return className
	 */
	public String getClassName() {
		return className;
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
	 * @return roles the values of the user roles that can access this entity, as a CSV String
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * 
	 * @return the values of the user roles that can access this entity
	 */
	public Set<String> getRoleValues() {
		return roleSet;
	}

	/**
	 * 
	 * @return the list of attributes to expose for this entity
	 */
	public List<EntityAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * 
	 * @return the list of attributes to expose for this entity, in the reverse order.
	 */
	public List<EntityAttribute> getAttributesInReverseOrder() {
		List<EntityAttribute> reverseList = new ArrayList<>(attributes);
		Collections.reverse(reverseList);
		return reverseList;
	}

	/**
	 * Get the additional filter jpql string, that may contain dynamic attributes. The
	 * dynamic attributes, if any, can be expressed in the query as ${variableName} and 
	 * their values are inserted into the global scope by the getGlobalScopeAttributes()
	 * method of the SecurityService (client applications should provide a custom implementation
	 * of the SecurityService in order to make this effective).
	 * 
	 * @return the additional filter jpql string.
	 */
	public String getAdditionalFilter() {
		return additionalFilter;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JpaEntity jpaEntity = (JpaEntity) o;

		if (displayName != null ? !displayName.equals(jpaEntity.displayName) : jpaEntity.displayName != null)
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return displayName != null ? displayName.hashCode() : 0;
	}

	/**
	 * Startup point for creating JpaEntity instances.
	 * @return a builder object for constructing new JpaEntity instances.
	 */
	public static JpaEntityBuilder newBuilder() {
		return new JpaEntityBuilder();
	}

	public static class JpaEntityBuilder {
		private JpaEntity jpaEntity = new JpaEntity();
		
		/**
		 * Set the className in the builder object.
		 * @param className the full name of the {@link Class} representing the entity
		 * @return the builder object it is called on.
		 */
		public JpaEntityBuilder withClassName(String className) {
			jpaEntity.className = className;
			return this;
		}
		
		/**
		 * Set the displayName in the builder ojbject.
		 * @param displayName the displayable alias
		 * @return the builder object it is called on.
		 */
		public JpaEntityBuilder withDisplayName(String displayName) {
			jpaEntity.displayName = displayName;
			return this;
		}
		
		/**
		 * 
		 * @param roles
		 * @return the builder object it is called on.
		 */
		public JpaEntityBuilder withRoles(final String roles) {
			jpaEntity.roles = roles;
			jpaEntity.roleSet = Collections.unmodifiableSet(new HashSet<String>() {
				{
					if (roles != null && !roles.isEmpty()) {
						String[] roleValues = roles.split(",");
						for (String role : roleValues) {
							add(role.trim());
						}
					}
				}
			});
			return this;
		}
		
		/**
		 * 
		 * @param additionalFilter
		 * @return the builder object it is called on.
		 */
		public JpaEntityBuilder withAdditionalFilter(String additionalFilter) {
			jpaEntity.additionalFilter = additionalFilter;
			return this;
		}
		
		/**
		 * 
		 * @param attributes
		 * @return the builder object it is called on.
		 */
		public JpaEntityBuilder withAttributes(List<EntityAttribute> attributes) {
			jpaEntity.attributes = attributes;
			jpaEntity.attributes = attributes;
			for(EntityAttribute attribute : attributes) {
				attribute.setParentEntity(jpaEntity);
			}
			return this;
		}
		
		/**
		 * Ends the work of this builder by creating the target object and invalidating this builder.
		 * This method can successfully be called only once. If one call throws an exception, it may be
		 * called again and again. Once the method returns successfully, any subsequent call will 
		 * 
		 * @return the constructed JpaEntity
		 * @throws IllegalStateException if one of the required attributes are not yet set in the constructed object,
		 * or if the builder has already been consumed (which is one successful invocation off this method
		 * has already happened.
		 */
		public JpaEntity build() {
			if(jpaEntity.className == null) {
				throw new IllegalStateException("className attribute of the JpaEntity cannot be null.");
			}
			if(jpaEntity.displayName == null) {
				throw new IllegalStateException("displayName attribute of the JpaEntity cannot be null.");
			}
			if(jpaEntity == null) {
				throw new IllegalStateException("This builder has aleady been consumed.");
			}
			final JpaEntity finalEntity = jpaEntity;
			jpaEntity = null;
			return finalEntity;
		}
	}

}
