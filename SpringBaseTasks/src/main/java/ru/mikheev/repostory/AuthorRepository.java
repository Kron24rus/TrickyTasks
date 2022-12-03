package ru.mikheev.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mikheev.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
