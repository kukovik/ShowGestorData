package com.gestordevices.structs;

import android.os.Parcel;
import android.os.Parcelable;

public class PenInfoPkt implements Parcelable {
    public static final Creator<PenInfoPkt> CREATOR = new Creator<PenInfoPkt>() {
        @Override
        public PenInfoPkt createFromParcel(Parcel in) {
            return new PenInfoPkt(in);
        }

        @Override
        public PenInfoPkt[] newArray(int size) {
            return new PenInfoPkt[size];
        }
    };
    //uint16_t magic;
    private int mSequence;
    private int[] mButtons = new int[3];
    private int[] mGyro = new int[3];
    private long mElapsedRealtimeNanos;
    //    uint8_t crc8;
    private int[] mAccel = new int[3];

//    uint8_t slowData;

    public PenInfoPkt() {
    }

    protected PenInfoPkt(Parcel in) {
        mSequence = in.readInt();
        in.readIntArray(mButtons);
        in.readIntArray(mGyro);
        in.readIntArray(mAccel);
        mElapsedRealtimeNanos = in.readLong();
    }

    public void setmElapsedRealtimeNanos(long mElapsedRealtimeNanos) {
        this.mElapsedRealtimeNanos = mElapsedRealtimeNanos;
    }

    public void setSequence(int val) {
        mSequence = val;
    }

    public void setButtons(int i, int val) {
        mButtons[i] = val;
    }

    public void setGyro(int i, int val) {
        mGyro[i] = val;
    }

    public void setAccel(int i, int val) {
        mAccel[i] = val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mSequence);
        parcel.writeIntArray(mButtons);
        parcel.writeIntArray(mGyro);
        parcel.writeIntArray(mAccel);
        parcel.writeLong(mElapsedRealtimeNanos);
    }

    public String toString() {
        final StringBuilder b = new StringBuilder(100);
        b.append(String.format("%3d ", mSequence));
        b.append(String.format("b(%4d ", mButtons[0]));
        b.append(String.format("%4d ", mButtons[1]));
        b.append(String.format("%4d) ", mButtons[2]));
        b.append(String.format("g(%6d ", mGyro[0]));
        b.append(String.format("%6d ", mGyro[1]));
        b.append(String.format("%6d) ", mGyro[2]));
        b.append(String.format("a(%6d ", mAccel[0]));
        b.append(String.format("%6d ", mAccel[1]));
        b.append(String.format("%6d) ", mAccel[2]));
        b.append(mElapsedRealtimeNanos);

        return b.toString();
    }
}
