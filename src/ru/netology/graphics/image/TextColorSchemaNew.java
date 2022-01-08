package ru.netology.graphics.image;

public class TextColorSchemaNew implements TextColorSchema {
    private char[] chars;

    public TextColorSchemaNew(char[] chars) {
        this.chars = chars;
    }

    @Override
    public char convert(int color) {

        int length = chars.length;
        int x = 255 / length;
        for (int i = 0; i < length; i++) {
            if (color >= i * x && color < (i + 1) * x) {
                return chars[i];
            }
        }
        return chars[length - 1];
    }
}
