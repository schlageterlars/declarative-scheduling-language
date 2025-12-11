package model;

import java.util.List;

public class Schedule implements Visitable {
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

    public List<Sequence> getSequences() {
        return this.sequences;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSchedule(this);
    }
}
