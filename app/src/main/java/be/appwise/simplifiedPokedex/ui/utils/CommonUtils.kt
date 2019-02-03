package be.appwise.simplifiedPokedex.ui.utils

object CommonUtils {
    fun getCorrectNationalDexNotation(nat_dex: Int?): String {
        return String.format("%03d", nat_dex)
    }
}