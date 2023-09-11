package com.example.bankapplication.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Manager {
    private Integer id;
    private String firstName;
    private String lastName;
    private int status;
    private String description;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
