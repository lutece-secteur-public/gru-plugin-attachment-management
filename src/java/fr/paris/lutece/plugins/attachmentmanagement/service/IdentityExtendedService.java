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
package fr.paris.lutece.plugins.attachmentmanagement.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentConstants;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentUtils;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AttributeDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AuthorType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.RequestAuthor;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.ResponseStatusType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.IdentityChangeRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.crud.IdentityChangeResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.search.IdentitySearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskGetResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskSearchRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskStatusType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskType;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskUpdateStatusRequest;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskUpdateStatusResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.service.IdentityServiceExtended;
import fr.paris.lutece.plugins.identitystore.v3.web.service.ServiceContractService;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityStoreException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * 
 * IdentityExtendedService
 *
 */
public class IdentityExtendedService {
      
    // BEANS
    public static final String BEAN_NAME = "attachment-management.identityExtendedService";

    private final IdentityServiceExtended _identityService;
    private final ServiceContractService _serviceContractService;

    /**
     * Constructor
     * @param identityServiceExtended
     * @param serviceContractService
     */
    public IdentityExtendedService(IdentityServiceExtended identityServiceExtended,
            ServiceContractService serviceContractService) {
        _identityService=identityServiceExtended;
        _serviceContractService=serviceContractService;
    }

    /**
     * Get identity by cuid
     * 
     * @param strGuid
     * @return the identity find
     */
    public IdentityDto getIdentityByCuid(String strGuid) {
    	IdentityDto identity = null;
    	try {
    	    IdentitySearchResponse response = _identityService.getIdentityByCustomerId(strGuid,
    		    AttachmentConstants.PROPERTY_CLIENT_CODE, getRequestAuthor());
    
    	    if (response != null && !ResponseStatusType.NOT_FOUND.equals(response.getStatus().getType())
    		    && response.getIdentities() != null && !response.getIdentities().isEmpty()) {
    		identity = response.getIdentities().get(0);
    	    }
    	} catch (IdentityStoreException | AppException e) {
    	    AppLogService.info("Error getting Identity with cuid {} ", strGuid, e);
    	}
    	return identity;
    }

    /**
     * Update identity
     * 
     * @param strCustomerId
     * @param identityDto
     * @return
     */
    public boolean updateIdentity(String strCustomerId, IdentityDto identityDto) {
    	IdentityChangeRequest identityChange = new IdentityChangeRequest();
    	identityChange.setIdentity(identityDto);
    
    	try {
    	    IdentityChangeResponse response = _identityService.updateIdentity(strCustomerId, identityChange,
    		    AttachmentConstants.PROPERTY_CLIENT_CODE, getRequestAuthor());

    	    return response != null && (ResponseStatusType.INCOMPLETE_SUCCESS.equals(response.getStatus().getType())
    	            || ResponseStatusType.OK.equals(response.getStatus().getType())
    	            || ResponseStatusType.SUCCESS.equals(response.getStatus().getType()));
    	            
    	} catch (IdentityStoreException | AppException e) {
    	    AppLogService.info("Error updating Identity with customer id {} ", strCustomerId, e);
    	}
    	return false;
    }

    /**
     * Get identity task search
     * 
     * @param request
     * @return
     */
    public IdentityTaskSearchResponse getIdentityTaskSearch(IdentityTaskSearchRequest request) {
    	try {
    	    IdentityTaskSearchResponse identityTaskSearchResponse = _identityService.searchIdentityTasks(request,
    		    AttachmentConstants.PROPERTY_CLIENT_CODE, getRequestAuthor());
    
    	    if (identityTaskSearchResponse.getStatus().getHttpCode() == 200
    		    && !identityTaskSearchResponse.getTasks().isEmpty()) {
    		return identityTaskSearchResponse;
    	    }
    
    	} catch (IdentityStoreException e) {
    	    AppLogService.error("An error occurred while searching Identity Tasks", e);
    	}
    
    	return null;
    }

    /**
     * Update identity task status
     * 
     * @param strTaskCode
     * @param statusType
     * @return true if the status has been updated
     */
    public boolean updateIdentityTaskStatus(String strTaskCode, IdentityTaskStatusType statusType) {
    	try {
    	    IdentityTaskUpdateStatusRequest request = new IdentityTaskUpdateStatusRequest();
    	    request.setStatus(statusType);
    
    	    IdentityTaskUpdateStatusResponse identityTaskUpdateStatusResponse = _identityService
    		    .updateIdentityTaskStatus(strTaskCode, request, AttachmentConstants.PROPERTY_CLIENT_CODE,
    			    getRequestAuthor());
    
    	    if (identityTaskUpdateStatusResponse.getStatus().getHttpCode() == 200) {
    		return true;
    	    }
    
    	} catch (IdentityStoreException e) {
    	    AppLogService.error("An error occurred while updating task {}", strTaskCode, e);
    	}
    
    	return false;
    }

