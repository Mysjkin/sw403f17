package p4test.SyntaxAnalysis;

import p4test.AbstractSyntaxTree.AST;
import p4test.AbstractSyntaxTree.Dcl.VarDcl;
import p4test.AbstractSyntaxTree.Types;
import p4test.DefaultHashMap;
import p4test.Token;
import p4test.TokenType;

import java.util.*;

/**
 * Created by mysjkin on 3/6/17.
 */
public class TableDrivenParser
{
    // Can be optimized to use integers instead of strings
    private Stack<String> ParseStack;

    private ParsingTable table;
    private Scanner input;
    private Stack<String> parseStack;
    private Stack<Token> terminalsStack;
    private Token CurrentToken;
    private ASTFactory AstFactory;

    private List<String> terminals;

    public TableDrivenParser(Scanner input)
    {
        this.input = input;
        this.table = new ParsingTable();
        terminals = new ArrayList<>();

        CurrentToken = input.nextToken();

    }

    /* Parses the input program and returns the AST for the program */
    public AST ParseProgram()
    {
        parseStack = new Stack<String>(); /* RHS symbols for productions and terminals */
        terminalsStack = new Stack<Token>();
        AstFactory = new ASTFactory(terminalsStack);
        Apply(table.GetPrediction("Program", CurrentToken).Right); /* Push RHS symbols for the productions of "Program". */
        boolean accepted = false;
        AST programTree = new AST();

        while (!accepted)
        {
            /* If the next RHS symbol is a terminal, or if the current token is an identifier,
             * try to match the symbol with the current token.
             * If the parseStack is empty and the current token is EOF, end the parsing. */
            if (table.IsTerminal(parseStack.peek()) || CurrentToken.Type.equals(TokenType.IDENTIFIER))
            {
                terminalsStack.push(CurrentToken);
                Match(parseStack.peek(), CurrentToken);
                if (parseStack.size() == 0 && CurrentToken.Type.equals(TokenType.EOF))
                {
                    accepted = true;
                }
                parseStack.pop();
            }
            else
            {
                /* If the next right hand side symbol is the empty string, pop it, and
                 * check for empty parseStack and EOF current token. End the parsing if
                 * both of these are true. */
                if(parseStack.peek() != null && parseStack.peek().equals("EPSILON"))
                {
                    parseStack.pop();
                    if(parseStack.size() == 0 && !CurrentToken.Type.equals(TokenType.EOF))
                        throw new Error("lel");
                    else if (parseStack.size() == 0)
                    {
                        accepted = true;
                        break;
                    }
                }
                else
                {
                    /* Acquire the RHS symbols for the production of the next RHS symbol
                     * in the parseStack. If there are no productions, throw an error.
                     * Otherwise, push the productions' RHS symbols to the parseStack. */
                    Production productions = table.GetPrediction(parseStack.peek(), CurrentToken);
                    String[] RHSSymbols = productions != null ? productions.Right : null;
                    if (RHSSymbols == null)
                    {
                        throw new Error("No productions available.");
                    }
                    else
                    {
                        parseStack.pop();
                        Apply(RHSSymbols);
                    }
                }
            }
        }
        return AstFactory.program;
    }

    /* Pushes the input list of productions onto the
     * parseStack in reverse order, so that the first
      * production rule in the list is the first one popped */
    private void Apply(String[] RHSSymbols)
    {
        int iterations = RHSSymbols.length;
        for(int i = iterations-1; i>=0; i--)
        {
            parseStack.push(RHSSymbols[i]);
        }
    }

    /* If the two input tokens match by type, retrieve
     * the next token in the scanner */
    private void Match(TokenType type, Token token)
    {
        if(type.equals(token.Type))
            Consume();
        else
            throw new Error("lel");
    }

    /* If the given if the val and the string version of token
     * are identical, retrieve the next token in the scanner */
    private void Match(String val, Token token)
    {
        //String value = GetMatchVal(token);
        if(val.equals(val))
        {
            System.out.println("matched " + val);
            Consume();
        }
        else
            throw new Error("Got " + val + " expected " + val);
    }

    /* Retrieves the comparable version of the given token
     * Based on the value of the token */
    private String GetMatchVal(Token token)
    {
        switch (token.Value)
        {
            case "number":
                return "Type";
            case "\\n":
                return "EOL";
        }
        switch (token.Type)
        {
            case IDENTIFIER:
                return "Identifier";
        }
        return "sentinel";
    }

    /* Sets current token to the next token found by the scanner */
    private void Consume()
    {
        CurrentToken = input.nextToken();
    }
}