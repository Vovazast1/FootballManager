package com.example.footballmanager.controller;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAll();
    }

    @PostMapping
    public void registerPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @PutMapping("player/{playerId}")
    public void updatePlayer(@PathVariable("playerId") Long playerId, @RequestBody Player player) {
        playerService.updatePlayer(playerId, player);
    }

    @DeleteMapping("player/{playerId}")
    public void deletePlayer(@PathVariable("playerId") Long playerId) {
        playerService.deletePlayer(playerId);
    }
}
