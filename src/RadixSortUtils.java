class RadixSortUtils {
    public static int numLength(int num) {
        int length = 0;
        while (num > 0) {
            num /= 10;
            length++;
        }
        return length;
    }

    static int getDigitAtIndexFromEnd(int num, int index) {
        for (int i = 0; i < index; i++) {
            num /= 10;
        }
        return num % 10;
    }
}