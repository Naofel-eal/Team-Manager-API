package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class DBAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_admin_user", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE")
    )
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
