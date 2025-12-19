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

package fr.paris.lutece.plugins.attachmentmanagement.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestFilterDto;
import fr.paris.lutece.plugins.attachmentmanagement.service.IdentityExtendedService;
import fr.paris.lutece.plugins.attachmentmanagement.service.cache.AttachmentRequestCacheService;
import fr.paris.lutece.plugins.attachmentmanagement.service.rbac.AttachmentRequestResourceIdService;
import fr.paris.lutece.plugins.attachmentmanagement.service.AttachmentRequestService;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentConstants;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentUtils;
import fr.paris.lutece.plugins.identitypicker.service.IdentityPickerResourceService;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * 
 * ManageAttachmentJspBean
 *
 */
@Controller( controllerJsp = "ManageAttachment.jsp", controllerPath = "jsp/admin/plugins/attachmentmanagement/", right = "ATTACHMENT_MANAGEMENT" )
public class ManageAttachmentJspBean extends AbstractJspBean<Integer, RequestDto>{
 
    /**
     * 
     */
    private static final long serialVersionUID = 3166366877801404001L;
    
    //TEMPLATE
    private static final String TEMPLATE_MANAGE_ATTACHMENT = "/admin/plugins/attachmentmanagement/manage_attachments.html";
    private static final String TEMPLATE_ATTACHMENT_DETAIL = "/admin/plugins/attachmentmanagement/attachment_detail.html";    
    
    //VIEW
    private static final String VIEW_DEFAULT = "attachments";
    private static final String VIEW_ATTACHMENT_DETAIL = "attachment_detail";
    
    //ACTION
    private static final String ACTION_REJECTED = "rejected";
    private static final String ACTION_CERTIFY = "certify";
    private static final String ACTION_REINIT_CACHE = "reinit_cache";
    private static final String ACTION_UPDATE_REQUEST_CACHE = "update_request_cache";
    
    //MARKERS
    private static final String MARK_REQUEST_LIST = "requestList";
    private static final String MARK_REQUEST_FILTER = "requestFilter";
    private static final String MARK_REQUEST = "request";
    private static final String MARK_TYPE = "type";
    private static final String MARK_CAN_EDIT = "canEdit";
    
    //PARAMETERS
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_REFUSAL_REASON = "refusalReason";
    private static final String PARAMETER_TYPE = "type";
    
    //PROPERTIES
    private static final String PERMISSION_UNAUTHORIZED = I18nService.getLocalizedString(AttachmentConstants.MSG_PERMISSION_UNAUTHORIZED,Locale.getDefault()); 
    
    //SERVICE
    private final AttachmentRequestService _attachmentRequestService = SpringContextService.getBean(AttachmentRequestService.BEAN_NAME);
    private final IdentityExtendedService _identityExtendedService = SpringContextService.getBean(IdentityExtendedService.BEAN_NAME);
    private final AttachmentRequestCacheService _identityTaskCacheService = SpringContextService.getBean(AttachmentRequestCacheService.BEAN_NAME);

    
    @View(defaultView = true, value = VIEW_DEFAULT)
    public String getManageAttachments(HttpServletRequest request) throws AccessDeniedException{       
    	Map<String, Object> model = getModel();
    
    	// Check authorization
    	if (!_identityExtendedService.isAuthorizedToManageAttachementRequest()) {
    	    throw new AccessDeniedException("Le client d'application "+ AttachmentConstants.PROPERTY_CLIENT_CODE +" n'est pas autorisé");
    	}
    	
    	RequestFilterDto requestFilter = new RequestFilterDto();
    	populate(requestFilter, request);
    	        
        model.putAll(getPaginatedListModel(
        	request, 
        	MARK_REQUEST_LIST, 
        	_attachmentRequestService.getRequestIdsByFilter(requestFilter), 
        	AttachmentConstants.MANAGE_ATTACHMENT_JSP
        	));
           
        model.put(MARK_REQUEST_FILTER,requestFilter);
            
    	HtmlTemplate html = AppTemplateService.getTemplate(TEMPLATE_MANAGE_ATTACHMENT, getLocale(), model);                
    	return html.getHtml( );       
    }

