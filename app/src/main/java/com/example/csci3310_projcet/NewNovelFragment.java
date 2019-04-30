package com.example.csci3310_projcet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NewNovelFragment extends Fragment {
    private EditText mNovelTitle;
    private EditText mNovelAbstract;
    private Button mNovelSubmit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_newnovel, container, false);

        mNovelTitle=myFragmentView.findViewById(R.id.novel_title);
        mNovelAbstract=myFragmentView.findViewById(R.id.novel_abstract);
        mNovelSubmit=myFragmentView.findViewById(R.id.novel_submit);
        mNovelSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> novel = new HashMap<>();
                novel.put("Author",MainActivity.CHECK);
                novel.put("Title",mNovelTitle.getText().toString());
                novel.put("Abstract", mNovelAbstract.getText().toString());
                novel.put("Chapter", Arrays.asList(""));
                db.collection("novels").add(novel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new MessageFragment()).addToBackStack(null).commit();

            }
        });
        return myFragmentView;
    }
}
