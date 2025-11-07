package ast;

import java.util.List;

public class Schedule {
    private final List<Sequence> sequences;

    public Schedule(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sequence seq : sequences) {
            sb.append(seq).append("\n");
        }
        return sb.toString();
    }
}
