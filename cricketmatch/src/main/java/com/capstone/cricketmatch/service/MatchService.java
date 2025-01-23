package com.capstone.cricketmatch.service;

import java.util.List;

import com.capstone.cricketmatch.entity.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.capstone.cricketmatch.entity.Match;
import com.capstone.cricketmatch.repository.MatchRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamServiceClient teamServiceClient;

    @Autowired
    private KafkaTemplate<String, PlayerStats> kafkaTemplate;

    private static final String TOPIC = "match-score";

    public void sendMatchScores(PlayerStats playerStats) {
        kafkaTemplate.send(TOPIC, playerStats);
    }

    public Mono<Match> createMatch(Match match) {
        return matchRepository.save(match)
            .flatMap(savedMatch -> {
                // Call Team Service to create teams
                return teamServiceClient.createTeamsForMatch(
                        savedMatch.getId().toString(),
                        List.of(savedMatch.getTeam1(), savedMatch.getTeam2()),
                        match.getTeamSize()
                ).thenReturn(savedMatch);
            });
    }


    public Flux<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    public Mono<Match> getMatchByCode(String code){
        return matchRepository.findByCode(code);
    }

    // public Flux<Match> getMatchesByDate(Date date){
    //     return matchRepository.findByDate(date);
    // }

    public Flux<Match> getMatchesByLocation(String location){
        return matchRepository.findByLocation(location);
    }

    public Flux<Match> getMatchesByStatus(String status){
        return matchRepository.findByStatus(status);
    }

    
    public Mono<Match> startMatch(Long id) {
        return matchRepository.findById(id)
        .flatMap(match -> {
            match.setStatus("Ongoing");
            return matchRepository.save(match);
        })
        .switchIfEmpty(Mono.error(new RuntimeException("Match not found")));
    }
    
    public Mono<Match> endMatch(Long id,String winner) {
        return matchRepository.findById(id)
        .flatMap(match -> {
            match.setStatus("Completed");
            match.setWinner(winner);
            return matchRepository.save(match);
        })
        .switchIfEmpty(Mono.error(new RuntimeException("Match not found")));
    }


    public Mono<Match> getMatchStats(Long id) {
        return matchRepository.findById(id)
                .flatMap(match -> {
                    // Logic to fetch match stats
                    return Mono.just(match);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Match not found")));
    }
}