    @View(VIEW_ATTACHMENT_DETAIL)
    public String getAttachment(HttpServletRequest request) throws AccessDeniedException {
        String strType = request.getParameter(PARAMETER_TYPE);
        
        if(StringUtils.isEmpty( strType )) {
            throw new AccessDeniedException( PERMISSION_UNAUTHORIZED );
        }
        
        int nType = Integer.parseInt(strType);       
        permission( nType );
        
    	Map<String, Object> model = getModel();
    	String strIdRequest = request.getParameter(PARAMETER_ID);
    	
    	if(StringUtils.isEmpty(strIdRequest)) {
    	    return redirectView(request, VIEW_DEFAULT);
    	}
    	
    	model.put(MARK_REQUEST,_attachmentRequestService.getRequestById(Integer.parseInt(strIdRequest),true));
    	model.put(MARK_TYPE,strType);
    	model.put(MARK_CAN_EDIT,canEditIdentity());
    	
    	HtmlTemplate html = AppTemplateService.getTemplate(TEMPLATE_ATTACHMENT_DETAIL,getLocale(),model);        
        return html.getHtml();  
    }

    /**
     * Returns true if the logged-in user has permissions to update the identity
     * @return true if user can edit identity
     */
    private boolean canEditIdentity( )
    {
        return RBACService.isAuthorized(
                IdentityPickerResourceService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                IdentityPickerResourceService.PERMISSION_UPDATE,(User) getUser()
                );
    }
    
    /**
     * Throw an exception if the user does not have the required permissions to access
     * @param nType
     * @throws AccessDeniedException
     */
    private void permission( int nType ) throws AccessDeniedException
    {
        boolean isAuthorizedToView = RBACService.isAuthorized(
                AttachmentRequestResourceIdService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                AttachmentRequestResourceIdService.PERMISSION_VIEW_REQUEST,(User) getUser()
                );
        
        boolean isAuthorizedToProcess = RBACService.isAuthorized(
                AttachmentRequestResourceIdService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                AttachmentRequestResourceIdService.PERMISSION_PROCESS_REQUEST,(User) getUser()
                );
        
        boolean isAuthorizedToViewHistory = RBACService.isAuthorized(
                AttachmentRequestResourceIdService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                AttachmentRequestResourceIdService.PERMISSION_PROCESS_REQUEST,(User) getUser()
                );
                
        if((AttachmentConstants.TYPE_VIEW==nType && !isAuthorizedToView)
                || (AttachmentConstants.TYPE_PROCESS==nType && !isAuthorizedToProcess)
                || (AttachmentConstants.TYPE_HISTORY==nType && !isAuthorizedToViewHistory)
                || (AttachmentConstants.TYPE_VIEW!=nType
                        && AttachmentConstants.TYPE_HISTORY!=nType
                        && AttachmentConstants.TYPE_PROCESS!=nType)){
            throw new AccessDeniedException( PERMISSION_UNAUTHORIZED );
        }
    }
    
