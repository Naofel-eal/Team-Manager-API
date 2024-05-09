package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class DBAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_admin_user"))
    public DBUser user;

    public DBAdmin(Long id, DBUser user) {
        this.id = id;
        this.user = user;
    }

    public DBAdmin(DBUser user) {
        this.user = user;
    }

    public DBAdmin() {}
}
