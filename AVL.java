public class AVL {
	Tree elt;
	Integer bal;
	AVL left;
	AVL right;
	int count;
	
	public AVL(Tree elt, AVL left, AVL right) {
		this.elt = elt;
		this.left = left;
		this.right = right;
		this.bal = height(right) - height(left);
		this.count = 1;
	}
 
    public static Pair<AVL, Integer> add(AVL tree, Tree elt){
        if(tree == null){

            return new Pair<AVL, Integer>(new AVL(elt, null, null), 1);
        }else{
            if(elt == tree.elt){
                return new Pair<AVL, Integer>(tree, 0);
            }else{
                int h;
                if(Tree.weight(tree.elt) < Tree.weight(elt)){
                    Pair<AVL, Integer> res = add(tree.right, elt);
                    tree.right = res.first();
                    h = res.second();
                }else{
                    Pair<AVL, Integer> res = add(tree.left, elt);
                    tree.left = res.first();
                    h = -res.second();
                }
                if(h == 0){
                    return new Pair<AVL, Integer>(tree, 0);
                }else{
                    tree.bal = tree.bal+h;
                    tree = rebalance(tree);
                    if(tree.bal == 0){
                        return new Pair<AVL,Integer>(tree, 0);
                    }else{
                        return new Pair<AVL,Integer>(tree, 1); 		
                    }
                }
            }

        }
    }


    public static AVL deleteMax(AVL tree){
        if(tree != null){
            if(tree.right == null){
				return tree.left;
            }else{
                return rebalance(new AVL(tree.elt, tree.left, deleteMax(tree.right)));
            }
        }else{
            return null;
        }
    }

    public static Tree max(AVL tree){
        if(tree != null){
            if(tree.right == null){
                return tree.elt;
            }else{
                return max(tree.right);
            }
        }else{
            return null;
        }
    }
	
	public static Integer height(AVL tree) {
		if(tree == null) {
			return -1;
		}else if(tree.right == null) {
			return height(tree.left) + 1;
		}else if(tree.left == null) {
			return height(tree.right) + 1;
		}else {
			return Math.max(height(tree.left), height(tree.right)) + 1;
		}
	}
	
	public static void calc_bal(AVL tree) {
		if(tree != null) {
			tree.bal = height(tree.right) - height(tree.left);
		}
	}
	
	public static AVL rebalance(AVL tree) {
		if(tree.bal == 2) {
			if(tree.right.bal >=0) {
				return ROTG(tree);
			}else {
				tree.right = ROTD(tree.right);
				return ROTG(tree);
			}
		}else if(tree.bal == -2) {
			if(tree.left.bal <= 0) {
				return ROTD(tree);
			}else {
				tree.left = ROTG(tree.left);
				return ROTD(tree);
			}
		}else {
			return tree;
		}
	}

	private static AVL ROTD(AVL tree) {
		AVL B = tree.left;
		int a =tree.bal; int b =B.bal;
		tree.left = B.right;
		B.right = tree;
		tree.bal=a-Math.min(b,0)+1;
		B.bal=Math.max(a+2,Math.max(a+b+2,b+1));
		return B;
	}

	private static AVL ROTG(AVL tree) {
		AVL B=tree.right;
		int a=tree.bal;
		int b=B.bal;

		tree.right = B.left;
		B.left = tree;

		tree.bal=a-Math.max(b,0)-1;
		B.bal = Math.min(a-2,Math.min(a+b-2,b-1));
		return B;
	}
	public static void PrintAVL(AVL tree){
		if(tree!=null){
			PrintAVL(tree.left);
			System.out.println("Valeur" + tree.elt+" Balance :"+tree.bal);
			PrintAVL(tree.right);
		}
	}
}