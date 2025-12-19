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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.attachmentmanagement.business.Request;
import fr.paris.lutece.plugins.attachmentmanagement.business.RequestHome;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestFilterDto;
import fr.paris.lutece.plugins.attachmentmanagement.dto.FileDto;
import fr.paris.lutece.plugins.attachmentmanagement.mapper.RequestMapper;
import fr.paris.lutece.plugins.attachmentmanagement.service.cache.AttachmentRequestCacheService;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentUtils;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.contract.ServiceContractSearchResponse;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskDto;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskStatusType;
import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import io.jsonwebtoken.lang.Collections;

/**
 * 
 * AttachmentRequestService
 *
 */
public class AttachmentRequestService{
       
    public static final String BEAN_NAME = "attachment-management.attachmentRequestService";
    private final IdentityExtendedService _identityExtendedService;
    private final AttachmentFileService _attachmentFileService;
    private final AttachmentRequestCacheService _attachmentRequestCacheService;
    
    /**
     * Constructor
     * @param identityExtendedService
     * @param attachmentFileService
     * @param attachmentRequestCacheService
     */
    public AttachmentRequestService(IdentityExtendedService identityExtendedService,
            AttachmentFileService attachmentFileService,
            AttachmentRequestCacheService attachmentRequestCacheService){
        _identityExtendedService=identityExtendedService;
        _attachmentFileService=attachmentFileService;
        _attachmentRequestCacheService=attachmentRequestCacheService;
    }
    
    /**
     * Retrieve all the necessary elements for the requests and cache them
     * @return request list
     */
    public List<RequestDto> getRequestList() {
    	List<RequestDto> requestListCache = _attachmentRequestCacheService.getRequests( );
    	
    	if(!Collections.isEmpty(requestListCache)) {
    	    return requestListCache;
    	}
    	
    	List<RequestDto> requestList = new ArrayList<>();   	
        List<IdentityTaskDto> acrTaskList =_identityExtendedService.getACRTaskList();
        
        if(acrTaskList!=null) {
            acrTaskList.forEach(acr->{
                IdentityDto identityDto = _identityExtendedService.getIdentityByCuid(acr.getResourceId());  
                requestList.add(RequestMapper.toRequestDto(identityDto,acr));
            });
        }
        
        loadLocalRequest(requestList);
        
        //Put in cache
        _attachmentRequestCacheService.putRequests( requestList );
    	return requestList;
    }
    
    /**
     * Allows you to retrieve the request ID, which is saved in the database. 
     * If it doesn't exist, create a new request to retrieve an ID.
     * @param requestDtoList
     */
    private void loadLocalRequest(List<RequestDto> requestDtoList) {
    	List<String> taskList = requestDtoList.stream()
    		.map(RequestDto::getTask)
    		.collect(Collectors.toList());
    	
    	List<Request> requestList = RequestHome.findByTasks(taskList);
    	Map<String,Request> mapRequest = requestList.stream()
    		.collect(Collectors.toMap(Request::getTask, Function.identity()));
    	
    	requestDtoList.forEach(d->{
    	    Request localRequest = mapRequest.get(d.getTask());
    	    if(localRequest!=null) {
        		d.setId(localRequest.getIdRequest());
        		d.setAgent(localRequest.getAgent());
        		d.setDateTreatment(localRequest.getDateTreatment());
        		d.setRefusalReason(localRequest.getRefusalReason());
    	    } else {
        		Request request = new Request();
        		request.setTask(d.getTask());
        		RequestHome.create(request);
        		
        		d.setId(request.getIdRequest());
    	    }
    	});	
    }
    
    /**
     * Get the requestDto by ID and the files if the loadFiles parameter is true.
     * @param nId
     * @param loadFiles
     * @return requestDto
     */
    public RequestDto getRequestById(int nId, boolean loadFiles) {
        //search in cache
        RequestDto requestDto = _attachmentRequestCacheService.getRequest( nId );
        //If no request was found in the cache
        if(requestDto==null) {
        	requestDto = getRequestList().stream()
        		.filter(d->nId==d.getId())
        		.findFirst()
        		.orElse(null);
        }
    	
    	//Retreive files
    	if(loadFiles && requestDto!=null) {
    	    List<FileDto> filesDto = new ArrayList<>();
    	    List<File> files = _attachmentFileService.getFiles(requestDto.getTask());
    	    
    	    //Base64
    	    for(File file : files) {
        		if(file.getPhysicalFile() != null && file.getPhysicalFile().getValue() != null) {
        		    String base64 = Base64.getEncoder().encodeToString(file.getPhysicalFile().getValue());
        		    filesDto.add(new FileDto(file.getMimeType(), base64));
        		}
    	    }
    	    requestDto.setFiles(filesDto);
    	}
    	return requestDto;
    }
    
