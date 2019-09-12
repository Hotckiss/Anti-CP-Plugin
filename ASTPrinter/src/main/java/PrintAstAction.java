import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Action of printing AST tree of selected text
 */
public class PrintAstAction extends AnAction {
    public PrintAstAction() {
        super();
    }

    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        if (project == null) {
            return;
        }

        if (editor == null) {
            Messages.showMessageDialog(project, "Unable to fetch editor instance", "Error!", Messages.getErrorIcon());
            return;
        }

        final int selectionStartIndex = editor.getSelectionModel().getSelectionStart();
        final int selectionFinishIndex = editor.getSelectionModel().getSelectionEnd();

        final Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);

        if (psiFile == null) {
            return;
        }

        PsiElement firstElement = psiFile.findElementAt(selectionStartIndex);
        PsiElement lastElement = psiFile.findElementAt(selectionFinishIndex);

        if (lastElement == null) {  // avoid error if all file is selected
            lastElement = psiFile.getLastChild();
        }

        if (firstElement == null) { // undefined border
            Messages.showMessageDialog(project, "Unable to fetch selected piece of code", "Error!", Messages.getErrorIcon());
            return;
        }

        PsiElement lca = PsiTreeUtil.findCommonParent(firstElement, lastElement);
        String ast = ASTPrinter.makeAstViewModel(lca, firstElement, lastElement);

        Messages.showMessageDialog(editor.getProject(), ast, "AST", Messages.getInformationIcon());
    }
}