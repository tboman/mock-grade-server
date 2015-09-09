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

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Service
public class ValidationService {
    Map<String, Result> results;

    {
        results = new HashMap<String, Result>();
    }

    @PostConstruct
    public void init() {
        results.put(Consts.VALID_ACCOUNTID,   new Result(200, "0000", "123"));
        results.put(Consts.INVALID_ACCOUNTID, new Result(200, "0001", ""));
        results.put(Consts.UNKNOWN_ACCOUNTID, new Result(400, "1040", ""));
        results.put(Consts.GRADE_ERROR,       new Result(400, "2050", ""));
        results.put(Consts.REMOTE_DB_ERROR,   new Result(500, "2396", ""));
        results.put(Consts.REMOTE_SVC_ERROR,  new Result(500, "2696", ""));
        results.put(Consts.SVC_UNAVAILABLE,   new Result(503, "", ""));
    }

    public Result validate(String accountId) {
        return results.get(accountId);
    }
}
