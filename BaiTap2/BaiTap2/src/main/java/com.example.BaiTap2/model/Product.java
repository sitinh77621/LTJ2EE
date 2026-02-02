package com.example.BaiTap2.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @Length(max = 200, message = "Tên hình ảnh không quá 200 ký tự")
    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá phải >= 1")
    @Max(value = 999999, message = "Giá không quá 999999")
    private Long price;

    private Category category;
}
