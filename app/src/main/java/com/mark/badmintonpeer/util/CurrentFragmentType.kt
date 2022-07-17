package com.mark.badmintonpeer.util

import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    GROUP(getString(R.string.group)),
    CHATROOM(getString(R.string.chatroom)),
    NEWS(getString(R.string.news)),
    PROFILE(getString(R.string.profile)),
    DETAIL(""),
    FILTER(""),
    CREATE(getString(R.string.create)),
    CHAT(""),

}
