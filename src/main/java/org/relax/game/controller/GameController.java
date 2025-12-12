package org.relax.game.controller;

import org.relax.game.models.Response;
import org.relax.game.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    GameService game;

    @PostMapping("/start")
    public ResponseEntity<String> start() {
        game = new GameService();
        return ResponseEntity.ok("Game started");
    }

    @PostMapping("/spin")
    public Response spin() {
        game.initGrid();
        int roundWin = game.calculateRound();
        return new Response(game.getGrid(), game.getPayout(), roundWin);
    }

    // assume avalanche happens after roundWin>0
    @PostMapping("/avalanche")
    public Response avalanche() {
        game.updateGrid();
        int roundWin = game.calculateRound();
        return new Response(game.getGrid(), game.getPayout(), roundWin);
    }

    // when user decides to end the game with a final round
    @PostMapping("/final")
    public Response finalRound() {
        game.finalRound();
        return new Response(game.getGrid(), game.getPayout(), 0);
    }
}