    /**
     * Get identity task by task code
     * 
     * @param strTaskCode
     * @return the identity tasj by task code
     */
    public IdentityTaskDto getIdentityTask(String strTaskCode) {
    	try {
    	    IdentityTaskGetResponse identityTaskGetResponse = _identityService.getIdentityTask(strTaskCode,
    		    AttachmentConstants.PROPERTY_CLIENT_CODE, getRequestAuthor());
    
    	    if (identityTaskGetResponse.getStatus().getHttpCode() == 200) {
    		return identityTaskGetResponse.getTask();
    	    }
    	} catch (IdentityStoreException e) {
    	    AppLogService.error("An error occurred while retrieving task {}", strTaskCode, e);
    	}
    
    	return null;
    }

    /**
     * Get active service contract
     * 
     * @return the active service contract
     */
    public ServiceContractSearchResponse getActiveServiceContract() {
    	ServiceContractSearchResponse serviceContractSearchResponse = null;
    	try {
    	    serviceContractSearchResponse = _serviceContractService.getActiveServiceContract(
    		    AttachmentConstants.PROPERTY_CLIENT_CODE, AttachmentConstants.PROPERTY_CLIENT_CODE,
    		    getRequestAuthor());
    	} catch (IdentityStoreException e) {
    	    AppLogService.error("Error ServiceContract for application {}", e.getMessage(),
    		    AttachmentConstants.PROPERTY_CLIENT_CODE);
    	}
    
    	return serviceContractSearchResponse;
    }

    /**
     * Returns a list of identityTasks for the type attachment certification
     * request.
     * 
     * @return a list of identityTasks for the type attachment certification
     *         request.
     */
    public List<IdentityTaskDto> getACRTaskList() {
    	IdentityTaskSearchRequest request = new IdentityTaskSearchRequest();
    	request.setTaskType(IdentityTaskType.ATTACHMENT_CERTIFICATION_REQUEST);
    
    	List<IdentityTaskStatusType> listTaskStatus = new ArrayList<>();
    	listTaskStatus.add(IdentityTaskStatusType.TODO);
    	listTaskStatus.add(IdentityTaskStatusType.IN_PROGRESS);
    	listTaskStatus.add(IdentityTaskStatusType.PROCESSED);
    	listTaskStatus.add(IdentityTaskStatusType.REFUSED);
    
    	request.setTaskStatus(listTaskStatus);
    
    	IdentityTaskSearchResponse response = getIdentityTaskSearch(request);
    
    	if (response != null && response.getTasks() != null) {
    	    return response.getTasks();
    	}
    
    	return Collections.emptyList();
    }
    
    /**
     * Get request author
     * 
     * @return RequestAuthor
     */
    private static RequestAuthor getRequestAuthor() {
    	RequestAuthor requestAuthor = new RequestAuthor();
    	requestAuthor.setName(AttachmentConstants.PROPERTY_CLIENT_CODE);
    	requestAuthor.setType(AuthorType.owner);
    	return requestAuthor;
    }
    
    /**
     * Verifies whether the given client is authorized to manage attachment requests 
     *
     * @return true if the client's service contract permits attachment certification
     */
    public boolean isAuthorizedToManageAttachementRequest() {
        ServiceContractSearchResponse serviceContract = getActiveServiceContract();
        
        return serviceContract != null && serviceContract.getServiceContract( )!= null
            && serviceContract.getServiceContract().isAuthorizedAttachementCertification();       
    }
    
    /**
     * 
     * @param requestDto
     * @return
     */
    public boolean certifyIdentity(RequestDto requestDto) {
        if(requestDto==null) {
            return false;
        }
        
        IdentityDto identityDto = getIdentityByCuid(requestDto.getCuid());
        
        IdentityDto identityToCertify = new IdentityDto();
        identityToCertify.setCustomerId( identityDto.getCustomerId( ) );
        identityToCertify.setLastUpdateDate( identityDto.getLastUpdateDate( ) );
        identityToCertify.setMonParisActive( identityDto.getMonParisActive( ) );
        
        List<AttributeDto> listCertifiedAttribute = new ArrayList<>();
                
        Date date = new Date( );
        
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_GENDER,requestDto.getGender( ),date ,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_LAST_NAME,requestDto.getLastname( ),date ,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME,requestDto.getFirstname( ),date ,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME,requestDto.getPreferredname( ),date ,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE,requestDto.getBirthdate( ),date,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE, requestDto.getBirthCityCode( ),date,listCertifiedAttribute );
        AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE,requestDto.getBirthCountryCode( ),date,listCertifiedAttribute );
        
        if(StringUtils.isEmpty(requestDto.getBirthCityCode()) && StringUtils.isNotEmpty(requestDto.getBirthCity())){
            AttachmentUtils.addCertificateAttribute(AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE,requestDto.getBirthCity(),date,listCertifiedAttribute);
        }
        
        identityToCertify.setAttributes( listCertifiedAttribute );
        
        return updateIdentity( requestDto.getCuid( ),identityToCertify);
    }

}
