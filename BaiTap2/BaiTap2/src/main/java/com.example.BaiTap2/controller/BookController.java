package com.example.BaiTap2.controller;

import com.example.BaiTap2.model.Book;
import com.example.BaiTap2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // LIST + SEARCH
    @GetMapping
    public String listBooks(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("books", bookService.search(keyword));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "book-list";
    }

    // ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    // SAVE NEW
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.add(book);
        return "redirect:/books";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book-form";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.update(book);
        return "redirect:/books";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
