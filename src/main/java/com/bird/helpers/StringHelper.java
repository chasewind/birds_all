package com.bird.helpers;

public class StringHelper {

    public static void main(String[] args) {
        System.out.println(toCamelCase("/store/item_list.htm"));
        System.out.println(toCamelCase("/store/itemPurchase_List.htm"));
        System.out.println(toCamelCase("itemPXAsSFurchase_List.htm"));

    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String toCamelCase(String str) {
        return new WordTokenizer() {

            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(Character.toUpperCase(ch));
                } else {
                    buffer.append(Character.toLowerCase(ch));
                }
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != UNDERSCORE) {
                    buffer.append(ch);
                }
            }
        }.parse(str);
    }

    private abstract static class WordTokenizer {

        protected static final char UNDERSCORE = '_';

        public String parse(String str) {
            if (isEmpty(str)) {
                return str;
            }

            int length = str.length();
            StringBuilder buffer = new StringBuilder(length);

            for (int index = 0; index < length; index++) {
                char ch = str.charAt(index);

                if (Character.isWhitespace(ch)) {
                    continue;
                }

                if (Character.isUpperCase(ch)) {
                    int wordIndex = index + 1;
                    // itemPXAsSFurchase_List
                    // itemPxAsSFurchaseList.htm
                    while (wordIndex < length) {
                        char wordChar = str.charAt(wordIndex);

                        if (Character.isUpperCase(wordChar)) {
                            wordIndex++;
                        } else if (Character.isLowerCase(wordChar)) {
                            wordIndex--;
                            break;
                        } else {
                            break;
                        }
                    }

                    if (wordIndex == length || wordIndex > index) {
                        index = parseUpperCaseWord(buffer, str, index, wordIndex);
                    } else {
                        index = parseTitleCaseWord(buffer, str, index);
                    }

                    continue;
                }

                if (Character.isLowerCase(ch)) {
                    index = parseLowerCaseWord(buffer, str, index);
                    continue;
                }

                if (Character.isDigit(ch)) {
                    index = parseDigitWord(buffer, str, index);
                    continue;
                }

                inDelimiter(buffer, ch);
            }

            return buffer.toString();
        }

        private int parseUpperCaseWord(StringBuilder buffer, String str, int index, int length) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            for (; index < length; index++) {
                ch = str.charAt(index);
                inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startDigitSentence(buffer, ch);
            } else {
                startDigitWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isDigit(ch)) {
                    inDigitWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        protected boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuilder buffer, char ch);

        protected abstract void startWord(StringBuilder buffer, char ch);

        protected abstract void inWord(StringBuilder buffer, char ch);

        protected abstract void startDigitSentence(StringBuilder buffer, char ch);

        protected abstract void startDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDelimiter(StringBuilder buffer, char ch);
    }
}
