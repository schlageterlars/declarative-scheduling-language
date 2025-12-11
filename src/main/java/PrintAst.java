// DslToAst.java


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import generated.DSLLexer;
import generated.DSLParser;
import model.Schedule;
import processing.Builder;

public final class PrintAst {
    private PrintAst() { }

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
        System.out.printf("\"%n%s\"%n", ast);
    }
}

