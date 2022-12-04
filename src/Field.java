public class Field {
    int[][] data = new int[8][8];
    char[] dot_variants = {' ', '○', '●'};
    String horiz_border = "+-----+-----+-----+-----+-----+-----+-----+-----+";

    void draw() {
        System.out.println(horiz_border);
        for (int i = 0; i < 8; i++) {
            StringBuilder line_of_dots = new StringBuilder();
            for (int num : data[i]) {
                line_of_dots.append("|  ").append(dot_variants[num]).append("  ");
            }
            System.out.println(line_of_dots.append("|"));
            System.out.println(horiz_border);
        }
    }

}
