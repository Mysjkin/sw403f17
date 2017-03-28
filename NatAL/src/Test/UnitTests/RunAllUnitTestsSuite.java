package Test.UnitTests;

import Test.UnitTests.SyntaxAnalysisTest.ParserTest.ParsingTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Test.UnitTests.SyntaxAnalysisTest.GrammarTest.GrammarTest;
import Test.UnitTests.SyntaxAnalysisTest.GrammarTest.LLOneTest;
import Test.UnitTests.Semantics.ScopeTest;
import Test.UnitTests.SyntaxAnalysisTest.ScannerTest;
import Test.UnitTests.SyntaxAnalysisTest.ParserTest.ParserTest;

@RunWith(Suite.class)
@SuiteClasses({ParserTest.class, ScannerTest.class, ParsingTableTest.class, GrammarTest.class, LLOneTest.class, ScopeTest.class})
public class RunAllUnitTestsSuite {

}