    /**
     * Get request list by filter
     * @param filter
     * @return
     */
    public List<Integer> getRequestIdsByFilter(RequestFilterDto filter){
    	int idRequest = filter.getId();
    	String status = StringUtils.trimToEmpty(filter.getStatus()).toLowerCase();
    	String lastname = StringUtils.trimToEmpty(filter.getLastname());
    	String firstname = StringUtils.trimToEmpty(filter.getFirstname());
    	String email = StringUtils.trimToEmpty(filter.getEmail()).toLowerCase();
    	String birthdate = StringUtils.trimToEmpty(filter.getBirthdate()).toLowerCase();
    
    	Set<String> todoStatuses = Set.of(IdentityTaskStatusType.TODO.name().toLowerCase(),
    		IdentityTaskStatusType.IN_PROGRESS.name().toLowerCase());
    	
    	return getRequestList().stream()
    		.filter(d -> {
    		    if (status.isEmpty()) {
    		        return true;
    		    }
    		    String s = d.getStatus().toLowerCase();
    
    		    if (todoStatuses.contains(status)) {
    		        return todoStatuses.contains(s);
    		    } else {
    		        return s.equals(status);
    		    }
    		})
    		.filter(r -> idRequest==0 || r.getId()==idRequest)
    		.filter(r -> lastname.isEmpty() || AttachmentUtils.fuzzyMatch(lastname, r.getLastname(), 2))
    		.filter(r -> firstname.isEmpty() || AttachmentUtils.fuzzyMatch(firstname, r.getFirstname(), 2))
    		.filter(r -> email.isEmpty() || r.getEmail().toLowerCase().equals(email))
    		.filter(r -> birthdate.isEmpty() || r.getBirthdate().equalsIgnoreCase(birthdate))
    		.sorted(AttachmentUtils.getRequestComparator(filter))
    		.map(RequestDto::getId)
    		.collect(Collectors.toList());	 
    }
    
    /**
     * Get list request by ids
     * @param requestIds
     * @return list of request by ids
     */
    public List<RequestDto> getRequestListByIds(List<Integer> requestIds) {  
    	return getRequestList().stream()
        	.filter(r-> requestIds.contains(r.getId()))
        	.sorted(Comparator.comparingInt(r -> requestIds.indexOf(r.getId())))
        	.collect(Collectors.toList());
    }
    
    /**
     * Allows you to reject a request, update the local request, reject the task identity, and delete files.
     * @param nRequestId
     * @param strRefusalReason
     * @param strAgent
     */
    public void doRejectRequest(int nRequestId,String strRefusalReason, String strAgent) {
        if(nRequestId==0) {
            return;
        }
        
        //Update local request
    	Request request = RequestHome.findByPrimaryKey(nRequestId);
    	request.setRefusalReason(strRefusalReason);
    	request.setDateTreatment(new Timestamp(new Date().getTime()));
    	request.setAgent(strAgent);
    	
    	RequestHome.update(request);
    	
    	IdentityTaskDto task = _identityExtendedService.getIdentityTask(request.getTask());
    	
    	if (task != null) {
            // Remove files
            _attachmentFileService.deleteFiles(task);
            
    	    // Reject task
    	    _identityExtendedService.updateIdentityTaskStatus(request.getTask(),
    		    IdentityTaskStatusType.REFUSED);
    	    
            //Update cache
            updateRequestDtoCache(request.getIdRequest());   	    
    	}	   	
    }
    
    /**
     * Allows you to perform identity verification, 
     * change the task to the processed status, 
     * and delete files attached by the user.
     * @param nRequestId
     * @param strAgent
     * @return true if the identity is certified
     */
    public boolean doProcessCertificationRequest(int nRequestId,String strAgent) {
        if(nRequestId==0) {
            return false;            
        }
        
        RequestDto requestDto = getRequestById( nRequestId, false );
        
        if(_identityExtendedService.certifyIdentity(requestDto)){
            
            //Update local request
            Request request = RequestHome.findByPrimaryKey(nRequestId);
            request.setDateTreatment(new Timestamp(new Date().getTime()));
            request.setAgent(strAgent);
            
            RequestHome.update(request);
            
            IdentityTaskDto task = _identityExtendedService.getIdentityTask(request.getTask());
            
            if (task != null) {
                // Remove files
                _attachmentFileService.deleteFiles(task);
                
                // Proccess task
                _identityExtendedService.updateIdentityTaskStatus(request.getTask(),
                    IdentityTaskStatusType.PROCESSED);
                
                //Update cache
                updateRequestDtoCache(request.getIdRequest());
            }            
            return true;
        }
        
        return false;
    }
    
    /**
     * This method allows the request DTO object to be updated individually in the cache.
     * @param nIdRequest
     */
    public void updateRequestDtoCache(int nIdRequest) {
        Request request = RequestHome.findByPrimaryKey(nIdRequest);
        if(request!=null) {
            RequestDto requestDto = _attachmentRequestCacheService.getRequest(request.getIdRequest());
            
            if(requestDto!=null) {
                IdentityDto identityDto = _identityExtendedService.getIdentityByCuid(requestDto.getCuid()); 
                IdentityTaskDto task = _identityExtendedService.getIdentityTask(request.getTask());
        
                if(identityDto!=null && task!=null ) {
                    requestDto = RequestMapper.toRequestDto(identityDto,task);
                }
                requestDto.setId(request.getIdRequest());   
                requestDto.setAgent(request.getAgent());
                requestDto.setDateTreatment(request.getDateTreatment());
                requestDto.setRefusalReason(request.getRefusalReason());              
                requestDto.setFiles(null);
                
                //Update cache
                _attachmentRequestCacheService.updateRequest(requestDto);
            }
        }
    }
}
