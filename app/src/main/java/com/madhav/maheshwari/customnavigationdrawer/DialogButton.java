package com.madhav.maheshwari.customnavigationdrawer;

public class DialogButton {
    private String title;
    private int icon;
    private com.madhav.maheshwari.customnavigationdrawer.AbstractDialog.OnClickListener onClickListener;

    public DialogButton(String title, int icon, com.madhav.maheshwari.customnavigationdrawer.AbstractDialog.OnClickListener onClickListener) {
        this.title = title;
        this.icon = icon;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public AbstractDialog.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
