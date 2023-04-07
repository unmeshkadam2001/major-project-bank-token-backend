package com.pratiti.project.model;

import com.pratiti.project.entity.Servicetype;

import java.util.List;

public class ServiceAndTypes {

    private String serviceName;
    private List<Servicetype> servicetypes;

    public String getServiceName() {
        return serviceName;
    }

    public List<Servicetype> getServicetypes() {
        return servicetypes;
    }

    public void setServicetypes(List<Servicetype> servicetypes) {
        this.servicetypes = servicetypes;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
