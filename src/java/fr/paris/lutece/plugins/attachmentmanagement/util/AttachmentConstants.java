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
package fr.paris.lutece.plugins.attachmentmanagement.util;

import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * 
 * AttachmentConstants
 *
 */
public class AttachmentConstants {

    /**
     * Private constructor
     */
    private AttachmentConstants() {
	// Do nothing
    }

    // PROPERTIES
    public static final String PROPERTY_CLIENT_CODE = AppPropertiesService.getProperty("attachment-management.client.code", "");
    public static final int PROPERTY_WORKFLOW_ID = AppPropertiesService.getPropertyInt("attachment-management.workflow.id",0);
    public static final int PROPERTY_REJECT_REQUEST_ACTION_ID = AppPropertiesService.getPropertyInt("attachment-management.workflow.reject.request.action.id", 0);
    public static final int PROPERTY_ACCEPT_REQUEST_ACTION_ID = AppPropertiesService.getPropertyInt("attachment-management.workflow.accept.request.action.id", 0);
    public static final boolean PROPERTY_CAN_EDIT_IDENTITY = AppPropertiesService.getPropertyBoolean("attachment-management.can.edit.identity", false);
    
    
    // FORMAT
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    // JSP
    public static final String MANAGE_ATTACHMENT_JSP = "jsp/admin/plugins/attachmentmanagement/ManageAttachment.jsp";

    // ATTRIBUTES
    public static final String ATTRIBUTE_DB_IDENTITY_LAST_NAME = "family_name";
    public static final String ATTRIBUTE_DB_IDENTITY_PREFERRED_USER_NAME = "preferred_username";
    public static final String ATTRIBUTE_DB_IDENTITY_FIRSTNAME = "first_name";
    public static final String ATTRIBUTE_DB_IDENTITY_GENDER = "gender";
    public static final String ATTRIBUTE_DB_IDENTITY_BIRTHDATE = "birthdate";
    public static final String ATTRIBUTE_DB_IDENTITY_BIRTHPLACE = "birthplace";
    public static final String ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY = "birthcountry";
    public static final String ATTRIBUTE_DB_IDENTITY_BIRTHPLACE_CODE = "birthplace_code";
    public static final String ATTRIBUTE_DB_IDENTITY_BIRTHCOUNTRY_CODE = "birthcountry_code";
    public static final String ATTRIBUTE_DB_IDENTITY_EMAIL = "email";

    // FILTER
    public static final String FILTER_STATUS = "status";
    public static final String FILTER_ID_REQUEST = "idRequest";
    public static final String FILTER_FIRSTNAME = "firstname";
    public static final String FILTER_LASTNAME = "lastname";
    public static final String FILTER_BIRTHDATE = "birthdate";
    public static final String FILTER_MAIL = "mail";

    // CONSTANTS
    public static final String METADATA_CLIENT_CODE = "client_code";
    public static final String METADATA_PROVIDER_NAME = "provider_name";
    public static final String METADATA_FILE_NAME = "file_name";
    public static final String METADATA_FILE_PATH = "file_path";
    public static final String METADATA_FILES = "files";
    
    public static final int TYPE_VIEW = 1;
    public static final int TYPE_PROCESS = 2;
    public static final int TYPE_HISTORY = 3;
    
    public static final String WORKFLOW_RESOURCE_TYPE = "ATTACHMENT_REQUEST";

    // MSG
    public static final String MSG_REFUSAL_REASON_EMPTY = "attachmentmanagement.msg.refusal.reason.empty";
    public static final String MSG_SUCCESS_REFUSAL_REQUEST = "attachmentmanagement.msg.success.refusal.request";
    public static final String MSG_SUCCESS_CERTIFICATION_REQUEST = "attachmentmanagement.msg.success.certification.request";
    public static final String MSG_ERROR_CERTIFICATION_REQUEST = "attachmentmanagement.msg.error.certification.request";
    public static final String MSG_PERMISSION_UNAUTHORIZED = "attachmentmanagement.attachment.request.permission.unauthorized";
   
}
