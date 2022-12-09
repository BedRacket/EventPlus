package org.bedracket.eventbus.event;

@SuppressWarnings("unused SpellCheckingInspection")
public interface Blockable {
    boolean isBlocked();

    void setBlocked(boolean block);
}