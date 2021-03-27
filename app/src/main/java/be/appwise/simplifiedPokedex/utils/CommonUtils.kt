package be.appwise.simplifiedPokedex.utils

object CommonUtils {
    fun getCorrectNationalDexNotation(nat_dex: Int?): String {
        return String.format("%03d", nat_dex)
    }
}