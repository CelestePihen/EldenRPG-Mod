package fr.cel.eldenrpg.util;

import net.minecraft.util.math.Vec2f;

public interface IKeyboard {
    boolean isBlocked();
    void setBlockedAll(boolean blocked);

    void setBlockForward(boolean block);
    boolean isBlockForward();

    void setBlockBackward(boolean block);
    boolean isBlockBackward();

    void setBlockLeft(boolean block);
    boolean isBlockLeft();

    void setBlockRight(boolean block);
    boolean isBlockRight();

    void setBlockJump(boolean block);
    boolean isBlockJump();

    void setBlockSneak(boolean block);
    boolean isBlockSneak();

    void setBlockSprint(boolean block);
    boolean isBlockSprint();

    void setMovementVec(Vec2f vec);
}