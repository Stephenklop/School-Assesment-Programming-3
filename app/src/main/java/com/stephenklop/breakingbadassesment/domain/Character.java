package com.stephenklop.breakingbadassesment.domain;

import android.content.res.Resources;

import com.stephenklop.breakingbadassesment.R;

public class Character {
    private int mId;
    private String mName, mBirthday, mImg, mStatus, mNickname, mPortrayed, mOccupation, mAppearance;

    public Character(int mId, String mName, String mBirthday, String mImg, String mStatus, String mNickname, String mPortrayed, String mOccupation, String mAppearance) {
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
//        switch (mStatus) {
//            case "presumed dead":
//                return Resources.getSystem().getString(R.string.presumed_dead);
//            case "deceased":
//                return Resources.getSystem().getString(R.string.deceased);
//            case "alive":
//                return Resources.getSystem().getString(R.string.alive);
//            default:
//                return Resources.getSystem().getString(R.string.unknown);
//        }

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

    public String getmOccupation() {
        return mOccupation;
    }

    public void setmOccupation(String mOccupation) {
        this.mOccupation = mOccupation;
    }

    public String getmAppearance() {
        return mAppearance;
    }

    public void setmAppearance(String mAppearance) {
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
