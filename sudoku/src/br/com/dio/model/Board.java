package br.com.dio.model;

import java.util.*;


public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream)
                .noneMatch(s -> Boolean.TRUE.equals(s.isFixed()) && s.getValue() != null)) {
            // TODO: implement status determination
        }

        return spaces.stream().flatMap(Collection::stream)
        .anyMatch(s -> s.getValue() == null) ?
                GameStatusEnum.IN_PROGRESS : GameStatusEnum.COMPLETE;
    }

    public boolean hasErrors(){
        if(getStatus() == GameStatusEnum.NON_STARTER) {
            return false;
        }
        
        return spaces.stream().flatMap(Collection::stream)
        .anyMatch(s -> s.getValue() != null && !s.getValue().equals(s.getExpectedValue()));
    }

    public boolean changeValue(final int col, final int row, final Integer value) {
        final Space space = spaces.get(col).get(row);
        if (Boolean.TRUE.equals(space.isFixed())) {
            return false;
        }

        space.setValue(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        final Space space = spaces.get(col).get(row);
        if (Boolean.TRUE.equals(space.isFixed())) {
            return false;
        }

        space.clearValue();
        return true;
    }

    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearValue));
    }

    public boolean isComplete() {
        return getStatus().equals(GameStatusEnum.COMPLETE) && !hasErrors();
    }

}
