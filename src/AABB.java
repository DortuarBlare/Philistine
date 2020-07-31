public class AABB {
    int[] min = new int[2];
    int[] max = new int[2];

    static boolean AABBvsAABB(AABB first, AABB second) {
        // Выходим без пересечения, потому что найдена разделяющая ось
        if(first.max[0] < second.min[0] || first.min[0] > second.max[0]) return false;
        if(first.max[1] < second.min[1] || first.min[1] > second.max[1]) return false;

        // Разделяющая ось не найдена, поэтому существует по крайней мере одна пересекающая ось
        return true;
    }
}