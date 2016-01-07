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

	private List<EntityAttribute> attributes;

	/**
	 * 
	 * @param className
	 * @param displayName
	 * @param roles
	 * @param attributes List<EntityAttribute>
	 */
	public JpaEntity(String className, String displayName, final String roles, List<EntityAttribute> attributes) {
		super();
		this.className = className;
		this.displayName = displayName;
		this.roles = roles;
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
		this.attributes = attributes;
		for(EntityAttribute attribute : attributes) {
			attribute.setParentEntity(this);
		}
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
}
