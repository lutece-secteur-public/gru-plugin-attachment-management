<jsp:useBean id="manageAttachment" scope="session" class="fr.paris.lutece.plugins.attachmentmanagement.web.ManageAttachmentJspBean" />
<% String strContent = manageAttachment.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
