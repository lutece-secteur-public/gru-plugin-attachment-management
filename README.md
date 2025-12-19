# Plugin Attachment Management

## Overview

The **attachment-management** plugin allows administrators to manage **identity certification requests** submitted via the **attachment-request** plugin.

🔗 Related plugin:  
https://github.com/lutece-secteur-public/gru-plugin-attachment-request

It provides tools to review requests, access attached documents, validate or reject submissions, and notify users of decisions.

---

## Main Features

### Attachment Certification Management

Administrators can:

- List identity certification requests
- View request details and attached documents
- Validate or reject requests
- View request history
- Modify user identity (with proper permissions)

---

## Access Control (RBAC)

Access to administration features is restricted by RBAC permissions.

The following permissions must be assigned to users:

- `VIEW_REQUEST`  
  View request details

- `PROCESS_REQUEST`  
  Accept or reject a request

- `VIEW_HISTORY`  
  View request history

### Additional Permission for Identity Modification

To enable identity modification, the user must have **both of the following permissions**:

- `UPDATE` permission on the resource  
  **Identity Search Service (Identity Picker)** — `IDENTITYPICKER`

- `VIEW` permission on the resource  
  **Identity Search Service (Identity Picker)** — `IDENTITYPICKER`

Both permissions are required for the identity modification feature to be available.


## Application Configuration

An **application client code** with the appropriate permissions is required.

The following properties must be configured in:

`attachment-management.properties`

```properties
# Client application code
attachment-management.client.code=

# Identitystore configuration
attachment-management.identitystore.ApiEndPointUrl=
attachment-management.identitystore.AccessManagerEndPointUrl=
attachment-management.identitystore.AccessManagerCredential=
```

---

## Identity Modification

User identity can be **modified directly from the attachment certification request management screen**.

This feature relies on the **Identity Picker** plugin.

🔗 Identity Picker plugin:  
https://github.com/lutece-secteur-public/gru-plugin-identitypicker

The following properties must be configured in:

`identitypicker.properties`

```properties
# Default client code
identitypicker.default.client.code=

# Identitystore configuration
identitypicker.identitystore.apiEndPointUrl=
identitypicker.identitystore.accessManagerEndPointUrl=
identitypicker.identitystore.accessManagerCredentials=
```

⚠️ **Important**

- The client code must have permissions to **update identities** in IdentityStore.
- Without the required RBAC permission, the **Edit Identity** button will not be displayed.

---

## Attachments Storage (S3)

Attached files are retrieved from an **S3-compatible storage**.

Configure the following file:

`sthree.properties`

```properties
# S3 connection configuration
s3Url=
s3Key=
s3Password=
```

---

## Workflow & User Notification

A workflow is used to **notify users** when their request is accepted or rejected.

### Workflow Configuration

Override the following properties in:

`attachment-management.properties`

```properties
attachment-management.workflow.id=
attachment-management.workflow.reject.request.action.id=
attachment-management.workflow.accept.request.action.id=
```

### Notification Task

The provider `AttachmentRequestEmailProviderManager` supplies user information for the workflow task:

**`TaskNotifyGru`** — Notify a user.

⚠️ **Important**

If necessary, add the following dependency to enable the notification task:

🔗 GRU Workflow Notify Module:  
https://github.com/lutece-secteur-public/gru-module-workflow-notifygru

## Attachment Requests Cache

To **improve the user experience** and **reduce waiting times**, the plugin includes an internal **cache** called `attachmentRequestCacheService`. This cache reduces calls to the external service by temporarily storing the results of **identity certification requests**.  

### Cache Activation

The cache can be enabled from the interface:  System -> Cache Management


### Cache Refresh

On the request management page, a **“Clear Cache” button** allows fetching **new requests** by forcing data retrieval from the external service.  

⚠️ **Important**: if request statuses are changed outside the application (e.g., in the external service), it is recommended to **clear the cache** to ensure the displayed information is up to date.

