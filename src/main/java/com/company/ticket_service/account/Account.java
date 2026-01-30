package com.company.ticket_service.account;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.payment.Payment;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 150, message = "Age must be less than 150")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRoles role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
}
