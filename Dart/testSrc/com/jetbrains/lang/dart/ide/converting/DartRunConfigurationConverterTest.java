package com.jetbrains.lang.dart.ide.converting;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.javascript.debugger.execution.JavascriptDebugConfigurationType;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.testFramework.LightPlatformTestCase;
import com.jetbrains.lang.dart.ide.DartRunConfigurationConverterProvider;
import com.jetbrains.lang.dart.util.DartTestUtils;
import org.jdom.Element;

import java.io.File;

public class DartRunConfigurationConverterTest extends LightPlatformTestCase {
  private static String getBaseDataPath() {
    return DartTestUtils.BASE_TEST_DATA_PATH + FileUtil.toSystemDependentName("/converter/");
  }

  public void doTest(String oldFileName, String newFileName) throws Exception {
    final Element oldRoot = loadElement(oldFileName);
    assertTrue(DartRunConfigurationConverterProvider.isConversionNeeded(oldRoot));
    if (ConfigurationType.CONFIGURATION_TYPE_EP.findExtension(JavascriptDebugConfigurationType.class) == null) {
      return;// launched from 'Dart-plugin' project
    }
    DartRunConfigurationConverterProvider.converter(oldRoot);
    assertElementEquals(loadElement(newFileName), oldRoot);
  }

  protected Element loadElement(final String filePath) throws Exception {
    return JDOMUtil.load(new File(getBaseDataPath(), filePath));
  }

  private static void assertElementEquals(final Element expected, final Element actual) {
    String expectedText = JDOMUtil.createOutputter("\n").outputString(expected);
    String actualText = JDOMUtil.createOutputter("\n").outputString(actual);
    assertEquals(expectedText, actualText);
  }

  public void testLocal() throws Exception {
    doTest("LocalDartApplicationConfiguration.xml", "LocalJSConfiguration.xml");
  }

  public void testRemote() throws Exception {
    doTest("RemoteDartApplicationConfiguration.xml", "RemoteJSConfiguration.xml");
  }
}
