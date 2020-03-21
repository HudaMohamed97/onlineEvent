package com.OnlineEvent.umangburman.event;

public interface ActivityDrawerHandler {
    void setDrawerLocked(Boolean shouldLock);
    void hideToolbar(Boolean shouldLock);
    void showDrwer(Boolean show);
    void hideItem(String menuId);
    void showItem(String menuId);


}
