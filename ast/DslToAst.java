// DslToAst.java
package ast;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.generated.DSLLexer;
import ast.generated.DSLParser;

public final class DslToAst {
    private DslToAst() { }

    public static void main(String[] args) throws Exception {
        DSLLexer lexer = new DSLLexer(CharStreams.fromStream(System.in));
        DSLParser parser = new DSLParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.schedule();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.printf("%d error(s) detected%n", parser.getNumberOfSyntaxErrors());
            System.exit(1);
        }

        Schedule ast = new DslBuilder().build(tree);
        System.out.printf("\"%n%s\"%n", ast);
    }
}

