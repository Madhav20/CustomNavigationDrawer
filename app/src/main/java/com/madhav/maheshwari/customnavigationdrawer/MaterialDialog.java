package com.madhav.maheshwari.customnavigationdrawer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;

public class MaterialDialog extends AbstractDialog {


    protected MaterialDialog(@NonNull final Activity mActivity,
                             @NonNull String title,
                             @NonNull String message,
                             boolean mCancelable,
                             @NonNull DialogButton mPositiveButton,
                             @NonNull DialogButton mNegativeButton,
                             @RawRes int mAnimationResId,
                             @NonNull String mAnimationFile) {
        super(mActivity, title, message, mCancelable, mPositiveButton, mNegativeButton, mAnimationResId, mAnimationFile);

        initDialog();
    }

    public void initDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = createView(inflater, null);
        builder.setView(dialogView);
        // Set Cancelable property
        builder.setCancelable(mCancelable);
        // Create and show dialog
        mDialog = builder.create();
    }


    public static class Builder {
        private Activity activity;
        private String title;
        private String message;
        private boolean isCancelable;
        private DialogButton positiveButton;
        private DialogButton negativeButton;
        private int animationResId = NO_ANIMATION;
        private String animationFile;

        public Builder(@NonNull Activity activity) {
            this.activity = activity;
        }


        @NonNull
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }


        @NonNull
        public Builder setMessage(@NonNull String message) {
            this.message = message;
            return this;
        }


        @NonNull
        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        @NonNull
        public Builder setPositiveButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setPositiveButton(name, NO_ICON, onClickListener);
        }


        @NonNull
        public Builder setPositiveButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            positiveButton = new DialogButton(name, icon, onClickListener);
            return this;
        }


        @NonNull
        public Builder setNegativeButton(@NonNull String name, @NonNull OnClickListener onClickListener) {
            return setNegativeButton(name, NO_ICON, onClickListener);
        }


        @NonNull
        public Builder setNegativeButton(@NonNull String name, int icon, @NonNull OnClickListener onClickListener) {
            negativeButton = new DialogButton(name, icon, onClickListener);
            return this;
        }


        @NonNull
        public Builder setAnimation(@RawRes int animationResId) {
            this.animationResId = animationResId;
            return this;
        }

        @NonNull
        public Builder setAnimation(@NonNull String fileName) {
            this.animationFile = fileName;
            return this;
        }


        @NonNull
        public com.madhav.maheshwari.customnavigationdrawer.MaterialDialog build() {
            return new com.madhav.maheshwari.customnavigationdrawer.MaterialDialog(activity, title, message, isCancelable, positiveButton, negativeButton, animationResId, animationFile);
        }
    }
}
