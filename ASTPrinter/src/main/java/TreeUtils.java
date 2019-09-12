import com.intellij.psi.PsiElement;

/**
 * Common utils for syntax tree
 */
public class TreeUtils {

    /**
     * Method that finds child position in all childs array
     * @param element element to find
     * @param arr list of all childs
     * @return index of element in array
     */
    public static int position(PsiElement element, PsiElement[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return i;
            }
        }

        return 0;
    }

    /**
     * Method that finds leftmost or rightmost leaf in tree with provided root
     * @param root tree root
     * @param isFirst if true, finds leftmost element and rightmost otherwise
     * @return leftmost or rightmost leaf in tree
     */
    public static PsiElement extremeLeaf(PsiElement root, boolean isFirst) {
        PsiElement result = root;

        while (result.getChildren().length > 0) {
            PsiElement[] childs = result.getChildren();
            result = childs[isFirst ? 0 : childs.length - 1];
        }

        return result;
    }

    /**
     * Method that returns first child of parent on path to child
     * @param parent parent node
     * @param child child node
     * @return first child of parent on path to child
     */
    public static PsiElement preLCA(PsiElement parent, PsiElement child) {
        PsiElement cur = child;
        PsiElement res = child;

        while (cur != parent) {
            res = cur;
            cur = cur.getParent();
        }

        return res;
    }
}
