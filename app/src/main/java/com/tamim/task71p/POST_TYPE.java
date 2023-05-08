package com.tamim.task71p;

public enum POST_TYPE {
    Lost("Lost"),
    Found("Found");

    private final String postTypeValue;

    POST_TYPE(String postTypeValue) {
        this.postTypeValue = postTypeValue;
    }

    public String getPostTypeValue() {
        return postTypeValue;
    }
}
