package com.company.ticket_service.user;

import com.company.ticket_service.core.BaseEntity;
import com.company.ticket_service.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {
  @NotBlank(message = "First name is required")
  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

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
  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Ticket> tickets = new ArrayList<>();
}