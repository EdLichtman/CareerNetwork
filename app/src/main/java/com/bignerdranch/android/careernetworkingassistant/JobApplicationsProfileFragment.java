package com.bignerdranch.android.careernetworkingassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;


/**
 * Created by edwardlichtman on 10/7/15.
 */
public class JobApplicationsProfileFragment extends Fragment {

    private static final int REQUEST_PHOTO = 2;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private String mPhotoFileLocation = "IMG_ProfilePhoto.jpg";
    private File mPhotoFile;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, JobApplicationPagerActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_photo, container, false);

        createFieldsAndListeners(v);
        updatePhotoView();

        return v;
    }

    private void createFieldsAndListeners(View v) {

        mPhotoButton = (ImageButton) v.findViewById(R.id.profileCamera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = mPhotoFileLocation != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoFile = getProfilePhoto();
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.profilePhoto);


    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            bitmap = PictureUtils.rotate(bitmap, PictureUtils.Rotation.COUNTER_CLOCKWISE);
            bitmap = PictureUtils.flip(bitmap, PictureUtils.Direction.HORIZONTAL);
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private File getProfilePhoto() {
        File externalFilesDir = getContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, mPhotoFileLocation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }



}
