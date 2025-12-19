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

import java.sql.Timestamp;
import java.util.List;


/**
 * 
 * RequestDto
 *
 */
public class RequestDto{
    private int nId;
    private String strCuid;
    private String strTask;
    private String strGender;
    private String strGenderCertifier;
    private String strLastname;
    private String strLastnameCertifier;
    private String strFirstname;
    private String strFirstnameCertifier;
    private String strPreferredname;
    private String strPreferrednameCertifier;
    private String strBirthdate;
    private String strBirthdateCertifier;
    private String strBirthCity;
    private String strBirthCityCertifier;
    private String strBirthCountry;
    private String strBirthCountryCertifier;
    private String strBirthCityCode;
    private String strBirthCountryCode;
    private String strEmail;
    private String strEmailCertifier;
    private String strRequestDate;
    private String strStatus;
    private List<FileDto> files;
    private String strRefusalReason;
    private String strAgent;
    private Timestamp dateDateTreatment;
    
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
     * @return the strCuid
     */
    public String getCuid( )
    {
        return strCuid;
    }

    /**
     * @param strCuid the strCuid to set
     */
    public void setCuid( String strCuid )
    {
        this.strCuid = strCuid;
    }

    /**
     * @return the strTask
     */
    public String getTask() {
        return strTask;
    }
    /**
     * @param strTask the strTask to set
     */
    public void setTask(String strTask) {
        this.strTask = strTask;
    }
    
    /**
     * @return the strGender
     */
    public String getGender() {
        return strGender;
    }
    /**
     * @param strGender the strGender to set
     */
    public void setGender(String strGender) {
        this.strGender = strGender;
    }
    /**
     * @return the strPreferredname
     */
    public String getPreferredname() {
        return strPreferredname;
    }
    /**
     * @param strPreferredname the strPreferredname to set
     */
    public void setPreferredname(String strPreferredname) {
        this.strPreferredname = strPreferredname;
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
     * @return the strBirthCity
     */
    public String getBirthCity() {
        return strBirthCity;
    }
    /**
     * @param strBirthCity the strBirthCity to set
     */
    public void setBirthCity(String strBirthCity) {
        this.strBirthCity = strBirthCity;
    }
    /**
     * @return the strBirthCountry
     */
    public String getBirthCountry() {
        return strBirthCountry;
    }
    /**
     * @param strBirthCountry the strBirthCountry to set
     */
    public void setBirthCountry(String strBirthCountry) {
        this.strBirthCountry = strBirthCountry;
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
     * @return the strRequestDate
     */
    public String getRequestDate() {
        return strRequestDate;
    }
    /**
     * @param strRequestDate the strRequestDate to set
     */
    public void setRequestDate(String strRequestDate) {
        this.strRequestDate = strRequestDate;
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
     * @return the strGenderCertifier
     */
    public String getGenderCertifier() {
        return strGenderCertifier;
    }

    /**
     * @param strGenderCertifier the strGenderCertifier to set
     */
    public void setGenderCertifier(String strGenderCertifier) {
        this.strGenderCertifier = strGenderCertifier;
    }

    /**
     * @return the strLastnameCertifier
     */
    public String getLastnameCertifier() {
        return strLastnameCertifier;
    }

    /**
     * @param strLastnameCertifier the strLastnameCertifier to set
     */
    public void setLastnameCertifier(String strLastnameCertifier) {
        this.strLastnameCertifier = strLastnameCertifier;
    }

    /**
     * @return the strFirstnameCertifier
     */
    public String getFirstnameCertifier() {
        return strFirstnameCertifier;
    }

    /**
     * @param strFirstnameCertifier the strFirstnameCertifier to set
     */
    public void setFirstnameCertifier(String strFirstnameCertifier) {
        this.strFirstnameCertifier = strFirstnameCertifier;
    }

    /**
     * @return the strPreferrednameCertifier
     */
    public String getPreferrednameCertifier() {
        return strPreferrednameCertifier;
    }

    /**
     * @param strPreferrednameCertifier the strPreferrednameCertifier to set
     */
    public void setPreferrednameCertifier(String strPreferrednameCertifier) {
        this.strPreferrednameCertifier = strPreferrednameCertifier;
    }

    /**
     * @return the strBirthdateCertifier
     */
    public String getBirthdateCertifier() {
        return strBirthdateCertifier;
    }

    /**
     * @param strBirthdateCertifier the strBirthdateCertifier to set
     */
    public void setBirthdateCertifier(String strBirthdateCertifier) {
        this.strBirthdateCertifier = strBirthdateCertifier;
    }

    /**
     * @return the strBirthCityCertifier
     */
    public String getBirthCityCertifier() {
        return strBirthCityCertifier;
    }

    /**
     * @param strBirthCityCertifier the strBirthCityCertifier to set
     */
    public void setBirthCityCertifier(String strBirthCityCertifier) {
        this.strBirthCityCertifier = strBirthCityCertifier;
    }

    /**
     * @return the strBirthCountryCertifier
     */
    public String getBirthCountryCertifier() {
        return strBirthCountryCertifier;
    }

    /**
     * @param strBirthCountryCertifier the strBirthCountryCertifier to set
     */
    public void setBirthCountryCertifier(String strBirthCountryCertifier) {
        this.strBirthCountryCertifier = strBirthCountryCertifier;
    }
    /**
     * @return the strBirthCityCode
     */
    public String getBirthCityCode( )
    {
        return strBirthCityCode;
    }

    /**
     * @param strBirthCityCode the strBirthCityCode to set
     */
    public void setBirthCityCode( String strBirthCityCode )
    {
        this.strBirthCityCode = strBirthCityCode;
    }

    /**
     * @return the strBirthCountryCode
     */
    public String getBirthCountryCode( )
    {
        return strBirthCountryCode;
    }

    /**
     * @param strBirthCountryCode the strBirthCountryCode to set
     */
    public void setBirthCountryCode( String strBirthCountryCode )
    {
        this.strBirthCountryCode = strBirthCountryCode;
    }

    /**
     * @return the strEmailCertifier
     */
    public String getEmailCertifier() {
        return strEmailCertifier;
    }

    /**
     * @param strEmailCertifier the strEmailCertifier to set
     */
    public void setEmailCertifier(String strEmailCertifier) {
        this.strEmailCertifier = strEmailCertifier;
    }

    /**
     * @return the files
     */
    public List<FileDto> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<FileDto> files) {
        this.files = files;
    }

    /**
     * @return the strRefusalReason
     */
    public String getRefusalReason() {
        return strRefusalReason;
    }

    /**
     * @param strRefusalReason the strRefusalReason to set
     */
    public void setRefusalReason(String strRefusalReason) {
        this.strRefusalReason = strRefusalReason;
    }

    /**
     * @return the strAgent
     */
    public String getAgent() {
        return strAgent;
    }

    /**
     * @param strAgent the strAgent to set
     */
    public void setAgent(String strAgent) {
        this.strAgent = strAgent;
    }

    /**
     * @return the dateDateTreatment
     */
    public Timestamp getDateTreatment() {
        return dateDateTreatment;
    }

    /**
     * @param dateDateTreatment the dateDateTreatment to set
     */
    public void setDateTreatment(Timestamp dateDateTreatment) {
        this.dateDateTreatment = dateDateTreatment;
    }
}