    @Action(ACTION_CERTIFY)
    public String doCertify(HttpServletRequest request) throws AccessDeniedException {
        //RBAC
        boolean isAuthorized = RBACService.isAuthorized(
                AttachmentRequestResourceIdService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                AttachmentRequestResourceIdService.PERMISSION_PROCESS_REQUEST,
                (User) getUser()
                );
        
        if (!isAuthorized){
            throw new AccessDeniedException( PERMISSION_UNAUTHORIZED );
        }
        
    	String strIdRequest = request.getParameter(PARAMETER_ID);
    	if(StringUtils.isEmpty(strIdRequest)) {
    	    return redirectView(request, VIEW_DEFAULT);
    	}
    	
    	int idRequest = Integer.parseInt(strIdRequest);
    	    	
    	if(_attachmentRequestService.doProcessCertificationRequest(idRequest,AdminUserService.getAdminUser(request).getEmail())) {
    	    addInfo(AttachmentConstants.MSG_SUCCESS_CERTIFICATION_REQUEST,getLocale());
    	    
    	       //Notify user
            AttachmentUtils.runWorkflowAction(
                    AttachmentConstants.PROPERTY_WORKFLOW_ID,
                    AttachmentConstants.PROPERTY_ACCEPT_REQUEST_ACTION_ID,
                    idRequest,
                    AttachmentConstants.WORKFLOW_RESOURCE_TYPE,
                    request,
                    getUser()
                    );
    	} else {
    	    addError(AttachmentConstants.MSG_ERROR_CERTIFICATION_REQUEST,getLocale());
    	}
	    	
    	return redirect(request,VIEW_ATTACHMENT_DETAIL,PARAMETER_ID,idRequest,PARAMETER_TYPE,AttachmentConstants.TYPE_HISTORY); 
    }
    
    
    @Action(ACTION_REJECTED)
    public String doRejected(HttpServletRequest request) throws AccessDeniedException {
        //RBAC
        boolean isAuthorized = RBACService.isAuthorized(
                AttachmentRequestResourceIdService.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                AttachmentRequestResourceIdService.PERMISSION_PROCESS_REQUEST,
                (User) getUser()
                );
        
        if (!isAuthorized){
            throw new AccessDeniedException( PERMISSION_UNAUTHORIZED );
        }
        
    	String strIdRequest = request.getParameter(PARAMETER_ID);
    	String strRefusalReason = request.getParameter(PARAMETER_REFUSAL_REASON);
    	
    	if(StringUtils.isEmpty(strIdRequest)) {
    	    return redirectView(request, VIEW_DEFAULT);
    	}
    	
    	int idRequest = Integer.parseInt(strIdRequest);
    
    	if(StringUtils.isEmpty(strRefusalReason)) {
    	    addError(AttachmentConstants.MSG_REFUSAL_REASON_EMPTY,getLocale());
    	    return redirect(request,VIEW_ATTACHMENT_DETAIL,PARAMETER_ID,idRequest);
    	}
    	
    	_attachmentRequestService.doRejectRequest(
    		idRequest, 
    		strRefusalReason, 
    		AdminUserService.getAdminUser(request).getEmail()
    		);
    	
    	//Notify user
    	AttachmentUtils.runWorkflowAction(
    	        AttachmentConstants.PROPERTY_WORKFLOW_ID,
    	        AttachmentConstants.PROPERTY_REJECT_REQUEST_ACTION_ID,
    	        idRequest,
    	        AttachmentConstants.WORKFLOW_RESOURCE_TYPE,
    	        request,
    	        getUser()
    	        );
    	
    	addInfo(AttachmentConstants.MSG_SUCCESS_REFUSAL_REQUEST,getLocale());
        return redirect(request,VIEW_ATTACHMENT_DETAIL,PARAMETER_ID,idRequest,PARAMETER_TYPE,AttachmentConstants.TYPE_HISTORY); 
    }
    
    @Action(ACTION_REINIT_CACHE)
    public String doReinitCache(HttpServletRequest request) {       
        _identityTaskCacheService.removeCache();
        
        return redirectView( request, VIEW_DEFAULT );
    }
    
    @Action(ACTION_UPDATE_REQUEST_CACHE)
    public String doUpdateRequestCache(HttpServletRequest request) {
        String strIdRequest = request.getParameter(PARAMETER_ID);
        
        if(StringUtils.isEmpty(strIdRequest)) {
            return redirectView(request, VIEW_DEFAULT);
        }
        
        int id = Integer.parseInt(strIdRequest);
        _attachmentRequestService.updateRequestDtoCache(id);
        
        return redirect(request,VIEW_ATTACHMENT_DETAIL,PARAMETER_ID,id,PARAMETER_TYPE,AttachmentConstants.TYPE_PROCESS); 

    }
    
    @Override
    List<RequestDto> getItemsFromIds(List<Integer> listIds){
        return _attachmentRequestService.getRequestListByIds(listIds);
    }

}
