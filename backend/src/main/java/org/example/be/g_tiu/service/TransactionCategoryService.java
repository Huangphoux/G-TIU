package org.example.be.g_tiu.service;

import org.example.be.g_tiu.model.TransactionCategory;
import org.example.be.g_tiu.repository.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository repository;

    public List<TransactionCategory> getAllCategories() {
        return repository.findAll();
    }

    public TransactionCategory addCategory(TransactionCategory newCategory) {
        return repository.save(newCategory);
    }

    public TransactionCategory getCategoryById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TransactionCategory updateCategory(TransactionCategory updatedCategory) {
        return repository.save(updatedCategory);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
