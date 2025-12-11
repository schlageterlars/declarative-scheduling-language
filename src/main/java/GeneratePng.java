

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import generated.DSLLexer;
import generated.DSLParser;
import model.Schedule;
import processing.Builder;
import processing.Interpreter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GeneratePng {
    private GeneratePng() {}

    public static void main(String[] args) throws Exception {

        DSLLexer lexer;
        if (args.length == 0) {
            lexer = new DSLLexer(CharStreams.fromStream(System.in));
        } else {
            lexer = new DSLLexer(CharStreams.fromFileName(args[0]));
        }

        DSLParser parser = new DSLParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.schedule();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.printf("%d error(s) detected%n", parser.getNumberOfSyntaxErrors());
            System.exit(1);
        }

        Schedule ast = new Builder().build(tree);
        Map<String, Map<String, String>> result = new Interpreter().interpret(ast);

        StringBuilder plantUML = new StringBuilder();
        for (var entry : result.entrySet()) {
            String busId = entry.getKey();
            plantUML.append(busId+"\n");
            Map<String, String> stops = entry.getValue();
            // Convert time->stop into stop->list of times
            Map<String, List<String>> stopToTimes = new LinkedHashMap<>();

            for (var stopEntry : stops.entrySet()) {
                String time = stopEntry.getKey();
                String stop = stopEntry.getValue();
                stopToTimes.computeIfAbsent(stop, k -> new ArrayList<>()).add(time);
            }
            for (var row : stopToTimes.entrySet()) {
                plantUML.append("| ")
                        .append(row.getKey())
                        .append(" | ")
                        .append(String.join(" | ", row.getValue()))
                        .append(" |\n");
            }
        }

        System.out.println(plantUML.toString());

        String[] lines = plantUML.toString().split("\n");
        int rowHeight = 30;
        int firstColWidth = 250;
        int otherColWidth = 80;

        int maxCols = 0;
        for (String line : lines) {
            String[] cells = line.split("\\|");
            int colCount = 0;
            for (String cell : cells) {
                if (!cell.trim().isEmpty()) colCount++;
            }
            if (colCount > maxCols) maxCols = colCount;
        }

        int width = firstColWidth + (maxCols - 1) * otherColWidth;
        int height = lines.length * rowHeight;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Hintergrund
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Linien
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] cells = line.split("\\|");
            int x = 0;
            int colIndex = 0;

            int visibleCells = (int) java.util.Arrays.stream(cells)
                    .filter(s -> !s.trim().isEmpty())
                    .count();

            for (String cell : cells) {
                cell = cell.trim();
                if (!cell.isEmpty()) {
                    int colWidth = (colIndex == 0) ? firstColWidth : otherColWidth;

                    g.drawRect(x, i * rowHeight, colWidth, rowHeight);

                    if (visibleCells == 1) {
                        g.setFont(new Font("Arial", Font.BOLD, 16));
                    } else {
                        g.setFont(new Font("Arial", Font.PLAIN, 14));
                    }

                    g.drawString(cell, x + 5, i * rowHeight + 20);
                    x += colWidth;
                    colIndex++;
                }
            }
        }

        g.dispose();
        ImageIO.write(image, "png", new File("timeplan.png"));
        System.out.println("PNG-Busplan erstellt!");
    }
}
