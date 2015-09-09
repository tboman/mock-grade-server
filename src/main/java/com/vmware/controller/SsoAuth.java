/*****************************************************************
 *
 * mock-grade-server 1.0 Beta
 *
 * Copyright (c) 2015 VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with separate copyright notices
 * and license terms. Your use of these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 * Author: Tomas Boman (tomas@vmware.com)
 */
package com.vmware.controller;

import com.vmware.model.Result;
import com.vmware.model.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.ACCEPTED;

@Path("/")
@Component
public class SsoAuth {

    @Autowired
    private ValidationService validationService;

    @POST
    @Path("/ssoauth")
    public Response ssoauth(@FormParam("accountId") String accountId) {
        Result result = validationService.validate(accountId);
        return Response.status(result.getHttpStatus()).entity(result.toResultString()).build();
    }
}
