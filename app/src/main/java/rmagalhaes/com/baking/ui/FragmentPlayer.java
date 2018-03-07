package rmagalhaes.com.baking.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.models.RecipeSteps;

import static rmagalhaes.com.baking.utils.Contants.SAVED_INSTANCE_RECIPE_PLAYER_STATE;
import static rmagalhaes.com.baking.utils.Contants.SAVED_INSTANCE_RECIPE_STEPS_POSITION_STATE;
import static rmagalhaes.com.baking.utils.Contants.SAVED_INSTANCE_RECIPE_STEPS_STATE;

/**
 * Created by Rafael Magalhães on 03/03/18.
 */

public class FragmentPlayer extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView mDescription;
    private Context mContext;
    private Uri mVideoUrl;
    private long mDurationBuffer = 0;
    private int position = 0;
    private static ArrayList<RecipeSteps> mRecipeSteps = new ArrayList<>();
    private Button nextButton;

    public void setItems(ArrayList<RecipeSteps> steps, int position) {
        mRecipeSteps = steps;
        this.position = position;
        String videoUrl = mRecipeSteps.get(position).getVideoUrl();
        mVideoUrl = formatUri(videoUrl);
    }

    public void updateItemPosition(int position) {
        this.position = position;
        String videoUrl = mRecipeSteps.get(position).getVideoUrl();
        mVideoUrl = formatUri(videoUrl);
        setViewItems();
        Log.e("CLICK", "IUPDATE ");
    }

    public Uri formatUri(String videoUrl) {
        String patternString = ".mp4";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(videoUrl);
        Uri uri = null;
        if (matcher.find()) uri = Uri.parse(videoUrl);
        return uri;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.player);
        mDescription = (TextView) view.findViewById(R.id.description);
        nextButton = (Button) view.findViewById(R.id.next_step);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_RECIPE_STEPS_POSITION_STATE)) {
            position = savedInstanceState.getInt(SAVED_INSTANCE_RECIPE_STEPS_POSITION_STATE);
        }

        if (mRecipeSteps != null) {
            String videoUrl = mRecipeSteps.get(position).getVideoUrl();
            mVideoUrl = formatUri(videoUrl);

        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_RECIPE_PLAYER_STATE)) {
            mDurationBuffer = savedInstanceState.getLong(SAVED_INSTANCE_RECIPE_PLAYER_STATE);
        }

        setViewItems();
        setButtons();
    }

    private void setButtons() {
        if (nextButton != null) {
            if (position >= mRecipeSteps.size() - 1) {
                nextButton.setVisibility(View.GONE);
            }

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position++;
                    if (position >= mRecipeSteps.size() - 1) {
                        nextButton.setVisibility(View.GONE);
                    }
                    String videoUrl = mRecipeSteps.get(position).getVideoUrl();
                    mVideoUrl = formatUri(videoUrl);
                    setViewItems();
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mExoPlayer != null) mDurationBuffer = mExoPlayer.getCurrentPosition();
        outState.putLong(SAVED_INSTANCE_RECIPE_PLAYER_STATE, mDurationBuffer);
        outState.putInt(SAVED_INSTANCE_RECIPE_STEPS_POSITION_STATE, position);
    }

    public void setViewItems() {
        if (mExoPlayer != null) mExoPlayer.release();

        mDescription.setText(mRecipeSteps.get(position).getDescription());
        TrackSelector trackSelector = new DefaultTrackSelector();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        mPlayerView.setPlayer(mExoPlayer);
        if (mExoPlayer != null && mVideoUrl != null && mContext != null) {
            String userAgent = Util.getUserAgent(mContext, "Culinary");
            MediaSource mediaSource = new ExtractorMediaSource(mVideoUrl, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mDurationBuffer);
            mExoPlayer.setPlayWhenReady(true);
        } else {
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.video_placeholder);
            mPlayerView.setDefaultArtwork(icon);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mDurationBuffer = mExoPlayer.getCurrentPosition();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setViewItems();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23) {
            if(mExoPlayer != null) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23 && mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer != null || Util.SDK_INT <= 23) {
            setViewItems();
            mExoPlayer.seekTo(mDurationBuffer);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}
