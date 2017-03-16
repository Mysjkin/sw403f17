package p4test.AbstractSyntaxTree.Visualizing;

import p4test.AbstractSyntaxTree.AST;
import p4test.SyntaxAnalysis.Scanner;
import p4test.SyntaxAnalysis.TableDrivenParser;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by Anders Brams on 3/15/2017.
 */
public class Visualizer extends PApplet {

    Scanner sc;
    VisualNode visTree;
    TableDrivenParser parser;

    public void settings(){
        size(1000,800); //Because for some reason this has to be here
    }

    public void setup(){
        /* Visualizer settings */
        textSize(12);

        /* Scanner and parser */
        String code = "void func1()\n number b is a\n if (a equals b)\n number b is a+1\n end if\n end func1 \n";
        sc = new p4test.SyntaxAnalysis.Scanner(code);
        parser = new TableDrivenParser(sc);
        AST programTree = parser.ParseProgram();
        BuildVisualTree(programTree);
    }

    /* Runs once every frame */
    public void draw(){
        background(255);
        if (visTree != null){
            visTree.Show(this);
        }
        AdjustPositions();
    }

    /* Builds the visual tree from an AST */
    public void BuildVisualTree(AST tree){
        visTree = new VisualNode(tree, new PVector(400, 50)); //Root in the top-middle of the screen
        ArrayList<VisualNode> visTreeList = new ArrayList<VisualNode>();
        visTree.AssignPositionsToChildren(visTreeList);
    }

    void AdjustPositions(){
        
    }

    /* Called from Test.Main(), initializes the Processing Unit */
    public void Show(){
        PApplet.main(Visualizer.class.getName());
    }
}
