package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class AttachService {

    @Value("${attach.folder.name}")
    private String folderName;

    @Value("${attach.url}")
    private String attachUrl;
    @Autowired
    private AttachRepository attachRepository;

    public AttachDTO save(MultipartFile file, Integer id) {
        String pathFolder = getYmDString(); // 2022/04/23
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // jpg

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();

//            if (extension.equals("mp4")) {
//                    IContainer container = IContainer.make();
//                    container.open(path.toString(), IContainer.Type.READ, null);
//                    long duration = container.getDuration();
//                    entity.setDuration(duration);
//                    container.close();
//            }
            entity.setId(key);
            entity.setPath(pathFolder); // 2022/04/23
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setPrtId(id);
            entity.setUrl(path.toString());

            attachRepository.save(entity);
            log.info("attach saved successfully");

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(key);
            attachDTO.setOriginalName(entity.getOriginalName());
            attachDTO.setUrl(getUrl(entity.getId()));
            attachDTO.setPrtId(entity.getPrtId());

            return attachDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            File file = new File(url);

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            log.info("attach is ready to open ");
            return bytes;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public ResponseEntity<Resource> download(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();

            Path file = Paths.get(url);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("download successfully");
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                log.warn("Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.warn("Error: {}" + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String getUrl(String id) {
        return attachUrl + "/open/get/" + id;
    }


    private AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() ->
                new AppBadRequestException("image not found"));
    }

    public PageImpl<AttachDTO> attachPagination(int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
            return new PageImpl<>(getAttachDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    public boolean delete(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);
        if (optional.isPresent()){
            log.info("attach not found");
            return false;
        }
        AttachEntity entity = optional.get();
        Path path = Paths.get(folderName + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension());
        try {
            File fileToDelete = new File(path.toUri());
            attachRepository.deleteById(id);
            log.info("attach is  deleted");
            return fileToDelete.delete();
        } catch (Exception e) {
            log.warn("Error occurred while deleting the file: {}" + e.getMessage());
            System.out.println("Error occurred while deleting the file: " + e.getMessage());
        }
      return true;
    }

    private List<AttachDTO> getAttachDTOS(List<AttachEntity> list) {
        if (list.isEmpty()) {
            log.info("attach not found in pagination");
            throw  new ItemNotFoundException("attach not found");
        }
        List<AttachDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setSize(entity.getSize());
        dto.setPath(entity.getPath());
        dto.setId(entity.getId());
        dto.setOriginalName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setCreatedData(entity.getCreatedData());
        return dto;
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


}
