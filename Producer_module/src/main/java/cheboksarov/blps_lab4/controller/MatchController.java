package cheboksarov.blps_lab4.controller;

import cheboksarov.blps_lab4.model.Match;
import cheboksarov.blps_lab4.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/match")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public List<Match> findAllMatch(){
        return matchService.findAllMatch();
    }

    @PostMapping("save_match")
    public Match saveMatch(@RequestBody Match match){
        return matchService.saveMatch(match);
    }

    //@PostMapping("save_match_empty_stat")
    //public Match


    @GetMapping("/{match_id}")
    public Match getMatchById(@PathVariable Long match_id){
        return matchService.findById(match_id);
    }

    @PutMapping("update")
    public Match updateMatch(@RequestBody Match match){
        return matchService.updateMatch(match);
    }

    @DeleteMapping("/{match_id}")
    public void deleteMatch(Long match_id){
        matchService.deleteMatch(match_id);
    }



}
