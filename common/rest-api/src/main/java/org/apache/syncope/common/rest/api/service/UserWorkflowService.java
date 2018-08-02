/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.common.rest.api.service;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.lib.to.WorkflowFormTO;
import org.apache.syncope.common.lib.to.WorkflowTaskTO;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.apache.syncope.common.rest.api.beans.WorkflowFormQuery;

/**
 * REST operations related to user workflow.
 */
@Tag(name = "UserWorkflow")
@SecurityRequirements({
    @SecurityRequirement(name = "BasicAuthentication"),
    @SecurityRequirement(name = "Bearer") })
@Path("userworkflow")
public interface UserWorkflowService extends JAXRSService {

    /**
     * Returns a list of all available workflow forms.
     *
     * @param query query conditions
     * @return list of all available workflow forms
     */
    @GET
    @Path("forms")
    @Produces({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    PagedResult<WorkflowFormTO> getForms(@BeanParam WorkflowFormQuery query);

    /**
     * Returns a list of available forms for the given user key.
     *
     * @param userKey user key
     * @return list of available forms for the given user key
     */
    @GET
    @Path("forms/{userKey}")
    @Produces({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    WorkflowFormTO getFormForUser(@NotNull @PathParam("userKey") String userKey);

    /**
     * Claims the form for the given task id.
     *
     * @param taskId workflow task id
     * @return the workflow form for the given task id
     */
    @POST
    @Path("forms/{taskId}/claim")
    @Produces({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    WorkflowFormTO claimForm(@NotNull @PathParam("taskId") String taskId);

    /**
     * Submits a workflow form.
     *
     * @param form workflow form.
     * @return updated user
     */
    @POST
    @Path("forms")
    @Produces({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    UserTO submitForm(@NotNull WorkflowFormTO form);

    /**
     * Returns a list of available tasks for the given user key.
     *
     * @param userKey user key
     * @return list of available tasks for the given user key
     */
    @GET
    @Path("tasks/{userKey}")
    List<WorkflowTaskTO> getAvailableTasks(@NotNull @PathParam("userKey") String userKey);

    /**
     * Executes workflow task for matching id.
     *
     * @param taskId workflow task id
     * @param userTO argument to be passed to workflow task
     * @return updated user
     */
    @POST
    @Path("tasks/{taskId}/execute")
    @Produces({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML })
    UserTO executeTask(@NotNull @PathParam("taskId") String taskId, @NotNull UserTO userTO);
}
