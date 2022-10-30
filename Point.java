public class Point {
    private int x,y;

    /**
     * Contructor Point
     * @param x
     * @param y
     */
    Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Getter X
     */
    public int getx(){
        return this.x;
    }

    /**
     * Getter Y
     */
    public int gety(){
        return this.y;
    }

    /**
     * Setter X
     */
    public void setx(int x){
        this.x=x;
    }

    /**
     * Setter Y
     */
    public void sety(int y){
        this.y=y;
    }

} 
