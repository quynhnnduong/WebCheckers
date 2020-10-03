package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * Holds all existing players and add new players
 * @author Quynh Duong, Sasha Persaud
 */
public class PlayerLobby {
    //
    // attributes
    //

    /**
     * Creates a HashMap for players log in
     */
    private HashSet<Player> players = new HashSet<>();

    /**
     * Holds a like to the one and only GameCenter
     * @author Joel Clyne
     */
    private final GameCenter gameCenter;

    //
    // constructors
    //

    /**
     * New PlayerLobby set up
     */
    public PlayerLobby(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }


    /**
     * Adds new player to the players map.
     * @param name name of player to add
     * @return boolean
     */
    public boolean addPlayer(String name) {
        if (isNameValid(name) && !players.contains(getPlayer(name))) {    //check if player name is valid and the name is not taken
            Player newPlayer = new Player(name);    //create a player
            players.add(newPlayer);             // TODO: (Sasha) Information Expert: Should isNameValid be
                                                // contained in the Player class, since it concerns Player data,
                                                // not PlayerLobby data?
            return true;
        }
        return false;
    }


    /**
     * Look for a Player in the PlayerLobby. If they are found, return the player.
     * If they are not found, return null.
     * @param name name of player to find
     * @return player, null
     */
    public Player getPlayer(String name){
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Gets the number of players logged in.
     * @return size of players
     */
    public int getPlayerSize(){
        return players.size();
    }

    /**
     * Removes player from the players map.
     * @param player player to remove
     */
    public void removePlayer(Player player){
        players.remove(player);
    }

    public HashSet<Player> getPlayers(){ return players; }



    /**
     * Get all of the players
     * @return set of players

    public Collection<Player> getAllPlayers() {
        return players.values();
    }
     */

    /**
     * Get all of the players name
     * @return set of players name

    public ArrayList<String> getAllPlayersName(){
        ArrayList playerNames = new ArrayList<String>();
        for (Player player : players){
            playerNames.add(player.getName());
        }
        return playerNames;
    }
     */

    /**
     * Get all of the players
     * @return set of players map

    public Map<String, Player> getPlayersMap() {
        return players;
    }
     */



    /**
     * Checks if a string contains at least one alphanumeric character or a space
     * @param name string to check
     * @return boolean
     */
    public boolean isNameValid(String name){
        return name.matches("[A-Za-z0-9 ]+");
    }

}
