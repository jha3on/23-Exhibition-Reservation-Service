package app.core.rds.repository.file;

import app.core.rds.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    // ...
}