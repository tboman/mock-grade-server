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

import com.vmware.config.AppConfig;
import com.vmware.config.TestConfig;
import com.vmware.model.Consts;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class SsoAuthTest extends JerseyTest {

    private static final String SERVER_ENDPOINT = "/ssoauth";

    @Override
    protected Application configure() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        return new AppConfig().property("contextConfig", context);
    }

    @Test
    public void validOTP() {
        final Response result = postFormData(Consts.VALID_ACCOUNTID);
        verifyResult(result, "0000", "123", Response.Status.OK);
    }

    @Test
    public void invalidOTP() {
        final Response result = postFormData(Consts.INVALID_ACCOUNTID);
        verifyResult(result, "0001", "", Response.Status.OK);
    }

    @Test
    public void badAccountId() {
        final Response result = postFormData(Consts.UNKNOWN_ACCOUNTID);
        verifyResult(result, "1040", "", Response.Status.BAD_REQUEST);
    }

    @Test
    public void internalServerError() {
        final Response result = postFormData(Consts.GRADE_ERROR);
        verifyResult(result, "2050", "", Response.Status.BAD_REQUEST);
    }

    @Test
    public void remoteDbError() {
        final Response result = postFormData(Consts.REMOTE_DB_ERROR);
        verifyResult(result, "2396", "", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void remoteServiceError() {
        final Response result = postFormData(Consts.REMOTE_SVC_ERROR);
        verifyResult(result, "2696", "", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void serviceUnavailableError() {
        final Response result = postFormData(Consts.SVC_UNAVAILABLE);
        verifyResult(result, "", "", Response.Status.SERVICE_UNAVAILABLE);
    }

    /**
     * Helper methods
     */
    private void verifyResult(Response result, String resultCode, String organizationId, Response.Status statusCode) {
        assertThat(result.readEntity(String.class)).isEqualTo("Result="+resultCode+"\nOrganizationGroupId="+organizationId);
        assertThat(Response.Status.fromStatusCode(result.getStatus())).isEqualTo(statusCode);
    }

    private Response postFormData(String accountId) {

        Form form = new Form();
        form.param("accountId", accountId);

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);
        return target(SERVER_ENDPOINT).request().post(entity);
    }
}
