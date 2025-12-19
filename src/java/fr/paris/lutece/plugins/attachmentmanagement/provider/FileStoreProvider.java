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
package fr.paris.lutece.plugins.attachmentmanagement.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.paris.lutece.plugins.sthree.service.file.implementation.S3StorageFileService;
import fr.paris.lutece.portal.service.file.IFileDownloadUrlService;
import fr.paris.lutece.portal.service.file.IFileRBACService;
import fr.paris.lutece.portal.service.file.IFileStoreServiceProvider;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * 
 * FileStoreProvider
 *
 */
public class FileStoreProvider {
    private static final FileStoreProvider INSTANCE = new FileStoreProvider();

    // Map that will contain the list of File Service Provider
    private final Map<String, IFileStoreServiceProvider> _mapFileStoreProvider =
        new ConcurrentHashMap<>();

    // Dépendances
    private final IFileDownloadUrlService _fileDownloadUrlService;
    private final IFileRBACService _fileRBACService;

    // Configuration
    private final String s3Url;
    private final String s3Key;
    private final String s3Password;
    private final String s3DefaultFilePath;
    private final String s3ForcePathStyle;
    private final String s3Region;
    private final String s3ChecksumAlgorithm;
    private final String s3ProxyHost;
    private final String s3ProxyUsername;
    private final String s3ProxyPassword;
    private final String s3RequestTimeout;
    private final String s3ConnectionTimeout;

    private FileStoreProvider(){
        _fileDownloadUrlService =
            SpringContextService.getBean( "s3FileDownloadUrlService" );
        _fileRBACService =
            SpringContextService.getBean( "defaultFileRBACService" );

        s3Url = AppPropertiesService.getProperty( "s3Url", "" );
        s3Key = AppPropertiesService.getProperty( "s3Key", "" );
        s3Password = AppPropertiesService.getProperty( "s3Password", "" );
        s3DefaultFilePath = AppPropertiesService.getProperty( "s3DefaultFilePath", "" );
        s3ForcePathStyle = AppPropertiesService.getProperty( "s3ForcePathStyle", "" );
        s3Region = AppPropertiesService.getProperty( "s3Region", "" );
        s3ChecksumAlgorithm = AppPropertiesService.getProperty( "s3ChecksumAlgorithm", "" );
        s3ProxyHost = AppPropertiesService.getProperty( "s3ProxyHost", "" );
        s3ProxyUsername = AppPropertiesService.getProperty( "s3ProxyUsername", "" );
        s3ProxyPassword = AppPropertiesService.getProperty( "s3ProxyPassword", "" );
        s3RequestTimeout = AppPropertiesService.getProperty( "s3RequestTimeout", "" );
        s3ConnectionTimeout = AppPropertiesService.getProperty( "s3ConnectionTimeout", "" );
    }

    public static FileStoreProvider getInstance(){
        return INSTANCE;
    }

    public IFileStoreServiceProvider getFileStoreServiceProvider( String providerName ){
        String bucket = providerName.toLowerCase();

        return _mapFileStoreProvider.computeIfAbsent( bucket, name -> {
            S3StorageFileService service = new S3StorageFileService(
                _fileDownloadUrlService,
                _fileRBACService,
                s3Url,
                name,
                s3Key,
                s3Password,
                s3DefaultFilePath,
                s3ForcePathStyle,
                s3Region,
                s3ChecksumAlgorithm,
                s3ProxyHost,
                s3ProxyUsername,
                s3ProxyPassword,
                s3RequestTimeout,
                s3ConnectionTimeout
            );
            service.setName( name );
            return service;
        } );
    }
}

