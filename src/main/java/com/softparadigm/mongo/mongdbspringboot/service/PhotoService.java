package com.softparadigm.mongo.mongdbspringboot.service;

import com.softparadigm.mongo.mongdbspringboot.domain.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String addPhoto(String originalFilename, MultipartFile image) throws IOException;

    Photo getPhoto(String id);
}
