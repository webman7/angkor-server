package com.adplatform.restApi.infra.file.api;

import com.adplatform.restApi.infra.file.service.AwsFileService;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
public class FileApi {
    private final FileService fileService;
    private final AwsFileService awsFileService;

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(filename)))
                .body(this.fileService.findByName(filename));
    }

//    @GetMapping(path = "/downloadFile")
//    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileUrl, @RequestParam String fileName) {
//
//        try{
//            byte[] data = awsFileService.download(fileUrl);
//            ByteArrayResource resource = new ByteArrayResource(data);
//            return ResponseEntity
//                    .ok()
//                    .contentLength(data.length)
//                    .header("Content-type", "application/pdf")
//                    .header("Content-disposition", "inline; filename=\"" + URLEncoder.encode(fileUrl, "UTF-8").replaceAll("\\+", "%20") + "\"")
//                    .body(resource);
//        }catch (IOException ex){
//            return ResponseEntity.badRequest().contentLength(0).body(null);
//        }
//    }

    @GetMapping(path = "/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String fileUrl, @RequestParam String fileName) { // 객체 다운  fileUrl : 폴더명/파일네임.파일확장자

        try{
            byte[] bytes = awsFileService.download(fileUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(contentType(fileUrl));
            httpHeaders.setContentLength(bytes.length);
            String[] arr = fileUrl.split("/");
            String type = arr[arr.length - 1];
            String fileName1 = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");
            httpHeaders.setContentDispositionFormData("attachment", fileName1); // 파일이름 지정

            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        }catch (IOException ex){
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }

    private MediaType contentType(String keyName) {
        String[] arr = keyName.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
