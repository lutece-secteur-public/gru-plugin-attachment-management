/*
 * Copyright (c) 2002-2026, City of Paris
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
package fr.paris.lutece.plugins.attachmentmanagement.util;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestFilterDto;
import fr.paris.lutece.plugins.attachmentmanagement.enums.EnumCertifier;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AttributeDto;
import fr.paris.lutece.portal.service.workflow.WorkflowService;

/**
 * 
 * AttachmentUtils
 *
 */
public class AttachmentUtils {

    
    /**
     * Private Constructor
     */
    private AttachmentUtils() {
	//Do nothing
    }
    
    /**
     * Performs an approximate (fuzzy) comparison between two strings using
     * the Levenshtein distance algorithm.
     *
     * <p>The comparison is considered a match when the computed distance
     * is less than or equal to the specified threshold.</p>
     *
     * @param query     the reference string to compare against (e.g. "Jean")
     * @param value     the input string to evaluate (e.g. "Jean-Antoine")
     * @param threshold the maximum allowed distance for a match (e.g. 2)
     * @return          true if the strings are considered similar; false otherwise
     */
    public static boolean fuzzyMatch(String query, String value, int threshold) {
        if (query == null || value == null || query.isEmpty()) {
            return false;
        }

        LevenshteinDistance ld = new LevenshteinDistance();
        int distance = ld.apply(value.toLowerCase(), query.toLowerCase());

        return distance <= threshold;
    }
    
    /**
     * Request comparator
     * @param requestFilter
     * @return comparator
     */
    public static Comparator<RequestDto> getRequestComparator(RequestFilterDto requestFilter) {
        Comparator<RequestDto> requestComparator;
	
    	if("id".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getId);
    	}else if("lastname".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getLastname,String.CASE_INSENSITIVE_ORDER);
    	}else if("firstname".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getFirstname,String.CASE_INSENSITIVE_ORDER);
    	}else if("birthdate".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getBirthdate,String.CASE_INSENSITIVE_ORDER);
    	}else if("email".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getEmail,String.CASE_INSENSITIVE_ORDER);
    	}else if("demandDate".equalsIgnoreCase(requestFilter.getSortedAttributeName())) {
    	    requestComparator = Comparator.comparing(RequestDto::getRequestDate,String.CASE_INSENSITIVE_ORDER);
    	}else {
    	    requestComparator = Comparator.comparing(RequestDto::getId);
        }
    	
        if (!requestFilter.isAscSort()) {
            requestComparator = requestComparator.reversed();
        }
    	
    	return requestComparator;
	
    }
    
    /**
     * Allows adding elements for the official scanned attachment certification (NUM1) to the attribute
     * @param strKey
     * @param strValue
     * @param certDate
     * @param listCertifiedAttribute
     */
    public static void addCertificateAttribute(String strKey,String strValue,Date certDate,List<AttributeDto> listCertifiedAttribute){
        if (!StringUtils.isEmpty(strValue)){
            AttributeDto certifiedAttribute = new AttributeDto();
            certifiedAttribute.setKey(strKey);
            certifiedAttribute.setValue(strValue);
            certifiedAttribute.setCertificationLevel(400);
            certifiedAttribute.setCertifier(EnumCertifier.NUM1.getCode());
            certifiedAttribute.setCertificationDate(certDate);

            listCertifiedAttribute.add(certifiedAttribute);
        }
    }

    /**
     * Execute a workflow action
     * @param nIdAction
     * @param nIdRequest
     * @param strResourceType
     * @param request
     * @param user
     */
    public static void runWorkflowAction(int nIdWordklow, int nIdAction,int nIdRequest,
            String strResourceType,HttpServletRequest request,User user){
        WorkflowService workflowService = WorkflowService.getInstance( );

        if (workflowService.isAvailable()){
            //Init 
            workflowService.getState(nIdRequest,strResourceType,nIdWordklow,null);
            
            //Run action
            workflowService.doProcessAction(
                    nIdRequest,
                    strResourceType,
                    nIdAction,
                    null,
                    request,
                    Locale.getDefault( ),
                    true,
                    user
                    );
        }
    }
}
