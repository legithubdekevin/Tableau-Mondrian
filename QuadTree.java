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

    QuadTree(Tree coord, QuadTree v1, QuadTree v2, QuadTree v3, QuadTree v4){
        this.coord=coord;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
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

    public static QuadTree addPoint(Tree coord, QuadTree a){
        if(coord.getCoord().getx()<a.getCoord().getx()&& coord.getCoord().gety()<a.getCoord().gety()&& a.v1!=null){
            return new QuadTree(a.coord, addPoint(coord, a.v1), a.v2, a.v3, a.v4);
        }
        else if(coord.getCoord().getx()>=a.getCoord().getx()&&coord.getCoord().gety()<a.getCoord().gety()&& a.v2!=null){
            return new QuadTree(a.coord, a.v1, addPoint(coord, a.v2), a.v3, a.v4);
        }
        else if(coord.getCoord().getx()>=a.getCoord().getx()&&coord.getCoord().gety()>=a.getCoord().gety()&& a.v3!=null){
            return new QuadTree(a.coord, a.v1, a.v2, addPoint(coord, a.v3), a.v4);
        }
        else if(coord.getCoord().getx()<a.getCoord().getx()&&coord.getCoord().gety()>=a.getCoord().gety()&& a.v4!=null){
            return new QuadTree(a.coord, a.v1, a.v2, a.v3, addPoint(coord, a.v4));
        }
        else{
            return new QuadTree(coord);
        }
    }


}
