package com.elkusnandi.popularmovie.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elkusnandi.popularmovie.data.model.Genre;

import java.util.List;

/**
 * Class that handle transformation from a normal string to clickable link string using SpannableString.
 * Input object e.g Genre (id, name)
 * Output SpannableString e.g if clicked a genre it will sends its id and name then open search a movie of its genre
 * Created by Taruna 98 on 23/12/2017.
 */

public class TextToLinkUtils {

    private SpannableClickListener<Genre> clickListener;

    public TextToLinkUtils(SpannableClickListener<Genre> clickListener) {
        this.clickListener = clickListener;
    }

    public SpannableStringBuilder createSpannableString(List<Genre> genres, TextView textView) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        for (Genre genre : genres) {
            // Add text
            spannableStringBuilder.append(genre.getName());
            // Add link color to text
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#0645AD")),
                    spannableStringBuilder.length() - genre.getName().length(),
                    spannableStringBuilder.length(),
                    0
            );
            // Add clickable span
            spannableStringBuilder.setSpan(
                    new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            //Log.e("click", "click " + tag);
                            //clickListener.onLinkClicked(genre.getName(), genre.getId());
                            Log.v("SpanGenreClicked", "click " + genre.getName());
                            clickListener.onLinkClicked(genre);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);

                        }
                    },
                    spannableStringBuilder.length() - genre.getName().length(),
                    spannableStringBuilder.length(),
                    0
            );
            // Add divider
            spannableStringBuilder.append(" ");
        }

        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return spannableStringBuilder;
    }

    public interface SpannableClickListener<T> {
        void onLinkClicked(T itemClicked);
    }
}
