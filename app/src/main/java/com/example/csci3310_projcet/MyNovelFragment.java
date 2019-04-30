package com.example.csci3310_projcet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyNovelFragment extends Fragment {
    private TableRow row;
    private TextView x;
    private FragmentActivity myContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_novel, container, false);

        row=myFragmentView.findViewById(R.id.row_new_novel);
        x=myFragmentView.findViewById(R.id.new_novel);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new NewNovelFragment()).addToBackStack(null).commit();

            }
        });
        return myFragmentView;
    }
}
