package cc.i9mc.uhc.profession;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Perk {
    //A = 0 B = 1 NONE=-1
    private int type;
    private String name;
    private String displayName;
    private String[] lore;
    private int money;
}
