package com.camunda.consulting.expense_mgt.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ApplicationHelper {

    public static URI buildURI(String url, String path){
        return UriComponentsBuilder.fromHttpUrl(url).path(path).build().encode().toUri();
    }
}
