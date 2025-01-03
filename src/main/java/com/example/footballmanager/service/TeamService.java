package com.example.footballmanager.service;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public void updateTeam(Long id, Team team) {
        Team teamToUpdate = teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team with id " + id + " does not exist"));

        if(team.getName() != null && !team.getName().isEmpty()
                && !team.getName().equals(teamToUpdate.getName())) {
            teamToUpdate.setName(team.getName());
        }

        if (team.getAccount() != null
                && !team.getAccount().equals(teamToUpdate.getAccount())) {
            teamToUpdate.setAccount(team.getAccount());
        }

        if (team.getPlayers() != null
                && !team.getPlayers().equals(teamToUpdate.getPlayers())) {
            teamToUpdate.setPlayers(team.getPlayers());
        }

        if (team.getTransferCommission() != null
                && !team.getTransferCommission().equals(teamToUpdate.getTransferCommission())) {
            teamToUpdate.setTransferCommission(team.getTransferCommission());
        }

        teamRepository.save(teamToUpdate);
    }

    public void deleteTeam(Long id) {
        findTeamById(id).getPlayers().forEach(player -> player.setTeam(null));

        if (teamRepository.existsById(id))
            teamRepository.deleteById(id);
        else
            throw new NoSuchElementException("Team does not exist");
    }

    public void assignPlayerToTeam(Long teamId, Long playerId) {
        Team team = findTeamById(teamId);
        Player player = findPlayerById(playerId);

        if (player.getTeam() != null)
            throw new IllegalStateException("Player with id " + playerId
                    + " is already assigned to a team. You can only transfer");

        performTransferActions(team, player);
    }

    public void transferPlayerToTeam(Long teamId, Long playerId) {
        Team team = findTeamById(teamId);
        Player player = findPlayerById(playerId);

        if (player.getTeam().getId().equals(teamId))
            throw new IllegalStateException("Player with id " + playerId + " is already in this team");

        Long playerPrice = (long) (player.getExperience() * 100000 / player.getAge());
        playerPrice += playerPrice * (team.getTransferCommission() / 100);
        System.out.println(playerPrice);

        if (team.getAccount() < playerPrice)
            throw new IllegalStateException("Team does not have enough money to perform transfer");
        else {
            player.getTeam().setAccount(player.getTeam().getAccount() + playerPrice);
            team.setAccount(team.getAccount() - playerPrice);

            player.getTeam().getPlayers().remove(player);

            performTransferActions(team, player);
        }
    }

    public Team findTeamById(Long teamId) {
        if (teamRepository.existsById(teamId))
            return teamRepository.findById(teamId).get();
        else
            throw new NoSuchElementException("Team with id " + teamId + " does not exist");
    }

    public Player findPlayerById(Long playerId) {
        if (playerRepository.existsById(playerId))
            return playerRepository.findById(playerId).get();
        else
            throw new NoSuchElementException("Player with id " + playerId + " does not exist");
    }

    public void performTransferActions(Team team, Player player) {
        team.getPlayers().add(player);
        player.setTeam(team);

        playerRepository.save(player);
        teamRepository.save(team);
    }
}
