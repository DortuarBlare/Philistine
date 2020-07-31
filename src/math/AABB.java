package math;

public class AABB {
    int[] min;
    int[] max;

    public AABB() {
        min = new int[2];
        max = new int[2];
    }

    public AABB (int xmin, int ymin, int xmax, int ymax){
        min = new int[2];
        max = new int[2];
        min[0] = xmin;
        min[1] = ymin;
        max[0] = xmax;
        max[1] = ymax;
    }

    public static boolean AABBvsAABB(AABB first, AABB second) {
        // Выходим без пересечения, потому что найдена разделяющая ось
        if(first.max[0] < second.min[0] || first.min[0] > second.max[0]) return false;
        if(first.max[1] < second.min[1] || first.min[1] > second.max[1]) return false;

        // Разделяющая ось не найдена, поэтому существует по крайней мере одна пересекающая ось
        return true;
    }

    public void update(int xmin, int ymin, int xmax, int ymax){
        min[0] = xmin;
        min[1] = ymin;
        max[0] = xmax;
        max[1] = ymax;
    }
}