package raf.microservice.components.notificationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "types")
@Getter
@Setter
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "format", nullable = false)
    private String format;

    public Type(){}

}
