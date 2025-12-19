/*
 * Copyright (c) 2002-2025, Mairie de Paris
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

package fr.paris.lutece.plugins.attachmentmanagement.business;

import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for
 * Request objects
 */

public final class RequestHome {

    // Static variable pointed at the DAO instance
    private static IRequestDAO _dao = (IRequestDAO) SpringContextService.getBean("attachment-management.requestDAO");

    /**
     * Private constructor - this class need not be instantiated
     */

    private RequestHome() {
    }

    /**
     * Create an instance of the request class
     * 
     * @param request The instance of the Request which contains the informations to
     *               store
     * @return The instance of request which has been created with its primary key.
     */

    public static Request create(Request request) {
    	_dao.insert(request);
    
    	return request;
    }

    /**
     * Update of the request data specified in parameter
     * 
     * @param request The instance of the Request which contains the data to store
     * @return The instance of the request which has been updated
     */

    public static Request update(Request request) {
    	_dao.store(request);
    
    	return request;
    }

    /**
     * Remove the request whose identifier is specified in parameter
     * 
     * @param nRequestId The request Id
     */

    public static void remove(int nRequestId) {
        _dao.delete(nRequestId);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a request whose identifier is specified in parameter
     * 
     * @param nKey The request primary key
     * @return an instance of Request
     */

    public static Request findByPrimaryKey(int nKey) {
        return _dao.load(nKey);
    }

    /**
     * Returns an instance of a request by task
     * @param strTask
     * @return an instance of Request
     */
    public static Request findByTask(String strTask) {
        return _dao.loadByTask(strTask);
    }

    /**
     * Load the data of all the request objects and returns them in form of a
     * collection
     * 
     * @return the list which contains the data of all the request objects
     */

    public static List<Request> findAll() {
        return _dao.selectRequestsList();
    }
    
    /**
     * Load the data of all the request objects by list of tasks and returns them in form of a
     * collection
     * 
     * @return the list of request
     */

    public static List<Request> findByTasks(List<String> tasksList) {
        return _dao.selectRequestsList(tasksList);
    }

}
