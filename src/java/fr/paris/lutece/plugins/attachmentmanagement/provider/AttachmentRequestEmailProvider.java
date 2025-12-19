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
package fr.paris.lutece.plugins.attachmentmanagement.provider;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.service.AttachmentRequestService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.provider.IProvider;
import fr.paris.lutece.plugins.workflowcore.service.provider.InfoMarker;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;

/**
 * 
 * AttachmentRequestEmailProvider
 *
 */
public class AttachmentRequestEmailProvider implements IProvider{

    private static final String PROVIDER_REFERENCE_KEY = "ATTACHMENT-REQUEST-EMAIL";
    
    // MARKER NAMES
    protected static final String MARKERNAME_USER_FIRSTNAME = "firstname";
    protected static final String MARKERNAME_USER_LASTNAME = "lastname";
    protected static final String MARKERNAME_USER_PREFERRED_USERNAME = "preferred_username";
    protected static final String MARKERNAME_USER_BIRTHDATE = "birthdate";
    protected static final String MARKERNAME_USER_BIRTHPLACE = "birthplace";
    protected static final String MARKERNAME_USER_BIRTHCOUNTRY = "birthcountry";
    protected static final String MARKERNAME_USER_GENDER = "gender";
    protected static final String MARKERNAME_DATE_REQUEST = "dateRequest";
    protected static final String MARKERNAME_REASON_REFUSAL = "reasonRefusal";

    // MARKER DESCRIPTIONS
    protected static final String MARKERDESCRIPTION_FIRSTNAME = "attachmentmanagement.marker.provider.descriptionFirstName";
    protected static final String MARKERDESCRIPTION_LASTNAME = "attachmentmanagement.marker.provider.descriptionLastName";
    protected static final String MARKERDESCRIPTION_PREFERRED_USERNAME = "attachmentmanagement.marker.provider.descriptionPrefferedUsername";
    protected static final String MARKERDESCRIPTION_GENDER = "attachmentmanagement.marker.provider.descriptionGender";
    protected static final String MARKERDESCRIPTION_BIRTHDATE = "attachmentmanagement.marker.provider.descriptionBirthdate";
    protected static final String MARKERDESCRIPTION_BIRTHPLACE = "attachmentmanagement.marker.provider.descriptionBirthplace";
    protected static final String MARKERDESCRIPTION_BIRTHCOUNTRY = "attachmentmanagement.marker.provider.descriptionBirthcountry";
    protected static final String MARKERDESCRIPTION_DATE_REQUEST = "attachmentmanagement.marker.provider.descriptionDateRequest";
    protected static final String MARKERDESCRIPTION_REASON_REFUSAL = "attachmentmanagement.marker.provider.descriptionReasonRefusal";
      
    private final AttachmentRequestService _attachmentRequestService = SpringContextService.getBean(AttachmentRequestService.BEAN_NAME);
    private RequestDto _request;
    
    /**
     * The Constructor
     * @param id
     * @param s
     * @param resourceHistory
     * @param httpServletRequest
     */
    public AttachmentRequestEmailProvider( String id, String s, ResourceHistory resourceHistory, HttpServletRequest httpServletRequest ){
        if (resourceHistory != null && resourceHistory.getAction( ) != null){           
            _request = _attachmentRequestService.getRequestById(resourceHistory.getIdResource( ),false );
            
            if(_request == null){
                throw new AppException( "AttachmentRequestEmailProvider - Error when retreive attachment request for request id "+resourceHistory.getIdResource( ), new Exception( ));
            }
        }
    }

    @Override
    public String provideDemandId( ){
        return null;
    }

    @Override
    public String provideDemandTypeId( ){
        return  null;
    }
    
    @Override
    public String provideDemandReference( ){
        return PROVIDER_REFERENCE_KEY;
    }

    @Override
    public String provideCustomerId( ){
        return _request.getCuid( );
    }

    @Override
    public String provideCustomerEmail( ){
        return _request.getEmail( );
    }

