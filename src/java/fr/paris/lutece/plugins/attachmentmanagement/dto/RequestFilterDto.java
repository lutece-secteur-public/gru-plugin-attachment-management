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
package fr.paris.lutece.plugins.attachmentmanagement.dto;

/**
 * 
 * RequestFilterDto
 *
 */
public class RequestFilterDto {
    private int nId;
    private String strLastname;
    private String strFirstname;
    private String strBirthdate;
    private String strEmail;
    private String strStatus;
    private String strSortedAttributeName;
    private boolean strAscSort;   
    
    
    /**
     * @return the nId
     */
    public int getId() {
        return nId;
    }
    /**
     * @param nId the nId to set
     */
    public void setId(int nId) {
        this.nId = nId;
    }
    
    /**
     * @return the strLastname
     */
    public String getLastname() {
        return strLastname;
    }
    /**
     * @param strLastname the strLastname to set
     */
    public void setLastname(String strLastname) {
        this.strLastname = strLastname;
    }
    /**
     * @return the strFirstname
     */
    public String getFirstname() {
        return strFirstname;
    }
    /**
     * @param strFirstname the strFirstname to set
     */
    public void setFirstname(String strFirstname) {
        this.strFirstname = strFirstname;
    }
    /**
     * @return the strBirthdate
     */
    public String getBirthdate() {
        return strBirthdate;
    }
    /**
     * @param strBirthdate the strBirthdate to set
     */
    public void setBirthdate(String strBirthdate) {
        this.strBirthdate = strBirthdate;
    }
    /**
     * @return the strEmail
     */
    public String getEmail() {
        return strEmail;
    }
    /**
     * @param strEmail the strEmail to set
     */
    public void setEmail(String strEmail) {
        this.strEmail = strEmail;
    }
    /**
     * @return the strStatus
     */
    public String getStatus() {
        return strStatus;
    }
    /**
     * @param strStatus the strStatus to set
     */
    public void setStatus(String strStatus) {
        this.strStatus = strStatus;
    }
    /**
     * @return the strSortedAttributeName
     */
    public String getSortedAttributeName() {
        return strSortedAttributeName;
    }
    /**
     * @param strSortedAttributeName the strSortedAttributeName to set
     */
    public void setSortedAttributeName(String strSortedAttributeName) {
        this.strSortedAttributeName = strSortedAttributeName;
    }
    /**
     * @return the strAscSort
     */
    public boolean isAscSort() {
        return strAscSort;
    }
    /**
     * @param strAscSort the strAscSort to set
     */
    public void setAscSort(boolean strAscSort) {
        this.strAscSort = strAscSort;
    }
}
