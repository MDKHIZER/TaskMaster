package com.taskmanager.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.application.entity.Team;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.TeamRepository;
import com.taskmanager.application.repository.UserRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(String name, List<String> usernames) {
        Team team = new Team();
        team.setName(name);
        List<User> users = userRepository.findByUsernameIn(usernames);
        team.setMembers(users);
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
