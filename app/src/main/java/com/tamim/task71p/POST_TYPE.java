package com.tamim.task71p;

public enum POST_TYPE {
    LOST("Lost"),
    FOUND("Found");

    private final String postTypeValue;

    POST_TYPE(String postTypeValue) {
        this.postTypeValue = postTypeValue;
    }

    public String getPostTypeValue() {
        return postTypeValue;
    }
}
