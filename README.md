# Boggle!

#### Outline
A playable Boggle game built in Java. Upon launch, the game generates a random
4-by-4 board of letters. The player then must find as many 3+ letter words as
possible by connecting adjacent and diagonal letters together before time runs
out. Words are scored based on length. The player can pause a game and return
to it later on. At the end of a game, the player is shown the list of all
missed words and the corresponding total missed score.

Upon generation, the board is "solved" by searching all possible substrings
against a spell-check dictionary, which is stored as a tree of successive 
letters to facilitate efficient searching. User-submitted strings are then 
checked against this list of valid words.