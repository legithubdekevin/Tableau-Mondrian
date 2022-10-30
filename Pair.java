public class Pair<Type1, Type2> {
    private Type1 first;
    private Type2 second;

    Pair(Type1 first, Type2 second){
        this.first = first;
        this.second = second;
    }

    public Type1 first(){
        return this.first;
    }

    public Type2 second(){
        return this.second;
    }
}
