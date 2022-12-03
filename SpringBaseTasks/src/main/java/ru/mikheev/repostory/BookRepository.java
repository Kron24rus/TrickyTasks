package ru.mikheev.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mikheev.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
