package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.repository.FileDbRepository;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileDbService {

    private final FileDbRepository fileDbRepository;

    public FileDb getFile(String id) {
        return fileDbRepository.findById(id).get();
    }

    public Stream<FileDb> getAllFiles() {
        return fileDbRepository.findAll().stream();
    }
}