import com.intellij.psi.PsiElement;

public class ASTPrinter {
    public static String makeAstViewModel(PsiElement root, PsiElement a, PsiElement b) {
        return makeAstViewModelInternal(root, a, b, 0);
    }

    private static String makeAstViewModelInternal(PsiElement root, PsiElement a, PsiElement b, int depth) {
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < depth; i++) {
            resultBuilder.append("--");
        }

        resultBuilder.append(root.toString()).append("\n");

        if (a == b) {
            return resultBuilder.toString();
        }

        PsiElement[] childs = root.getChildren();
        PsiElement aPreLCA = TreeUtils.preLCA(root, a);
        PsiElement bPreLCA = TreeUtils.preLCA(root, b);
        int aIndex = TreeUtils.position(aPreLCA, childs);
        int bIndex = TreeUtils.position(bPreLCA, childs);

        if (aIndex == bIndex) {
            resultBuilder.append(makeAstViewModelInternal(aPreLCA, a, b, depth + 1));
        } else {
            resultBuilder.append(makeAstViewModelInternal(aPreLCA, a, TreeUtils.extremeLeaf(aPreLCA, false), depth + 1));

            for (int i = aIndex + 1; i < bIndex; i++) {
                resultBuilder.append(makeAstViewModelInternal(childs[i], TreeUtils.extremeLeaf(childs[i], true), TreeUtils.extremeLeaf(childs[i], false), depth + 1));
            }

            resultBuilder.append(makeAstViewModelInternal(bPreLCA, TreeUtils.extremeLeaf(bPreLCA, true), b, depth + 1));
        }
        return resultBuilder.toString();
    }
}
