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
package fr.paris.lutece.plugins.attachmentmanagement.service.rbac;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * RBAC Resource Id Service to allow roles on AdminUser Services
 *
 */
public class AttachmentRequestResourceIdService extends ResourceIdService {

    public static final String RESOURCE_TYPE = "ATTACHMENT_REQUEST_RESOURCE";

    public static final String PERMISSION_VIEW_REQUEST = "VIEW_REQUEST";
    public static final String PERMISSION_PROCESS_REQUEST = "PROCESS_REQUEST";
    public static final String PERMISSION_VIEW_HISTORY = "VIEW_HISTORY";

    public static final String PROPERTY_LABEL_RESOURCE_TYPE = "attachmentmanagement.attachment.request.ressourceType";
    public static final String PROPERTY_LABEL_VIEW_REQUEST = "attachmentmanagement.attachment.request.permission.view_request";
    public static final String PROPERTY_LABEL_PROCESS_REQUEST = "attachmentmanagement.attachment.request.permission.process_request";
    public static final String PROPERTY_LABEL_VIEW_HISTORY = "attachmentmanagement.attachment.request.permission.view_history";

    @Override
    public void register() {
        ResourceType rt = new ResourceType();
        rt.setResourceIdServiceClass(AttachmentRequestResourceIdService.class.getName());
        rt.setResourceTypeKey(RESOURCE_TYPE);
        rt.setResourceTypeLabelKey(PROPERTY_LABEL_RESOURCE_TYPE);

        Permission pViewRequest = new Permission();
        pViewRequest.setPermissionKey(PERMISSION_VIEW_REQUEST);
        pViewRequest.setPermissionTitleKey(PROPERTY_LABEL_VIEW_REQUEST);
        rt.registerPermission(pViewRequest);

        Permission pProcessRequest = new Permission();
        pProcessRequest.setPermissionKey(PERMISSION_PROCESS_REQUEST);
        pProcessRequest.setPermissionTitleKey(PROPERTY_LABEL_PROCESS_REQUEST);
        rt.registerPermission(pProcessRequest);

        Permission pViewHistory = new Permission();
        pViewHistory.setPermissionKey(PERMISSION_VIEW_HISTORY);
        pViewHistory.setPermissionTitleKey(PROPERTY_LABEL_VIEW_HISTORY);
        rt.registerPermission(pViewHistory);

        ResourceTypeManager.registerResourceType(rt);
    }

    @Override
    public ReferenceList getResourceIdList(Locale arg0){
        return null;
    }

    @Override
    public String getTitle(String arg0,Locale arg1){
        return StringUtils.EMPTY;
    }
}
