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
package fr.paris.lutece.plugins.attachmentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.paris.lutece.plugins.attachmentmanagement.provider.FileStoreProvider;
import fr.paris.lutece.plugins.attachmentmanagement.util.AttachmentConstants;
import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.task.IdentityTaskDto;
import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.service.file.IFileStoreServiceProvider;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * 
 * AttachmentFileService
 *
 */
public class AttachmentFileService
{
    public static final String BEAN_NAME = "attachment-management.attachmentFileService";
    
    private final IdentityExtendedService _identityExtendedService;
    
    /**
     * Constructor
     * @param identityExtendedService
     */
    public AttachmentFileService(IdentityExtendedService identityExtendedService) {
        _identityExtendedService=identityExtendedService;
    }
    
    /**
     * Retrieving task files.
     * @param strTask
     * @return files
     */
    public List<File> getFiles(String strTask) {
        List<File> files = new ArrayList<>();
        IdentityTaskDto identityTaskDto = _identityExtendedService.getIdentityTask(strTask);
    
        if (identityTaskDto != null && identityTaskDto.getMetadata() != null) {
            String strProvider = identityTaskDto.getMetadata().get(AttachmentConstants.METADATA_PROVIDER_NAME);
            if (StringUtils.isNotEmpty(strProvider)) {
                JSONParser parser = new JSONParser();
        
                // FileStore Provider
                IFileStoreServiceProvider provider = FileStoreProvider.getInstance()
                    .getFileStoreServiceProvider(strProvider);
        
                try {
                    JSONArray jsonFiles = (JSONArray) parser
                        .parse(identityTaskDto.getMetadata().get(AttachmentConstants.METADATA_FILES));
        
                    for (Object jsonFile : jsonFiles) {
                        JSONObject jsonObj = (JSONObject) jsonFile;
                        File file = provider.getFile(jsonObj.get(AttachmentConstants.METADATA_FILE_PATH).toString());
                        if (file != null) {
                            files.add(file);
                        }
                    }    
                } catch (Exception e) {
                    AppLogService.error("An error occurred while retreiving file by provider {} ", strProvider,
                        e.getMessage());
                }
            }
        }
        return files;
    }
    
    
    /**
     * This method removes user attachments by retrieving information from the
     * taskstack metadata that contains the provider and file path in S3
     * 
     * @param identityTaskDto
     */
    public void deleteFiles(IdentityTaskDto identityTaskDto) {
        if (identityTaskDto.getMetadata() != null) {
            String strProvider = identityTaskDto.getMetadata().get(AttachmentConstants.METADATA_PROVIDER_NAME);
            if(StringUtils.isNotEmpty(strProvider)) {
                JSONParser parser = new JSONParser();
        
                // FileStore Provider
                IFileStoreServiceProvider provider = FileStoreProvider.getInstance()
                    .getFileStoreServiceProvider(strProvider);
        
                try {
                    JSONArray jsonFiles = (JSONArray) parser
                        .parse(identityTaskDto.getMetadata().get(AttachmentConstants.METADATA_FILES));
            
                    for (Object jsonFile : jsonFiles) {
                        JSONObject jsonObj = (JSONObject) jsonFile;
                        provider.delete(jsonObj.get(AttachmentConstants.METADATA_FILE_PATH).toString());
                    }
        
                } catch (Exception e) {
                    AppLogService.error("An error occurred while deleting file by provider {} ", strProvider,
                        e.getMessage());
                }
            }
        }
    }
}
