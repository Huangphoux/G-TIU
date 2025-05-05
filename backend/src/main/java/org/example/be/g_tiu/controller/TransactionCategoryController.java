package org.example.be.g_tiu.controller;

import org.example.be.g_tiu.model.TransactionCategory;
import org.example.be.g_tiu.response.ApiResponse;
import org.example.be.g_tiu.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TransactionCategoryController {
    @Autowired
    private TransactionCategoryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionCategory>>> getAllCategories() {
        try {
            List<TransactionCategory> categories = service.getAllCategories();
            ApiResponse<List<TransactionCategory>> response =
                    new ApiResponse<>(true, categories, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<TransactionCategory>> errorResponse =
                    new ApiResponse<>(false, null, "Failed to fetch categories: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionCategory>> addCategory(@RequestBody TransactionCategory newCategory) {
        try {
            TransactionCategory category = service.addCategory(newCategory);
            ApiResponse<TransactionCategory> response =
                    new ApiResponse<>(true, category, "Category added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<TransactionCategory> errorResponse =
                    new ApiResponse<>(false, null, "Failed to add category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionCategory>> updateCategory(
            @PathVariable Long id,
            @RequestBody TransactionCategory updatedCategory) {

        try {
            TransactionCategory existingCategory = service.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, null, "Category not found"));
            }

            existingCategory.setName(updatedCategory.getName());
            existingCategory.setType(updatedCategory.getType());
            existingCategory.setBudget(updatedCategory.getBudget());

            TransactionCategory savedCategory = service.updateCategory(existingCategory);

            ApiResponse<TransactionCategory> response =
                    new ApiResponse<>(true, savedCategory, "Category updated successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<TransactionCategory> errorResponse =
                    new ApiResponse<>(false, null, "Failed to update category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        try {
            TransactionCategory existingCategory = service.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, null, "Category not found"));
            }

            service.deleteCategory(id);

            ApiResponse<Void> response = new ApiResponse<>(true, null, "Category deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse =
                    new ApiResponse<>(false, null, "Failed to delete category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
