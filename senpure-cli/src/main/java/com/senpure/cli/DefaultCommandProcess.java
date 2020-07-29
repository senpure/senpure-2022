package com.senpure.cli;

/**
 * DefaultCommandProcess
 *
 * @author senpure
 * @time 2020-07-27 16:13:46
 */
public class DefaultCommandProcess implements CommandProcess {
    String feed;

    @Override
    public void feed(String feed) {
        this.feed=feed;
    }


    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
