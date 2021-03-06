/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.intellij.plugins.markdown.highlighting;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class MarkdownHighlightingAnnotator implements Annotator, DumbAware {

    private static final SyntaxHighlighter SYNTAX_HIGHLIGHTER = new MarkdownSyntaxHighlighter();

    @Override public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        final IElementType type = element.getNode().getElementType();
        final TextAttributesKey[] tokenHighlights = SYNTAX_HIGHLIGHTER.getTokenHighlights(type);

        if (tokenHighlights.length > 0) {
            final Annotation annotation = holder.createInfoAnnotation(element, null);
            annotation.setTextAttributes(tokenHighlights[0]);
        }
    }
}
