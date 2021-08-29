package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.game.Game;

public abstract class GameEvent {
    private String name;
    private int excuteSeconds;
    private int priority;

    public GameEvent(String name, int excuteSeconds, int priority) {
        this.name = name;
        this.excuteSeconds = excuteSeconds;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public int getExcuteSeconds() {
        return this.excuteSeconds;
    }

    public int getPriority() {
        return this.priority;
    }

    public abstract void excute(Game game);
}
