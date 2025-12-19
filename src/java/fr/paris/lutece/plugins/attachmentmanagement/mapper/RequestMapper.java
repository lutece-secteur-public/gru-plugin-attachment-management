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
package fr.paris.lutece.plugins.attachmentmanagement.mapper;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.enums.EnumCertifier;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentConstants;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.AttributeDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskDto;

/**
 * 
 * RequestMapper
 *
 */
public class RequestMapper {
    
    /**
     * Private constructor
     */
    private RequestMapper() {
	//Do nothing
    }
    
    /**
     * Map data requestDto
     * @param identityDto
     * @param task
     * @return requestDto
     */
    public static RequestDto toRequestDto(IdentityDto identityDto, IdentityTaskDto task) {
    	RequestDto request = new RequestDto();
    	SimpleDateFormat format = new SimpleDateFormat(AttachmentConstants.DATE_FORMAT);
    	
    	//Attribute value
    	request.setCuid(identityDto.getCustomerId());
    	request.setTask(task.getTaskCode());
    	request.setRequestDate( format.format(task.getCreationDate()));
    	request.setGender(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_GENDER));
    	request.setFirstname(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME));
    	request.setLastname(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_LAST_NAME));
    	request.setPreferredname(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME));
    	request.setBirthdate(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE));
    	request.setBirthCity(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE));
        request.setBirthCityCode(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE));
    	request.setBirthCountry(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY));
        request.setBirthCountryCode(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE));
    	request.setEmail(getAttributeValue(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_EMAIL));
    	request.setStatus(task.getTaskStatus().name());
    	
    	//Certification Level
    	request.setGenderCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_GENDER));
    	request.setFirstnameCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_FIRSTNAME));
    	request.setLastnameCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_LAST_NAME));
    	request.setPreferrednameCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME));
    	request.setBirthdateCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHDATE));
    	request.setBirthCityCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHPLACE));
    	request.setBirthCountryCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY));
    	request.setEmailCertifier(getCertifier(identityDto, AttachmentConstants.ATTRIBUTE_DB_IDENTITY_EMAIL));
    	
    	return request;
    }
    
    
    /**
     * Returns the value of the attribute passed as a parameter
     * @param identityDto
     * @param attributeKey
     * @return the value of the attribute passed as a parameter
     */
    private static String getAttributeValue(IdentityDto identityDto, String attributeKey) {
    	if(identityDto!=null && identityDto.getAttributes()!=null) {
    	    Optional<AttributeDto> attributeDto = identityDto.getAttributes().stream()
    		    .filter( a->a.getKey().equals(attributeKey))
    		    .findFirst();
    	    
    	    if(attributeDto.isPresent()) {
    		return attributeDto.get().getValue();
    	    }
    	}
    	return StringUtils.EMPTY;
    }
    
    /**
     * Returns the certifier label of the attribute passed as a parameter
     * @param identityDto
     * @param attributeKey
     * @return the  certifier label of the attribute passed as a parameter
     */
    private static String getCertifier(IdentityDto identityDto, String attributeKey) {
    	if(identityDto!=null && identityDto.getAttributes()!=null) {
    	    Optional<AttributeDto> attributeDto = identityDto.getAttributes().stream()
    		    .filter( a->a.getKey().equals(attributeKey))
    		    .findFirst();
    	    
    	    if(attributeDto.isPresent()) {
    		return EnumCertifier.getLabelByCode(String.valueOf(attributeDto.get().getCertifier()));
    	    }
    	}
    	return StringUtils.EMPTY;
    }
}
