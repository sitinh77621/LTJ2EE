package com.example.BaiTap2.controller;

import com.example.BaiTap2.model.Product;
import com.example.BaiTap2.service.CategoryService;
import com.example.BaiTap2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /* ================= LIST ================= */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    /* ================= CREATE ================= */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("category_id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        productService.updateImage(product, imageProduct);
        product.setCategory(categoryService.get(categoryId));
        productService.add(product);

        return "redirect:/products";
    }

    /* ================= EDIT ================= */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "error/404";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable int id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("category_id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        Product oldProduct = productService.get(id);
        if (oldProduct == null) {
            return "error/404";
        }

        // giữ ID cũ
        product.setId(id);

        product.setCategory(categoryService.get(categoryId));

        // nếu không upload ảnh mới → giữ ảnh cũ
        if (imageProduct.isEmpty()) {
            product.setImage(oldProduct.getImage());
        } else {
            productService.updateImage(product, imageProduct);
        }

        productService.update(product);
        return "redirect:/products";
    }

    /* ================= DELETE ================= */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Product product = productService.get(id);
        if (product == null) {
            return "error/404";
        }

        productService.delete(id);
        return "redirect:/products";
    }
}
