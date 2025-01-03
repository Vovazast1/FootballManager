package com.example.footballmanager.service;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Long id, Player player) {
        Player playerToUpdate = playerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Player with id " + id + " does not exist"));

        if(player.getName() != null && !player.getName().isEmpty()
                && !player.getName().equals(playerToUpdate.getName())) {
            playerToUpdate.setName(player.getName());
        }

        if (player.getExperience() != null
                && player.getExperience() > playerToUpdate.getExperience()) {
            playerToUpdate.setExperience(player.getExperience());
        }

        if (player.getTeam() != null
                && !player.getTeam().equals(playerToUpdate.getTeam())) {
            playerToUpdate.setTeam(player.getTeam());
        }

        playerRepository.save(playerToUpdate);
    }

    public void deletePlayer(Long id) {
        if (playerRepository.existsById(id))
            playerRepository.deleteById(id);
        else
            throw new NoSuchElementException("Player does not exist");
    }
}
