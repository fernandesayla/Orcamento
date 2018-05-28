package br.com.aylafernandes.orcamento;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        TextView mMameAndSurname = view.findViewById(R.id.nameAndSurname);
        ImageView imageView = view.findViewById(R.id.profileImage);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            imageView.setImageURI(user.getPhotoUrl());
            mMameAndSurname.setText(user.getDisplayName());

        }


        return view;

    }

//
//    private void  getUser(){
//
//        if (user != null) {
//            // Name, email address, and profile photo Url
//
//
//            String name = user.getDisplayName();
//
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            String uid = user.getUid();
//
//            updateUI(user);
//           // Log.d("USER:", email + " - "+  photoUrl + " - "+  name);
//        }
//    }

}
