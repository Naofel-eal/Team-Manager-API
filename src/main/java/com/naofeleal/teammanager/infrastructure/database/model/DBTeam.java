package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class DBTeam {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne(mappedBy = "team", cascade = CascadeType.PERSIST, orphanRemoval = true)
    public DBManager manager;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public List<DBUser> members = new ArrayList<>();

    public DBTeam() {}

    public DBTeam(Long id, DBManager manager, List<DBUser> members) {
        this.id = id;
        this.manager = manager;
        this.members = members != null ? new ArrayList<>(members) : new ArrayList<>();
    }

    public DBTeam(DBManager manager, List<DBUser> members) {
        this.manager = manager;
        this.members = members != null ? new ArrayList<>(members) : new ArrayList<>();
    }
}
