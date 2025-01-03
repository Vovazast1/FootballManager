package com.example.footballmanager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String createdBy;

    private LocalDate createdOn;

    private Long account;

    private Integer transferCommission;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    public Team() {}

    public Team(Long id, String name, String createdBy, LocalDate createdOn, Long account, List<Player> players, Integer transferCommission) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.account = account;
        this.players = players;
        this.transferCommission = transferCommission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getTransferCommission() {
        return transferCommission;
    }

    public void setTransferCommission(Integer transferCommission) {
        this.transferCommission = transferCommission;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", account=" + account +
                ", transferCommission=" + transferCommission +
                ", players=" + players +
                '}';
    }
}
