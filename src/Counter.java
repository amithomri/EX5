//211321823 amit homri
public class Counter {
    private int count = 0;
    // add number to current count.
    public void increase(int number){
        this.count += number;
    }
    // subtract number from current count.
    void decrease(int number){
        this.count -= number;
    }
    // get current count.
    public int getValue(){
        return this.count;
    }

}
