package com.example.footballmanager.controller;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAll();
    }

    @PostMapping
    public void createTeam(@RequestBody Team team) {
        teamService.addTeam(team);
    }

    @PutMapping("team/{teamId}")
    public void updateTeam(@PathVariable("teamId") Long teamId, @RequestBody Team team) {
        teamService.updateTeam(teamId, team);
    }

    @DeleteMapping("team/{teamId}")
    public void deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @PutMapping("team/{teamId}/assign/player/{playerId}")
    public void addPlayerToTeam(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
        teamService.assignPlayerToTeam(teamId, playerId);
    }

    @PutMapping("team/{teamId}/transfer/player/{playerId}")
    public void transferTeam(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
        teamService.transferPlayerToTeam(teamId, playerId);
    }
}
