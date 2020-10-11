package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.print.PageRange;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.database.WordDataBase;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordDBRepository;

import java.util.List;


public class WordListFragment extends Fragment {

    public static final String TAG_WORD_DETAIL_FRAGMENT = "TAG_WORD_DETAIL_FRAGMENT";
    public static final int REQUEST_CODE_WORD_DETAIL_FRAGMENT = 0;
    private RecyclerView mRecyclerView;
    private WordAdapter mWordAdapter;

    private IRepository mRepository;

    public WordListFragment() {
        // Required empty public constructor
    }


    public static WordListFragment newInstance() {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mRepository = WordDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.word_list);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        List<Word> words = mRepository.getWords();

        if (mWordAdapter == null) {
            mWordAdapter = new WordAdapter(words);
            mRecyclerView.setAdapter(mWordAdapter);
        } else {
            mWordAdapter.setWords(words);
            mWordAdapter.notifyDataSetChanged();
        }
    }

    private class WordHolder extends RecyclerView.ViewHolder {

        private TextView mWordTitle, mWordMean;
        private ImageView mDeleteWord, mEditWord, mShareWord;
        private Word mWord;

        public WordHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

            //TODO
            setListeners();

        }

        private void setListeners() {
            mDeleteWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mEditWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mShareWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        private void findHolderViews(@NonNull View itemView) {
            mWordTitle = itemView.findViewById(R.id.word_title);
            mWordMean = itemView.findViewById(R.id.word_mean);
            mDeleteWord = itemView.findViewById(R.id.delete_word);
            mEditWord = itemView.findViewById(R.id.edit_word);
            mShareWord = itemView.findViewById(R.id.share_word);
        }

        private void bindWord(Word word) {
            mWord = word;
            mWordTitle.setText(word.getTitle());
            mWordMean.setText(word.getMean());
        }

    }

    private class WordAdapter extends RecyclerView.Adapter<WordHolder> {

        private List<Word> mWords;

        public List<Word> getWords() {
            return mWords;
        }

        public void setWords(List<Word> words) {
            mWords = words;
        }

        public WordAdapter(List<Word> words) {
            mWords = words;
        }

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.word_item_row, parent, false);

            WordHolder wordHolder = new WordHolder(view);
            return wordHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {
            Word word = mWords.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_word:
                WordDetailFragment wordDetailFragment = WordDetailFragment.newInstance();

                wordDetailFragment.setTargetFragment(
                        WordListFragment.this, REQUEST_CODE_WORD_DETAIL_FRAGMENT);

                wordDetailFragment.show(getFragmentManager(), TAG_WORD_DETAIL_FRAGMENT);
                return true;
            case R.id.search_word:

                //TODO
                return true;
            case R.id.word_number:
                int numberOfWords = mRepository.getWords().size();
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle("" + numberOfWords);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_WORD_DETAIL_FRAGMENT
                && resultCode == Activity.RESULT_OK && data != null) {

            Word word = (Word) data.getSerializableExtra(WordDetailFragment.EXTRA_WORD);
            mRepository.insertWord(word);
            updateUI();
        }
    }
}