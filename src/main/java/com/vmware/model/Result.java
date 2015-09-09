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
package com.vmware.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
    private String resultCode;
    private int httpStatus;
    private String organizationGroupId;

    public Result() {
    }

    public Result(int httpStatus, String resultCode, String organizationGroupId) {
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.organizationGroupId = organizationGroupId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getOrganizationGroupId() {
        return organizationGroupId;
    }

    public String toResultString() {
        StringBuilder result = new StringBuilder();
        result.append("Result="+ getResultCode());
        result.append("\n");
        result.append("OrganizationGroupId="+getOrganizationGroupId());

        return result.toString();
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}