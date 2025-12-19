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
package fr.paris.lutece.plugins.attachmentmanagement.service.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.paris.lutece.plugins.attachmentmanagement.dto.RequestDto;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;

/**
 * 
 * AttachmentRequestCacheService
 *
 */
public class AttachmentRequestCacheService extends AbstractCacheableService {

    public static final String BEAN_NAME = "attachment-management.attachmentRequestCacheService";
    private static final String SERVICE_NAME = "attachmentRequestCacheService";   
    private static final String CACHE_TASK = "[requests]";

    @Override
    public String getName(){
        return SERVICE_NAME;
    }
    
    /**
     * Constructor
     */
    public AttachmentRequestCacheService() {
        initCache();
    }
    
    /**
     * Put list of identity task in cache
     * @param identityTaskList
     */
    public void putRequests(List<RequestDto> requests) {       
        if(getFromCache(CACHE_TASK) != null) {
            removeKey(CACHE_TASK);
        }
        
        Map<Integer,RequestDto> mapRequests = requests.stream()
                .collect(Collectors.toMap(RequestDto::getId,Function.identity()));
               
        putInCache(CACHE_TASK,mapRequests);       
    }
    
    /**
     * Get list of request in cache
     * @return list of request
     */
    public List<RequestDto> getRequests() {
        Map<Integer,RequestDto> mapRequests = (Map<Integer,RequestDto>) getFromCache(CACHE_TASK);
        if(mapRequests != null && !mapRequests.isEmpty()) {
            return mapRequests.values().stream()
                    .collect(Collectors.toList());
        }   
        return Collections.emptyList( );
    }

    /**
     * Get request by id
     * @param nId
     * @return request by id
     */
    public RequestDto getRequest(int nId) {
        Map<Integer,RequestDto> mapRequests = (Map<Integer,RequestDto>) getFromCache(CACHE_TASK);
        if(mapRequests != null && !mapRequests.isEmpty()) {
            return mapRequests.get(nId);
        }      
        return null;
    }
    
    /**
     * Updates a request in the cache
     * @param request
     */
    public void updateRequest(RequestDto request) {
        Map<Integer,RequestDto> mapRequests = (Map<Integer,RequestDto>) getFromCache(CACHE_TASK);
        if(mapRequests != null && !mapRequests.isEmpty()) {
            mapRequests.put(request.getId(),request);
            putInCache(CACHE_TASK,mapRequests);
        }     
    }
    
    /**
     * Remove cache
     */
    public void removeCache() {
        removeKey(CACHE_TASK);
    }
}
