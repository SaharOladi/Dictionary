package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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


public class WordEditFragment extends DialogFragment {

    public static final String ARGS_EDIT_WORD = "ARGS_EDIT_WORD";

    public static final int RESULT_CODE_EDIT_WORD = 2;
    public static final String EXTRA_EDIT_WORD = "com.example.dictionary.controller.fragment.EXTRA_EDIT_WORD";

    private EditText mEditTitle, mEditMean, mEdit;

    private Word mWord;
    private IRepository mIRepository;

    public WordEditFragment() {
        // Required empty public constructor
    }

    public static WordEditFragment newInstance(Word word) {
        WordEditFragment fragment = new WordEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_EDIT_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIRepository = WordDBRepository.getInstance(getActivity());
        mWord = (Word) getArguments().getSerializable(ARGS_EDIT_WORD);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_word_edit, null);

        findViews(view);
        setListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
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
        mEditTitle = view.findViewById(R.id.edit_word_title);
        mEditMean = view.findViewById(R.id.edit_word_mean);
        mEdit = view.findViewById(R.id.edit_word_sentence);

        mEditTitle.setText(mWord.getTitle());
        mEditMean.setText(mWord.getMean());
        mEdit.setText(mWord.getSentence());

    }


    private void setListeners() {
        mEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mWord.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditMean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mWord.setMean(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mWord.setSentence(s.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void sendResult(Word word) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_EDIT_WORD;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EDIT_WORD, word);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}