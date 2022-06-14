package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.repository.FileDbRepository;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileDbService {

    private final FileDbRepository fileDbRepository;

    public FileDb store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDb filedb = new FileDb(fileName, file.getContentType(), file.getBytes());
        return fileDbRepository.save(filedb);
    }

    public FileDb getFile(String id) {
        return fileDbRepository.findById(id).get();
    }

    public Stream<FileDb> getAllFiles() {
        return fileDbRepository.findAll().stream();
    }
}
