package com.stephenklop.breakingbadassesment.domain;

import java.util.List;

public class Character {
    private int mId;
    private String mName, mBirthday, mImg, mStatus, mNickname, mPortrayed;
    private List<String> mOccupation;
    private List<Integer> mAppearance;

    public Character(int mId, String mName, String mBirthday, String mImg, String mStatus, String mNickname, String mPortrayed, List<String> mOccupation, List<Integer> mAppearance) {
        this.mId = mId;
        this.mName = mName;
        this.mBirthday = mBirthday;
        this.mImg = mImg;
        this.mStatus = mStatus;
        this.mNickname = mNickname;
        this.mPortrayed = mPortrayed;
        this.mOccupation = mOccupation;
        this.mAppearance = mAppearance;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getmPortrayed() {
        return mPortrayed;
    }

    public void setmPortrayed(String mPortrayed) {
        this.mPortrayed = mPortrayed;
    }

    public List<String> getmOccupation() {
        return mOccupation;
    }

    public void setmOccupation(List<String> mOccupation) {
        this.mOccupation = mOccupation;
    }

    public List<Integer> getmAppearance() {
        return mAppearance;
    }

    public void setmAppearance(List<Integer> mAppearance) {
        this.mAppearance = mAppearance;
    }

    @Override
    public String toString() {
        return "Character{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mBirthday='" + mBirthday + '\'' +
                ", mImg='" + mImg + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mNickname='" + mNickname + '\'' +
                ", mPortrayed='" + mPortrayed + '\'' +
                ", mOccupation=" + mOccupation +
                ", mAppearance=" + mAppearance +
                '}';
    }
}
