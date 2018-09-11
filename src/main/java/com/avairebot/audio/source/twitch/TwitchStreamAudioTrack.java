/*
 * Copyright (c) 2018.
 *
 * This file is part of AvaIre.
 *
 * AvaIre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AvaIre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AvaIre.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.avairebot.audio.source.twitch;

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.stream.M3uStreamAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.stream.M3uStreamSegmentUrlProvider;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamSegmentUrlProvider;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager.getChannelIdentifierFromUrl;

/**
 * Audio track that handles processing Twitch tracks.
 * <p>
 * This is a copy of {@link com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioTrack}
 */
public class TwitchStreamAudioTrack extends M3uStreamAudioTrack {

    private static final Logger log = LoggerFactory.getLogger(TwitchStreamAudioTrack.class);

    private final TwitchStreamAudioSourceManager sourceManager;

    /**
     * @param trackInfo     Track info
     * @param sourceManager Source manager which was used to find this track
     */
    public TwitchStreamAudioTrack(AudioTrackInfo trackInfo, TwitchStreamAudioSourceManager sourceManager) {
        super(trackInfo);

        this.sourceManager = sourceManager;
    }

    /**
     * @return Name of the channel of the stream.
     */
    public String getChannelName() {
        return getChannelIdentifierFromUrl(trackInfo.identifier);
    }

    @Override
    protected M3uStreamSegmentUrlProvider createSegmentProvider() {
        return new TwitchStreamSegmentUrlProvider(getChannelName());
    }

    @Override
    protected HttpInterface getHttpInterface() {
        return sourceManager.getHttpInterface();
    }

    @Override
    public void process(LocalAudioTrackExecutor localExecutor) throws Exception {
        log.debug("Starting to play Twitch channel {}.", getChannelName());

        super.process(localExecutor);
    }

    @Override
    public AudioTrack makeClone() {
        return new TwitchStreamAudioTrack(trackInfo, sourceManager);
    }

    @Override
    public AudioSourceManager getSourceManager() {
        return sourceManager;
    }
}