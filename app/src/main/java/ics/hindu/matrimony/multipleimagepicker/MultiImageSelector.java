package ics.hindu.matrimony.multipleimagepicker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import ics.hindu.matrimony.R;

import java.util.ArrayList;


public class MultiImageSelector {

    public static final String EXTRA_RESULT = MultiImageSelectorActivity.EXTRA_RESULT;

    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMinCount = 1;
    private int mMode = MultiImageSelectorActivity.MODE_MULTI;
    private ArrayList<String> mOriginData;
    private static MultiImageSelector sSelector;

    private MultiImageSelector() {
    }

    public static MultiImageSelector create() {
        if (sSelector == null) {
            sSelector = new MultiImageSelector();
        }
        return sSelector;
    }

    public MultiImageSelector showCamera(boolean show) {
        mShowCamera = show;
        return sSelector;
    }

    public MultiImageSelector count(int count) {
        mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector minCount(int count) {
        mMinCount = count;
        return sSelector;
    }

    public MultiImageSelector single() {
        mMode = MultiImageSelectorActivity.MODE_SINGLE;
        return sSelector;
    }

    public MultiImageSelector multi() {
        mMode = MultiImageSelectorActivity.MODE_MULTI;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images) {
        mOriginData = images;
        return sSelector;
    }

    public void start(Activity activity, int requestCode, int front) {
        final Context context = activity;
        if (hasPermission(context)) {
            activity.startActivityForResult(createIntent(context, front), requestCode);
        } else {
            Toast.makeText(context, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    public void start(Fragment fragment, int requestCode,int front) {
        final Context context = fragment.getContext();
        if (hasPermission(context)) {
            fragment.startActivityForResult(createIntent(context,front), requestCode);
        } else {
            Toast.makeText(context, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private Intent createIntent(Context context, int front) {
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, mShowCamera);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, mMaxCount);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MIN_COUNT, mMinCount);
        intent.putExtra("fornt", front);
        if (mOriginData != null) {
            intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mOriginData);
        }
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, mMode);
        return intent;
    }
}
