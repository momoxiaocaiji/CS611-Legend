import java.util.List;

public class CirculateList {
    private List<? extends Character> list;
    private int head;

    public CirculateList(List<? extends Character> list){
        this.list = list;
        // list should not be empty
        head = 0;
    }

    public Character getHead() {
        return list.get(head);
    }

    public void goNext() {
        head = (head + 1) % list.size();
        while (list.get(head).getHP() == 0 ){
            head = (head + 1) % list.size();
        }
    }


}
