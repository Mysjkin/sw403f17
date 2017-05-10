package Test;

import java.io.IOException;

import CodeGeneration.CodeGenerator;
import DataStructures.AST.AST;
import Semantics.Scope.SemanticAnalyzer;
import Syntax.Parser.Parser;
import Syntax.Scanner.Scanner;

public class CodeGenerationOnSingleFile {

	public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(InputTester.readFile("src/Test/TestPrograms/semantics/SetupNotVoidfail"));
        Parser parser = new Parser(sc);
        AST programTree = parser.ParseProgram();
        SemanticAnalyzer sm = new SemanticAnalyzer();
        sm.BeginSemanticAnalysis(programTree);
        //CodeGenerator c = new CodeGenerator(programTree, sm);
        //c.ToFile();
	}

}
