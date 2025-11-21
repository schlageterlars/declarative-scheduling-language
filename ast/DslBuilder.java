package ast;

import org.antlr.v4.runtime.Token;
// DslBuilder.java
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ast.generated.DSLParser;
import ast.generated.DSLParserBaseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Transformiert einen ParseTree in einen abstrakten Syntaxbaum (AST).
 * Abstraktion:
 * - Weglassen der primary-Knoten und damit auch der Klammer-Tokens
 * - Weglassen von expr- und multExpr-Knoten mit nur einem Kind-Knoten
 * - einheitliche Transformation von expr- und multExpr-Knoten
 *   mit drei Kind-Knoten
 */
public final class DslBuilder extends DSLParserBaseListener {
    private final Stack<Object> stack = new Stack<Object>();

    public Schedule build(ParseTree tree) {
        new ParseTreeWalker().walk(this, tree);
        return (Schedule) this.stack.pop();    
    }

    private int numberOfSemanticErrors;

    private void semanticError(Token t, String error) {
        this.numberOfSemanticErrors++;
        System.err.printf("line %d column %d: %s%n",
                          t.getLine(), t.getCharPositionInLine(), error);
    }

    public int getNumberOfSemanticErrors() {
        return this.numberOfSemanticErrors;
    }


    @Override
    public void exitPlace(DSLParser.PlaceContext ctx) {
        StringBuilder sb = new StringBuilder();

        for (ParseTree child : ctx.children) {
            sb.append(" " + child.getText());
        }

        // normalize multiple spaces to one 
        String combined = sb.toString().replaceAll("\\s+", " ").trim();

        stack.push(new Place(combined));
    }

    @Override
    public void exitStop(DSLParser.StopContext ctx) { 
        Place place = (Place) stack.pop();
        Duration duration = (Duration) stack.pop();


        stack.push(new Stop(place, duration));
    }

    @Override
    public void exitDuration(DSLParser.DurationContext ctx) {
        String duration = ctx.getText();
        stack.push(new Duration(duration));
    }

    @Override
    public void exitSequence(DSLParser.SequenceContext ctx) {
        Duration end = (Duration) stack.pop();

        List<Stop> stops = new ArrayList<>();
        // Assuming you pushed combined place strings
        while (!stack.isEmpty() && stack.peek() instanceof Stop) {
            stops.add(0, (Stop) stack.pop());
        }

        Place place = (Place) stack.pop();

        stops.add(0, new Stop(place, end));

        TimeRange range = (TimeRange) stack.pop();
        String identifier = (String) stack.pop();

        stack.push(new Sequence(identifier, range, stops));
    }

    @Override
    public void exitSchedule(DSLParser.ScheduleContext ctx) { 
        List<Sequence> sequences = new ArrayList<>();

        while (!stack.isEmpty() && stack.peek() instanceof Sequence) {
            sequences.add(0, (Sequence) stack.pop());
        }

        stack.push(new Schedule(sequences));
    }


    @Override
    public void exitIdentifier(DSLParser.IdentifierContext ctx) {
        stack.push(ctx.getText());
     }

     @Override
    public void exitTime_range(DSLParser.Time_rangeContext ctx) {
        stack.push(new TimeRange(ctx.getText()));
     }


}

