package com.reptrack.api.controller;

import com.reptrack.api.dto.LeaderboardEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/ping-redis")
    public ResponseEntity<String> pingRedis() {
        redisTemplate.opsForValue().set("test-key", "test-value");
        String value = redisTemplate.opsForValue().get("test-key");
        return ResponseEntity.ok("Redis says: " + value);
    }

    @GetMapping("/{exercise}/{metric}")
    public List<LeaderboardEntryDTO> getLeaderboard(
            @PathVariable String exercise,
            @PathVariable String metric
    ) {
        String key = "leaderboard:" + exercise + ":" + metric;
        Set<ZSetOperations.TypedTuple<String>> entries = redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, 9); // top 10

        if (entries == null) return List.of();

        return entries.stream().map(entry -> {
            String[] parts = entry.getValue().split("\\|");
            return new LeaderboardEntryDTO(
                    parts[0], // email
                    parts[1], // exercise
                    Integer.parseInt(parts[2]), // weight
                    Integer.parseInt(parts[3]), // reps
                    Integer.parseInt(parts[4])  // sets
            );
        }).collect(Collectors.toList());
    }
}
