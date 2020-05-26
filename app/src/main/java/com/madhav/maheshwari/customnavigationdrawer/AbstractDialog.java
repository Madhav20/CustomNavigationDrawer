package com.madhav.maheshwari.customnavigationdrawer;

import android.app.Activity;
import android.app.Dialog;

import android.content.res.Configuration;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class AbstractDialog implements DialogInterface {

    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;
    public static final int NO_ICON = -111;
    public static final int NO_ANIMATION = -111;

     Dialog mDialog;
     Activity mActivity;
     String title;
     String message;
     boolean mCancelable;
     DialogButton mPositiveButton;
     DialogButton mNegativeButton;
     int mAnimationResId;
     String mAnimationFile;
     LottieAnimationView mAnimationView;

    public View dialogView;
    EditText subject_name;

    public AbstractDialog(@NonNull Activity mActivity,
                             @NonNull String title,
                             @NonNull String message,
                             boolean mCancelable,
                             @NonNull DialogButton mPositiveButton,
                             @NonNull DialogButton mNegativeButton,
                             @RawRes int mAnimationResId,
                             @NonNull String mAnimationFile) {
        this.mActivity = mActivity;
        this.title = title;
        this.message = message;
        this.mCancelable = mCancelable;
        this.mPositiveButton = mPositiveButton;
        this.mNegativeButton = mNegativeButton;
        this.mAnimationResId = mAnimationResId;
        this.mAnimationFile = mAnimationFile;
    }

    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        dialogView = inflater.inflate(R.layout.custom_dialog, container, false);

        subject_name = dialogView.findViewById(R.id.subject_name);
        EditText attended_lecture = dialogView.findViewById(R.id.attended_lecture);
        EditText total_lecture = dialogView.findViewById(R.id.total_lecture);
        MaterialButton submitButton = dialogView.findViewById(R.id.submit_button);
        mAnimationView = dialogView.findViewById(R.id.animation_view);


        if (mPositiveButton != null) {
            submitButton.setVisibility(View.VISIBLE);
            submitButton.setText(mPositiveButton.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mPositiveButton.getIcon() != NO_ICON) {
                submitButton.setIcon(mActivity.getDrawable(mPositiveButton.getIcon()));
            }

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPositiveButton.getOnClickListener().onClick(com.madhav.maheshwari.customnavigationdrawer.AbstractDialog.this, BUTTON_POSITIVE);
                }
            });
        } else {
            submitButton.setVisibility(View.INVISIBLE);
        }

        // If Orientation is Horizontal, Hide AnimationView
        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAnimationView.setVisibility(View.GONE);
        } else {
            // Set Animation from Resource
            if (mAnimationResId != NO_ANIMATION) {
                mAnimationView.setVisibility(View.VISIBLE);
                mAnimationView.setAnimation(mAnimationResId);
                mAnimationView.playAnimation();

                // Set Animation from Assets File
            } else if (mAnimationFile != null) {
                mAnimationView.setVisibility(View.VISIBLE);
                mAnimationView.setAnimation(mAnimationFile);
                mAnimationView.playAnimation();

            } else {
                mAnimationView.setVisibility(View.GONE);
            }
        }

        return dialogView;
    }


    public void show() {
        if (mDialog != null) {
            mDialog.show();
        } else {
            throwNullDialog();
        }
    }


    @Override
    public void cancel() {
        if (mDialog != null) {
            mDialog.cancel();
        } else {
            throwNullDialog();
        }
    }

    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        } else {
            throwNullDialog();
        }
    }

    private void throwNullDialog() {
        throw new NullPointerException("Called method on null Dialog. Create dialog using `Builder` before calling on Dialog");
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, int which);
    }
}
