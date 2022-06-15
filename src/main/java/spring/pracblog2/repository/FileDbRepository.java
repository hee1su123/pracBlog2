package spring.pracblog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.pracblog2.domain.FileDb;

public interface FileDbRepository extends JpaRepository<FileDb, String> {
}
