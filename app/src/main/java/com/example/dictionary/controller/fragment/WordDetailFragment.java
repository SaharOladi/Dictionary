package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordDBRepository;


public class WordDetailFragment extends DialogFragment {

    public static final String EXTRA_WORD = "com.example.dictionary.controller.fragment.EXTRA_WORD";

    private EditText mWordTitle, mWordMean;

    private Word mWord;
    private IRepository mRepository;

    public WordDetailFragment() {
        // Required empty public constructor
    }

    public static WordDetailFragment newInstance() {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = WordDBRepository.getInstance(getActivity());
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_word_detail, null);

        findViews(view);
        setListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(mWord);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        return builder.create();

    }

    private void findViews(View view) {
        mWordTitle = view.findViewById(R.id.edit_text_title);
        mWordMean = view.findViewById(R.id.edit_text_mean);

        mWord = new Word();
    }

    private void setListeners() {
        mWordTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWordMean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setMean(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void sendResult(Word word){

        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WORD, word);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}