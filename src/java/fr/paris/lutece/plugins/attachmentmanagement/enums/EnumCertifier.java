/*
 * Copyright (c) 2002-2025, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.attachmentmanagement.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Enum certifier
 *
 */
public enum EnumCertifier {
    SMS("SMS","SMS"),
    MAIL("MAIL","Mail"),
    EMAILCERTIFIER("emailcertifier","MAIL"),
    SMSCERTIFIER("smscertifier","SMS"),
    NUM1("NUM1","PJ officielle scannée (NUM1)"),
    NUM2("NUM2","PJ non officielle scannée (NUM2)"),
    ORIG1("ORIG1","PJ officielle guichet (ORIG1)"),
    ORIG2("ORIG2","PJ non officielle guichet (ORIG2)"),
    DEC("DEC","Déclaratif (DEC)"),
    FC("fccertifier","Certification France Connect");
    
    private String strCode;
    private String strLabel;

    /**
     * Constructor
     * @param strCode
     * @param strLabel
     */
    private EnumCertifier(String strCode, String strLabel) {
	this.strCode = strCode;
	this.strLabel = strLabel;
    }

    /**
     * @return the strLabel
     */
    public String getLabel() {
        return strLabel;
    }
    
    /**
     * @return the strLabel
     */
    public String getCode() {
        return strCode;
    }
    
    /**
     * Get label by code
     * @param code
     * @return
     */
    public static String getLabelByCode(String code) {
	for(EnumCertifier certifier : EnumCertifier.values()) {
	    if(certifier.getCode().equals(code)) {
		return certifier.getLabel();
	    }	    
	}
	return StringUtils.EMPTY;
    }
}
