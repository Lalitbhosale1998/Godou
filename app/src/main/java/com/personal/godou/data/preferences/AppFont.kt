package com.personal.godou.data.preferences

enum class AppFont(val label: String) {
    NUNITO("Nunito (標準)"),
    NOTO_SANS_JP("Noto Sans JP"),
    M_PLUS_1("M PLUS 1"),
    DELA_GOTHIC_ONE("デラ・ゴシック (Dela Gothic)"),
    HACHI_MARU_POP("851マカポップ (Hachi Maru)"),
    KAISEI_DECOL("解星デコル (Kaisei Decol)"),
    KAISEI_OPTI("解星オプティ (Kaisei Opti)"),
    KOSUGI_MARU("小杉丸ゴシック (Kosugi Maru)"),
    MOCHIY_POP_P_ONE("モチーポップ (Mochiy Pop)"),
    POTTA_ONE("ポッタ (Potta One)"),
    RAMPART_ONE("ランパート (Rampart One)"),
    REGGAE_ONE("レゲエ (Reggae One)"),
    ROCKNROLL_ONE("ロックンロール (RocknRoll)"),
    WDXL_LUBRIFONT_JPN("WDXL Lubrifont"),
    YUJI_MAI("游明朝 (Yuji Mai)"),
    YUSEI_MAGIC("油星マジック (Yusei Magic)"),
    GOOGLE_SANS_FLEX("Google Sans Flex"),
    OUTFIT("Outfit"),
    PLAYFAIR("Playfair Display"),
    MONOSPACE("Monospace (等幅)"),
    SYSTEM_SANS("システム標準");

    companion object {
        fun fromStorage(value: String?): AppFont {
            return try {
                value?.let { valueOf(it) } ?: NUNITO
            } catch (e: Exception) {
                NUNITO
            }
        }
    }
}
