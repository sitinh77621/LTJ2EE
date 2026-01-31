package com.example.BaiTap2.service;

import com.example.BaiTap2.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    @PostConstruct
    public void init() {
        books.add(new Book(1, "Lập trình Spring Boot", "Đặng Chí Bảo"));
        books.add(new Book(2, "Lập trình OOP", "Đặng Chí Bảo"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book findById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Book book) {
        int newId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
        book.setId(newId);
        books.add(book);
    }

    public void update(Book updatedBook) {
        for (Book book : books) {
            if (book.getId() == updatedBook.getId()) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                break;
            }
        }
    }

    public void delete(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    // SEARCH
    public List<Book> search(String keyword) {
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());
    }
}
