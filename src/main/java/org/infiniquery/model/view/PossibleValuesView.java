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

package org.infiniquery.model.view;

import org.infiniquery.model.UserInputControlType;

/**
 * Data transfer object encapsulating the information necessary to build the input control for filtering values of an entity attribute, in the user interface.
 * @author Daniel Doboga
 * @since 1.0
 */
public class PossibleValuesView {
    private UserInputControlType inputControlType;
    private String[] possibleValues;

    public static PossibleValuesView getInstance(UserInputControlType inputControlType, String[] possibleValues) {
        if(possibleValues == null) {
            return new PossibleValuesView(inputControlType);
        } else {
            return new PossibleValuesView(inputControlType, possibleValues);
        }
    }

    private PossibleValuesView(UserInputControlType inputControlType) {
        this.inputControlType = inputControlType;
    }

    private PossibleValuesView(UserInputControlType inputControlType, String[] possibleValues) {
        this.inputControlType = inputControlType;
        this.possibleValues = possibleValues;
    }

    public UserInputControlType getInputControlType() {
        return inputControlType;
    }

    public void setInputControlType(UserInputControlType inputControlType) {
        this.inputControlType = inputControlType;
    }

    public String[] getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(String[] possibleValues) {
        this.possibleValues = possibleValues;
    }
}
