import java.awt.Color;

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
        if(coord.getCoord().getx()<a.getCoord().getx()){
            //Partie gauche
            if(coord.getCoord().gety()<a.getCoord().gety()){
                //Partie haute
                //V1
                System.out.println("v1");
                if(a.v1 == null){
                    return new QuadTree(a.coord, new QuadTree(coord), a.v2, a.v3, a.v4);
                }else{
                    return new QuadTree(a.coord, addPoint(coord, a.v1), a.v2, a.v3, a.v4);
                }
            }else{
                //Partie Basse
                //V4
                System.out.println("v4");
                if(a.v4 == null){
                    return new QuadTree(a.coord, a.v1, a.v2, a.v3, new QuadTree(coord));
                }else{
                    return new QuadTree(a.coord, a.v1, a.v2, a.v3, addPoint(coord, a.v4));
                }

            }
        }else{
             //Partie droite
             if(coord.getCoord().gety()<a.getCoord().gety()){
                //Partie haute
                //V2
                System.out.println("v2");
                if(a.v2 == null){
                    return new QuadTree(a.coord, a.v1, new QuadTree(coord), a.v3, a.v4);
                }else{
                    return new QuadTree(a.coord, a.v1, addPoint(coord, a.v2), a.v3, a.v4);
                }

            }else{
                //Partie Basse
                //V3
                System.out.println("v3");
                if(a.v3 == null){
                    return new QuadTree(a.coord, a.v1, a.v2, new QuadTree(coord), a.v4);
                }else{
                    return new QuadTree(a.coord, a.v1, a.v2, addPoint(coord, a.v3), a.v4);
                }
            }
        }
    }

    public static Image draw(QuadTree tree, int width, int height){
        Image img = new Image(width, height);
        img.setRectangle(0, width, 0, height, Color.black);
        drawRec(tree, img, 0, 0, width, height);
        return img;
    }

    public static void drawRec(QuadTree tree, Image img, int startX, int startY, int width, int height){
        if(tree != null){
            System.out.println(startX+", "+startY);
            System.out.println(width+"x"+height);
            System.out.println(tree.getCoord().getx()+", "+tree.getCoord().gety());
            //Draw horizontal
            System.out.println(startX+", "+width+", "+(tree.coord.getCoord().gety()+2)+", "+(tree.coord.getCoord().gety()-2));
            img.setRectangle(startX, width, Math.max(0, tree.coord.getCoord().gety()-2), tree.coord.getCoord().gety()+2, Color.white);
            //Draw vertical
            System.out.println((tree.coord.getCoord().getx()+2)+", "+(tree.coord.getCoord().getx()-2)+", "+startY+", "+height);
            img.setRectangle(Math.max(0, tree.coord.getCoord().getx()-2), tree.coord.getCoord().getx()+2, startY, height, Color.white);
            System.out.println(tree.v1);
            System.out.println(tree.v2);
            System.out.println(tree.v3);
            System.out.println(tree.v4);
            //Recall
            drawRec(tree.v1, img, startX, startY, tree.coord.getCoord().getx()-startX, tree.coord.getCoord().gety()-startY);
            drawRec(tree.v2, img, tree.coord.getCoord().getx(), startY, width-(tree.coord.getCoord().getx()-startX), (tree.coord.getCoord().gety()-startY));
            drawRec(tree.v3, img, tree.coord.getCoord().getx(), tree.coord.getCoord().gety(), width-(tree.coord.getCoord().getx()-startX), height-(tree.coord.getCoord().gety()-startY));
            drawRec(tree.v4, img, startX, tree.coord.getCoord().gety(), tree.coord.getCoord().getx()-startX, height-(tree.coord.getCoord().gety()-startY));
        }
    }


}
