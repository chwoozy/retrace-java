package com.stackify.sandbox.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CustomEP {
    GETUSERS("getusers"),
    GETTODOS("gettodos"),
    GETPHOTOS("getphotos"),
    GETPOSTS("getposts"),
    GETCOMMENTS("getcomments"),
    GETALBUMS("getalbums"),
    SLOWREQUEST("slowrequest"),
    BADWEBREQUEST("badwebrequest"),
    POST("post"),
    DELETE("delete"),
    PUT("put"),
    PATCH("patch");


    private String endPoint;

    CustomEP(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public static List<String> getEndPointList() {
        return Stream.of(CustomEP.values())
                .map(CustomEP::getEndPoint)
                .collect(Collectors.toList());
    }


}
