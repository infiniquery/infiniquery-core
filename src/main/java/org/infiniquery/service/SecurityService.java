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

package org.infiniquery.service;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Daniel Doboga
 * @since 1.0.0
 */
public interface SecurityService {

    /**
     * Retrieve the roles of the currently logged application user.
     * @return a set of String objects representing the role(s) of the current user
     */
    Set<String> getCurrentUserRoles();

    /**
     * Provide the attributes to be accessed, in an expression language fashion,
     * from the additional filter.
     * Implementations of this method should not cache the returned values and 
     * should be careful if returning mutable objects. In order to avoid 
     * performance overhead, the caller code from infiniquery engine doesn't do 
     * any immutable copies for avoiding concurrency issues (if you think there 
     * is a risk in this sense, you need to provide caching in your custom 
     * implementation of this method).
     * 
     * @return a map of attributes representing the global scope
     */
    Map<String, Object> getGlobalScopeAttributes();

}
