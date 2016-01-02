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
 * Enum representing the types of controls on the user interface for different data types and operator types. For example, for predefined values (also named reference data), we will have a combobox if the operator type is "single value", or a multi-selection list if the operator type is "multi-value"; for a String attribute type we can have a textbox for single value or multiple textboxes if the operator type is "multi-value"; etc.<br/>
 *
 * IMPORTANT: These values are decoded on the UI and translated into specific user input controls. Any change to this enum should be kept in sync with the JavaScript code.<br/>
 *
 * @author Daniel Doboga
 * @since 1.0
 */
public enum UserInputControlType {
    NUMBER_INPUT,
    FREE_TEXT_INPUT_SINGLE_VALUE,
    FREE_TEXT_INPUT_MULTIPLE_VALUES,
    DATE_INPUT,
    DATE_TIME_INPUT,
    REFERENCE_DATA_INPUT_SINGLE_VALUE,
    REFERENCE_DATA_INPUT_MULTIPLE_VALUES;
}
