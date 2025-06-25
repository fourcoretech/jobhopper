package com.resume.common.library.util;

import com.resume.common.library.exception.ResumeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@UtilityClass
@Slf4j
public class ResumeUtil {
    private static final List<String> ALLOWED_FILE_EXTENSIONS = List.of("pdf", "docx");

    public static void validateFile(MultipartFile file) throws IOException {
        long maxFileSize = 5 * 1024 * 1024;
        if (file == null || file.isEmpty() || file.getBytes().length == 0) {
            log.error("Invalid resume file uploaded: {}", file != null ? file.getOriginalFilename() : "null");
            throw new ResumeException("File is empty or null. Please upload a valid file.", HttpStatusCode.valueOf(400));
        } else if (file.getSize() > maxFileSize) {
            throw new ResumeException("File size exceeds the maximum limit of 5MB.", HttpStatusCode.valueOf(400));
        } else if (!isValidFileFormat(file)) {
            log.error("Invalid file format uploaded: {}", file.getOriginalFilename());
            throw new ResumeException("Invalid file format. Only PDF and DOCX files are supported.", HttpStatusCode.valueOf(400));
        }
    }

    private static boolean isValidFileFormat(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            return false;
        }
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        return ALLOWED_FILE_EXTENSIONS.contains(fileExtension);
    }
}
