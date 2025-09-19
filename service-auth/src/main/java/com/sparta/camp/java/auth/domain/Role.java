package com.sparta.camp.java.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.security.Permission;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="role")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Role {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 64)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name="role_id"),
      inverseJoinColumns = @JoinColumn(name="permission_id")
  )
  @Builder.Default
  private Set<Permission> permission = new HashSet<>();

}