    @Override
    public String provideCustomerMobilePhone( ){
        return null;
    }

    @Override
    public String provideSmsSender( ){
        return null;
    }
    
    @Override
    public String provideCustomerConnectionId( ){
        return null;
    }
    
    @Override
    public String provideDemandSubtypeId( ){
        return null;
    }

    @Override
    public Collection<InfoMarker> provideMarkerValues( ){
        Collection<InfoMarker> collectionInfoMarkers = new ArrayList<>( );
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_FIRSTNAME,_request.getFirstname()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_LASTNAME,_request.getLastname()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_PREFERRED_USERNAME,_request.getPreferredname()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_GENDER,getGenderLabel(_request.getGender())));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_BIRTHCOUNTRY,_request.getBirthCountry()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_BIRTHDATE,_request.getBirthdate()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_USER_BIRTHPLACE,_request.getBirthCity()));
        collectionInfoMarkers.add(createMarkerValues(MARKERNAME_DATE_REQUEST,_request.getRequestDate()));

   
        if(StringUtils.isNotEmpty(_request.getRefusalReason())){
            collectionInfoMarkers.add(createMarkerValues(MARKERNAME_REASON_REFUSAL,_request.getRefusalReason()));
        }

        return collectionInfoMarkers;
    }

    public static Collection<InfoMarker> getProviderMarkerDescriptions( ){
        Collection<InfoMarker> collectionInfoMarkers = new ArrayList<>( );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_FIRSTNAME, MARKERDESCRIPTION_FIRSTNAME ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_LASTNAME, MARKERDESCRIPTION_LASTNAME ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_PREFERRED_USERNAME, MARKERDESCRIPTION_PREFERRED_USERNAME ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_GENDER, MARKERDESCRIPTION_GENDER ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_BIRTHDATE, MARKERDESCRIPTION_BIRTHDATE ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_BIRTHPLACE, MARKERDESCRIPTION_BIRTHPLACE ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_USER_BIRTHCOUNTRY, MARKERDESCRIPTION_BIRTHCOUNTRY ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_DATE_REQUEST, MARKERDESCRIPTION_DATE_REQUEST ) );
        collectionInfoMarkers.add( createMarkerDescriptions( MARKERNAME_REASON_REFUSAL, MARKERDESCRIPTION_REASON_REFUSAL ) );

        return collectionInfoMarkers;
    }

    /**
     * Creates a {@code InfoMarker} object with the specified marker and description.
     *
     * @param strMarker
     *            the marker
     * @param strDescription
     *            the description to inject into the {@code InfoMarker} object
     * @return the {@code InfoMarker} object
     */
    protected static InfoMarker createMarkerDescriptions(String strMarker,String strDescription){
        InfoMarker notifyGruMarker = new InfoMarker(strMarker);
        notifyGruMarker.setDescription(I18nService.getLocalizedString(strDescription,I18nService.getDefaultLocale()));

        return notifyGruMarker;
    }

    private InfoMarker createMarkerValues(String strMarker,String strValue){
        InfoMarker notifyGruMarker = new InfoMarker(strMarker);
        notifyGruMarker.setValue(strValue);
        return notifyGruMarker;
    }

    /**
     * Get gender label
     * 
     * @param strId
     * @return gender label
     */
    private String getGenderLabel(String strId){
        if ( StringUtils.isNotEmpty(strId)){
            if (strId.equals("0")){
                return I18nService.getLocalizedString( "attachmentmanagement.gender.not_defined.label",I18nService.getDefaultLocale());
            } else if (strId.equals("1")){
                return I18nService.getLocalizedString( "attachmentmanagement.gender.female.label",I18nService.getDefaultLocale());
            } else if (strId.equals("2")){
                return I18nService.getLocalizedString( "attachmentmanagement.gender.male.label",I18nService.getDefaultLocale());
            }
        }
        return I18nService.getLocalizedString( "attachmentmanagement.gender.not_defined.label",I18nService.getDefaultLocale());
    }
}
