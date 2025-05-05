package org.example.be.g_tiu.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transaction_category")
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(precision = 12)
    private BigDecimal budget;

    // Constructors
    public TransactionCategory() {
    }

    public TransactionCategory(String name, TransactionType type, BigDecimal budget) {
        this.name = name;
        this.type = type;
        this.budget = budget;
    }

}
