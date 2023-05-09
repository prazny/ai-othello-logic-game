package org.example.runner;

import org.example.runner.players.Player;

import java.util.Map;

public interface GameRunner {
    public void run();
    public Map<Player, Integer> getStats();
    public long getTime();
    public void setNotice(boolean notice);
    public void setRoundNumber(int roundNumber);
}
