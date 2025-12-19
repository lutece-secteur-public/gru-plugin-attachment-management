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

import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Statement;

/**
 * This class provides Data Access methods for Request objects
 */
public final class RequestDAO implements IRequestDAO {
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_request, task, refusal_reason, agent, date_treatment FROM attachment_management_request WHERE id_request = ?";
    private static final String SQL_QUERY_SELECT_BY_TASK = "SELECT id_request, task, refusal_reason, agent, date_treatment FROM attachment_management_request WHERE task = ?"; 
    private static final String SQL_QUERY_INSERT = "INSERT INTO attachment_management_request ( id_request, task, refusal_reason, agent ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM attachment_management_request WHERE id_request = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE attachment_management_request SET id_request = ?, task = ?, refusal_reason = ?, agent = ?, date_treatment = ? WHERE id_request = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_request, task, refusal_reason, agent, date_treatment FROM attachment_management_request";
    private static final String SQL_QUERY_SELECTALL_BY_TASKSLIST = "SELECT id_request, task, refusal_reason, agent, date_treatment FROM attachment_management_request WHERE task IN (";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert(Request request) {
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
    	    int nIndex = 0;
    	    daoUtil.setInt(++nIndex, request.getIdRequest());
    	    daoUtil.setString(++nIndex, request.getTask());
    	    daoUtil.setString(++nIndex, request.getRefusalReason());
    	    daoUtil.setString(++nIndex, request.getAgent());
    
    	    daoUtil.executeUpdate();
    	    if (daoUtil.nextGeneratedKey()) {
    	        request.setIdRequest(daoUtil.getGeneratedKeyInt(1));
    	    }
    	}
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Request load(int nId) {
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT)) {
    	    daoUtil.setInt(1, nId);
    	    daoUtil.executeQuery();
    
    	    Request request = null;
    
    	    if (daoUtil.next()) {
        		request = new Request();
        
        		request.setIdRequest(daoUtil.getInt("id_request"));
        		request.setTask(daoUtil.getString("task"));
        		request.setRefusalReason(daoUtil.getString("refusal_reason"));
        		request.setAgent(daoUtil.getString("agent"));
        		request.setDateTreatment(daoUtil.getTimestamp("date_treatment"));
    	    }
    
    	    return request;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Request loadByTask(String strTask) {
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT_BY_TASK)) {
    	    daoUtil.setString(1, strTask);
    	    daoUtil.executeQuery();
    
    	    Request request = null;
    
    	    if (daoUtil.next()) {
        		request = new Request();
        
        		request.setIdRequest(daoUtil.getInt("id_request"));
        		request.setTask(daoUtil.getString("task"));
        		request.setRefusalReason(daoUtil.getString("refusal_reason"));
        		request.setAgent(daoUtil.getString("agent"));
        		request.setDateTreatment(daoUtil.getTimestamp("date_treatment"));
    	    }
    
    	    return request;
    	}
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete(int nRequestId) {
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_DELETE)) {
    	    daoUtil.setInt(1, nRequestId);
    	    daoUtil.executeUpdate();
    	}
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store(Request request) {
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_UPDATE)) {
    
    	    int nIndex = 0;
    	    daoUtil.setInt(++nIndex, request.getIdRequest());
    	    daoUtil.setString(++nIndex, request.getTask());
    	    daoUtil.setString(++nIndex, request.getRefusalReason());
    	    daoUtil.setString(++nIndex, request.getAgent());
    	    daoUtil.setTimestamp(++nIndex, request.getDateTreatment());
    	    
    	    daoUtil.setInt(++nIndex, request.getIdRequest());
    
    	    daoUtil.executeUpdate();
    	}
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Request> selectRequestsList() {
    	List<Request> listRequests = new ArrayList<>();
    	try (DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECTALL)) {
    	    daoUtil.executeQuery();
    
    	    while (daoUtil.next()) {
        		Request request = new Request();
        		request.setIdRequest(daoUtil.getInt("id_request"));
        		request.setTask(daoUtil.getString("task"));
        		request.setRefusalReason(daoUtil.getString("refusal_reason"));
        		request.setAgent(daoUtil.getString("agent"));
        		request.setDateTreatment(daoUtil.getTimestamp("date_treatment"));
        		
        		listRequests.add(request);
    	    }
    
    	    return listRequests;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Request> selectRequestsList(List<String> tasksList) {
    	List<Request> listRequests = new ArrayList<>();
    	String strQuery = SQL_QUERY_SELECTALL_BY_TASKSLIST + 
    		 tasksList.stream( ).map( t -> "?" ).collect( Collectors.joining( "," ) ) + ")";
    	
    	try (DAOUtil daoUtil = new DAOUtil(strQuery)) {
    	    int nIndex=1;
    	    for(String strTask:tasksList) {
    	        daoUtil.setString(nIndex++,strTask);
    	    }
    	    
    	    daoUtil.executeQuery();
    
    	    while (daoUtil.next()) {
        		Request request = new Request();
        		request.setIdRequest(daoUtil.getInt("id_request"));
        		request.setTask(daoUtil.getString("task"));
        		request.setRefusalReason(daoUtil.getString("refusal_reason"));
        		request.setAgent(daoUtil.getString("agent"));
        		request.setDateTreatment(daoUtil.getTimestamp("date_treatment"));
        		
        		listRequests.add(request);
    	    }
    	    return listRequests;
    	}
    }
}
