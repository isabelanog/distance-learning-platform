package com.dlp.authuser.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Api(tags = "Refresh Scope")
public class RefreshScopeController {

    @Value("${authuser.refreshscope.name}")
    private String name;

    @RequestMapping("/refreshscope")
    public String refreshScope() {
        return this.name;
    }
}
