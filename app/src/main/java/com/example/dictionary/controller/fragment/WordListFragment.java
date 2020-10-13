package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordDBRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class WordListFragment extends Fragment {

    public static final String TAG_WORD_DETAIL_FRAGMENT = "TAG_WORD_DETAIL_FRAGMENT";
    public static final int REQUEST_CODE_WORD_DETAIL_FRAGMENT = 0;
    private RecyclerView mRecyclerView;
    private WordAdapter mWordAdapter;

    private IRepository mRepository;
    private Word mWord = new Word();

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Do You Want to Delete Word?!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mRepository.deleteWord(mWord);
                                    updateUI();
                                }
                            })
                            .setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
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

    private class WordAdapter extends RecyclerView.Adapter<WordHolder> implements Filterable {

        private List<Word> mWords;
        private List<Word> mSearchWords;


        public List<Word> getWords() {
            return mWords;
        }

        public void setWords(List<Word> words) {
            mWords = words;
            mSearchWords = new ArrayList<>(words);
            notifyDataSetChanged();
        }

        public WordAdapter(List<Word> words) {
            mWords = words;
            mSearchWords = new ArrayList<>(words);
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

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    List<Word> filteredList = new ArrayList<>();
                    if (charSequence == null || charSequence.length() == 0) {
                        filteredList.addAll(mSearchWords);
                    } else {
                        String filter = charSequence.toString().toLowerCase().trim();
                        for (Word word : mSearchWords) {
                            if (word.getTitle().toLowerCase().contains(filter) ||
                                    word.getMean().toLowerCase().contains(filter)) {
                                filteredList.add(word);
                                System.out.println(word);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (mWords != null)
                        mWords.clear();
                    else
                        mWords = new ArrayList<>();
                    if (filterResults.values != null)
                        mWords.addAll((Collection<? extends Word>) filterResults.values);
                    notifyDataSetChanged();
                }
            };
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
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_word).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mWordAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setVisible(true);
        int count = mRepository.getWords().size();
        switch (item.getItemId()) {
            case R.id.add_word:
                WordDetailFragment wordDetailFragment = WordDetailFragment.newInstance();
                wordDetailFragment.setTargetFragment(
                        WordListFragment.this, REQUEST_CODE_WORD_DETAIL_FRAGMENT);
                wordDetailFragment.show(getFragmentManager(), TAG_WORD_DETAIL_FRAGMENT);
                count += 1;
                return true;
            case R.id.word_number:
                item.setTitle(count + " words");
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