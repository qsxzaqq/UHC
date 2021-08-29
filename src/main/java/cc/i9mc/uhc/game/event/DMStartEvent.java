package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.game.Game;

public class DMStartEvent extends GameEvent {
    public DMStartEvent() {
        super("PVP开启倒计时", 15, 2);
    }

    public void excute(Game game) {
        game.getDmBorder().setSize(0, 947);
    }
}
