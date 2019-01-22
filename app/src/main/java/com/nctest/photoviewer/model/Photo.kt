package com.nctest.photoviewer.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Photo() : Parcelable {

    internal var id: Int = 0

    internal var albumId: Int = 0

    internal var title: String? = null

    internal var url: String? = null

    @SerializedName("thumbnailUrl")
    internal var thumbnail: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        albumId = parcel.readInt()
        title = parcel.readString()
        url = parcel.readString()
        thumbnail = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(albumId)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}
