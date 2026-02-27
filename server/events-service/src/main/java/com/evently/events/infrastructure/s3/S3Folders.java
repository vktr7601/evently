package com.evently.events.infrastructure.s3;

import lombok.Getter;

@Getter
public enum S3Folders {
    LOCATIONS("locations"),
    ARTISTS("artists");
    private final String folderName;

    S3Folders(String folderName) {
        this.folderName = folderName;
    }
}