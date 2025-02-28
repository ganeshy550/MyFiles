package com.capstone.cricketmatch.controller;


import com.capstone.cricketmatch.entity.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cricketmatch.entity.Match;
import com.capstone.cricketmatch.service.MatchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController // Use RestController instead of Controller for REST endpoints
@RequestMapping("/api")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/schedule")
    public String scheduleFlight(@RequestBody PlayerStats playerStats) {
        matchService.sendMatchScores(playerStats);
        return "Flight scheduled successfully!";
    }



    @GetMapping("/allMatches") //working
    public Flux<Match> allMatches() {
        return matchService.getAllMatches();
    }

    @PostMapping("/createMatch") //working
    public Mono<Match> createMatch(@RequestBody Match match) {
        return matchService.createMatch(match);
    }


    @GetMapping("/code/{code}") // working
    public Mono<Match> getMatchByCode(@PathVariable String code) {
        return matchService.getMatchByCode(code);
    }

    @GetMapping("/location/{location}") // working
    public Flux<Match> getMatchByLocation(@PathVariable String location) {
        return matchService.getMatchesByLocation(location);
    }

    // @GetMapping("/date/{date}") // Avoid ambiguous mappings
    // public Flux<Match> getMatchByDate(@PathVariable Date date) {
    //     return matchService.getMatchesByDate(date);
    // }

    @GetMapping("/status/{status}")
    public Flux<Match> getMatchByStatus(@PathVariable String status) {
        return matchService.getMatchesByStatus(status);
    }

    @PutMapping("/startMatch/{id}")
    public Mono<Match> startMatch(@PathVariable Long id) {
        return matchService.startMatch(id);
    }


    @PutMapping("/endMatch/{id}")
    public Mono<Match> endMatch(@PathVariable Long id, @RequestBody Match match) {
        return matchService.endMatch(id, match.getWinner());
    }

    @GetMapping("/getMatchStats/{id}")
    public Mono<Match> getMatchStats(@PathVariable Long id) {
        return matchService.getMatchStats(id);
    }


}
