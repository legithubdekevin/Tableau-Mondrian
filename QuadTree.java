public class QuadTree {
    QuadTree v1,v2,v3,v4;
    Tree coord;

    QuadTree(Tree coord){
        this.coord=coord;
        this.v1 = null;
        this.v2 = null;
        this.v3 = null;
        this.v4 = null;
    }

    public Point getCoord() {
        return coord.getCoord();
    }

    public static Tree getTreeContained(Point p, QuadTree a){
        if(p.getx()<a.getCoord().getx()&& p.gety()<a.getCoord().gety()&& a.v1!=null){
            return getTreeContained(p, a.v1);
        }
        else if(p.getx()>=a.getCoord().getx()&&p.gety()<a.getCoord().gety()&& a.v2!=null){
            return getTreeContained(p, a.v2);
        }
        else if(p.getx()>=a.getCoord().getx()&&p.gety()>=a.getCoord().gety()&& a.v3!=null){
            return getTreeContained(p, a.v3); 
        }
        else if(p.getx()<a.getCoord().getx()&&p.gety()>=a.getCoord().gety()&& a.v4!=null){
            return getTreeContained(p, a.v4); 
        }
        else{
            return a.coord;
        }
    }


}
