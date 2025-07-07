package org.example.mapper;

import jakarta.annotation.Resource;
import org.example.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class BookMapperTest {

    @Resource
    private BookMapper bookMapper;

    @Test
    void insert() {
        Book book = Book.builder()
                .title("论如何取悦富婆")
                .author("lkzc19")
                .build();
        book.setCreatedAt(Instant.now());
        bookMapper.insert(book);
    }

    @Test
    void deleteById() {
        bookMapper.deleteById(1);
    }

    @Test
    void updateById() {
        Book book = Book.builder()
                .title("论如何取悦富婆")
                .author("lkzc18")
                .build();
        book.setId(2L);
        book.setUpdatedAt(Instant.now());
        bookMapper.updateById(book);
    }

    @Test
    void listAll() {
        System.out.println(bookMapper.listAll());
    }
}