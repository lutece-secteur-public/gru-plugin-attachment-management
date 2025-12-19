/*
 * Copyright (c) 2002-2025, Mairie de Paris
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
package fr.paris.lutece.plugins.attachmentmanagement.business;

import java.sql.Timestamp;

/**
 * This is the business class for the object Request
 */
public class Request {
    // Variables declarations
    private int nIdRequest;
    private String strTask;
    private String strRefusalReason;
    private String strAgent;
    private Timestamp dateDateTreatment;    

    /**
     * Returns the IdRequest
     * 
     * @return The IdRequest
     */
    public int getIdRequest() {
	return nIdRequest;
    }

    /**
     * Sets the IdRequest
     * 
     * @param nIdRequest The IdRequest
     */
    public void setIdRequest(int nIdRequest) {
	this.nIdRequest = nIdRequest;
    }

    /**
     * Returns the Task
     * 
     * @return The Task
     */
    public String getTask() {
	return strTask;
    }

    /**
     * Sets the Task
     * 
     * @param strTask The Task
     */
    public void setTask(String strTask) {
	this.strTask = strTask;
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