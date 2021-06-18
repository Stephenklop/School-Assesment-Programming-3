package com.stephenklop.breakingbadassesment.domain;

public class Quote {
    private int mId;
    private String mQuote, mAuthor, mSeries;

    public Quote(int mId, String mQuote, String mAuthor, String mSeries) {
        this.mId = mId;
        this.mQuote = mQuote;
        this.mAuthor = mAuthor;
        this.mSeries = mSeries;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmQuote() {
        return mQuote;
    }

    public void setmQuote(String mQuote) {
        this.mQuote = mQuote;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmSeries() {
        return mSeries;
    }

    public void setmSeries(String mSeries) {
        this.mSeries = mSeries;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "mId=" + mId +
                ", mQuote='" + mQuote + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mSeries='" + mSeries + '\'' +
                '}';
    }
}
