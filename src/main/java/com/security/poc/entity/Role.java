package com.security.poc.entity;

import com.security.poc.constant.AdminLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "role_hierarchy_levels",
            joinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "admin_level")
    private Set<AdminLevel> adminLevels = new HashSet<>();

    public boolean hasPermission(String permissionName) {
        if (this.privileges == null) {
            return false;
        }
        return this.privileges.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }
}
