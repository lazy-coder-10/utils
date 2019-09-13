package tech.appzilla.com.setmydate.Utils


class StringUtils private constructor() {


    init {
        throw Error("U will not able to instantiate it")
    }

    companion object {


        /**
         * Determines whether the string is null or a length of 0
         *      *
         *      * @param s String to be verified
         *      * @return `true`: Empty `false`: Not empty
         */
        private fun isEmpty(s: CharSequence?): Boolean {
            return s == null || s.length == 0
        }

        /**
         * Determine whether the string is null or all spaces
         *      *
         *      * @param s String to be verified
         *      * @return `true`: null or full space <br></br> `false`: not null and not all spaces
         */
        fun isBlank(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' }.isEmpty()
        }

        /**
         * Determine whether two strings are equal
         *      *
         *      * @param a to be verified string a
         *      * @param b String to be verified b
         *      * @return `true`: Equal to `false`: Not equal
         */


        //     /**
        //      * Check whether the two strings are case-insensitive
        //      *
        //      * @param a to be verified string a
        //      * @param b String to be verified b
        //      * @return {@code true}: Equal to {@code false}: Not equal
        //      */
        //     public static boolean equalsIgnoreCase(String a, String b) {
        //        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
        //    }

        /**
         * Null to a string of length 0
         *      *
         *      * @param s to be transferred to the string
         *      * @return s is null to a string of length 0, otherwise it will not change
         */
        fun null2Length0(s: String?): String {
            return s ?: ""
        }

        /**
         * Returns the length of the string
         *      *
         *      * @param s String
         *      * @return null Returns 0, others returns its own length
         */
        private fun length(s: CharSequence?): Int {
            return s?.length ?: 0
        }

        /**
         * Initial capitalization
         *      *
         *      * @param s to be transferred to the string
         *      * @return The first letter of the uppercase string
         */
        fun upperFirstLetter(s: String): String? {
            return if (isEmpty(s) || !Character.isLowerCase(s[0])) s else (s[0].toInt() - 32).toChar().toString() + s.substring(
                1
            )
        }

        /**
         * First letter lowercase
         *      *
         *      * @param s to be transferred to the string
         *      * @return The first letter of the lowercase string
         */
        fun lowerFirstLetter(s: String): String? {
            return if (isEmpty(s) || !Character.isUpperCase(s[0])) {
                s
            } else (s[0].toInt() + 32).toChar().toString() + s.substring(1)
        }

        /**
         *      *
         *      * @param s To reverse the string
         *      * @return Reverse the string
         */
        fun reverse(s: String): String {
            val len = length(s)
            if (len <= 1) return s
            val mid = len shr 1
            val chars = s.toCharArray()
            var c: Char
            for (i in 0 until mid) {
                c = chars[i]
                chars[i] = chars[len - i - 1]
                chars[len - i - 1] = c
            }
            return String(chars)
        }
    }


}
