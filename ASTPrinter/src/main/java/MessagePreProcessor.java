import com.intellij.codeInsight.editorActions.CopyPastePreProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RawText;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class that extend logic on copy-paste actions
 */
public class MessagePreProcessor implements CopyPastePreProcessor {

    /**
     * Nothing to do
     */
    @Nullable
    @Override
    public String preprocessOnCopy(PsiFile file, int[] startOffsets, int[] endOffsets, String text) {
        return null;
    }

    /**
     * Method that shows alert about copy-paste
     */
    @NotNull
    @Override
    public String preprocessOnPaste(Project project, PsiFile file, Editor editor, String text, RawText rawText) {
        Messages.showMessageDialog(editor.getProject(), "Copy-Paste is evil!", "Copy-Paste is evil!", Messages.getWarningIcon());
        return text;
    }
}