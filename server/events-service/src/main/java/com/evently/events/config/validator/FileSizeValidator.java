package com.evently.events.config.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<MaxFileSize,
        MultipartFile> {

    private long maxSizeInBytes;

    @Override
    public void initialize(MaxFileSize annotation) {
        this.maxSizeInBytes = annotation.maxSizeInMB() * 1024 * 1024;
    }

    @Override
    public boolean isValid(MultipartFile file,
                           ConstraintValidatorContext context) {
        if (file == null || file.isEmpty())
            return true;
        return file.getSize() <= maxSizeInBytes;
    }
}