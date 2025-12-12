# Relax Game Server Backend
This repository contains the backend code for a slot machine game.
## Structure
- models contains the data models for the game.
- service contains the logic of the game, including generating grids and finding clusters
- utils contains utility functions like getting pay from payoutTable
- controllers has a gamecontroller to handle requests during the game

## Features
- Generate a random grid for the slot machine
- Identify clusters of matching symbols
- Calculate payouts based on identified clusters and a payout table
- WR can be used for multiple clusters, but only the highest payout for a single cluster is considered.

## Tested RTP
The tested Return to Player (RTP) for this slot machine game is approximately 82% based on 1 million simulations.

Note: Code review task file account.java is also added to this project,
but is not part of the game backend.