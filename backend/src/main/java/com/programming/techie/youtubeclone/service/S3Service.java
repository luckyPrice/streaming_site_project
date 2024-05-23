package com.programming.techie.youtubeclone.service;


import com.amazonaws.ResetException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService{
    private final AmazonS3 s3Client;
    public static final String Bucket = "asw-videostreaming-bucket";

    @Override
    public String uploadFile(MultipartFile file){
        //aws에 업로드.
        //키 준비

        //파일 확장자를 가져옴
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        //랜덤
        var key = UUID.randomUUID().toString() + filenameExtension;

        //메타데이터
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            //동영상 넣기
            s3Client.putObject(new PutObjectRequest(Bucket, key, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            //객체에 대한 ACL(액세스 제어 목록)에 권한을 추가하거나 권한을 수정
        } catch(IOException ioException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An Exception occured with uploading the file");
        }

        return s3Client.getUrl(Bucket, key).toString();

    }
}