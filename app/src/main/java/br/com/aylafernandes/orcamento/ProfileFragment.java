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
import com.squareup.picasso.Picasso;

import br.com.aylafernandes.orcamento.models.User;
import de.hdodenhof.circleimageview.CircleImageView;


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
        CircleImageView imageView = view.findViewById(R.id.profileImage);
        TextView email = view.findViewById(R.id.profile_email);

        Bundle parametros = getArguments();
        User user = (User) parametros.getSerializable("user");

        if (user != null) {




            mMameAndSurname.setText(user.getName());
            email.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl()).into(imageView);



        }


        return view;

    }




}
