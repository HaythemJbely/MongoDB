package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.Photo;
import com.softparadigm.mongo.mongdbspringboot.exception.PhotoNotFoundException;
import com.softparadigm.mongo.mongdbspringboot.repository.PhotoRepository;
import com.softparadigm.mongo.mongdbspringboot.service.PhotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public String addPhoto(String originalFilename, MultipartFile image) throws IOException {
        Photo photo
                = new Photo();
        photo.setTitle(originalFilename);
        photo.setPhoto(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
        return photoRepository.save(photo).getId();
    }

    @Override
    public Photo getPhoto(String id) {
        Optional<Photo> photo = photoRepository.findById(id);
        if(photo.isPresent()) {
            return photo.get();
        } else {
            throw new PhotoNotFoundException("Photo not found with ID: " + id);
        }
    }
}
