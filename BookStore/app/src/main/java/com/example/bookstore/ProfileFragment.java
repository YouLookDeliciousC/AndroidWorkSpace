package com.example.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.Manifest.permission.CALL_PHONE;


/* *
 *Author: Goat Chen
 */

public class ProfileFragment extends Fragment {
    DatabaseHelper db;
    Button buttonModifyData;
    Button buttonDeleteAccount;
    Button buttonLogout;
    Button buttonCall;
    public static String OFFICE_PHONE = "0172331061";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView t = getActivity().findViewById(R.id.etPUsername);
        t.setText("User Name: " + MainActivity.GLOBAL_USERNAME);
        db = new DatabaseHelper(getContext());

        buttonModifyData = getActivity().findViewById(R.id.btnModifyData);
        buttonDeleteAccount = getActivity().findViewById(R.id.btnDeleteAccount);
        buttonLogout = getActivity().findViewById(R.id.btnLogout);
        buttonCall = getActivity().findViewById(R.id.btnCall);


        modifyData();
        deleteAccount();
        logout();
        contactUs();

    }

    private void contactUs() {
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + OFFICE_PHONE));

                if(ContextCompat.checkSelfPermission(getContext(),CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(i);
                }else{
                    requestPermissions(new String[]{CALL_PHONE},1);
                }

            }
        });
    }

    private void logout() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),BeginActivity.class);
                startActivity(i);
                MainActivity.GLOBAL_USERNAME="";
                MainActivity.GLOBAL_PASSWORD="";
                MainActivity.GLOBAL_PHONE="";
                MainActivity.GLOBAL_REALNAME="";


            }
        });
    }

    private void deleteAccount() {
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteUser(MainActivity.GLOBAL_USERNAME);
                Toast.makeText(getContext(),"Your account has been deleted!",Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getContext(),BeginActivity.class);
                        MainActivity.GLOBAL_USERNAME="";
                        MainActivity.GLOBAL_PASSWORD="";
                        MainActivity.GLOBAL_PHONE="";
                        MainActivity.GLOBAL_REALNAME="";
                        startActivity(i);
                    }
                },2000);
            }
        });
    }

    private void modifyData() {
        buttonModifyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ModifyActivity.class);
                startActivity(i);

            }
        });
    }

}
