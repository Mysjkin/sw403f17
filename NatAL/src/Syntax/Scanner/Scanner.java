package Syntax.Scanner;
import DataStructures.DefaultHashMap;
import Exceptions.*;
import Syntax.Tokens.Token;
import Syntax.Tokens.TokenType;
import Utilities.Reporter;

public class Scanner
{
    /* Uses string builder maybe more effective without */
    public static final char EOF = (char)-1;

    public static DefaultHashMap<String, TokenType> words =
            new DefaultHashMap<String, TokenType>(TokenType.IDENTIFIER);

    protected String input;
    protected int index = 0;
    protected char currentChar;
    protected int inputLength;
    private static int lineNumber;
    public static int GetLineNumber()
    {
        return lineNumber;
    }

    public Scanner (String input)
    {
        this.input = input;
        inputLength = input.length();
        currentChar = input.charAt(index);
        this.lineNumber = 1;
        // Operators
        words.put("or", TokenType.OPERATOR); words.put("and", TokenType.OPERATOR);
        words.put("equals", TokenType.OPERATOR); words.put("is", TokenType.OPERATOR);
        words.put("not", TokenType.OPERATOR); words.put("above", TokenType.OPERATOR);
        words.put("below", TokenType.OPERATOR);

        // Keywords
        words.put("until", TokenType.KEYWORD); words.put("end", TokenType.KEYWORD);
        words.put("if", TokenType.KEYWORD); words.put("else", TokenType.KEYWORD);
        words.put("string", TokenType.KEYWORD); words.put("boolean", TokenType.KEYWORD);
        words.put("return", TokenType.KEYWORD); words.put("void", TokenType.KEYWORD);
        words.put("structure", TokenType.KEYWORD); words.put("fraction", TokenType.KEYWORD);
        words.put("number", TokenType.KEYWORD);  words.put("text", TokenType.KEYWORD);
        words.put("character", TokenType.KEYWORD); words.put("list", TokenType.KEYWORD);
        words.put("of", TokenType.KEYWORD); words.put("digital", TokenType.KEYWORD);
        /*words.put("analog", TokenType.KEYWORD);
        words.put("high", TokenType.KEYWORD); words.put("low", TokenType.KEYWORD);*/

        // Other
        words.put("false", TokenType.BOOLEAN_LITERAL); words.put("true", TokenType.BOOLEAN_LITERAL);
        
        while (IsWS() || currentChar == '\n')
    	{
    		Advance();
    	}
    }

    public void Advance ()
    {
        index++;
        if (index >= inputLength)
        {
            currentChar = EOF;
        }
        else
        {
            if (currentChar == '\n')
                lineNumber++;

            currentChar = input.charAt(index);
        }
    }

    public Token NextToken()
    {	
        while(currentChar != EOF)
        {
            switch (currentChar)
            {
                case '\n':
                    Advance();
                    return MakeToken("\\n", TokenType.SEPARATOR);
                case '(':case ')':case ',':
                    Token sp = MakeToken(Character.toString(currentChar), TokenType.SEPARATOR);
                    Advance();
                return sp;
                case '+':case '-':case '/':case '*':
                    Token op = MakeToken(Character.toString(currentChar), TokenType.OPERATOR);
                    Advance();
                return op;
                case '\'':
                    return ScanChar();
                case '\"':
                    return ScanString();
                case '.' :case '[':case ']':
                	Token ac = MakeToken(Character.toString(currentChar), TokenType.ACCESSOR);
                	Advance();
                	return ac;
                default:
                    if(IsLetter())
                        return ScanLetters();
                    else if(Character.isDigit(currentChar))
                        return ScanDigit();
                    else if(IsWS())
                        Advance();
                    else
                        Reporter.Error(new InvalidCharacterSequenceException("Invalid character encountered: " + currentChar));
            }
        }

        return MakeToken("EOF", TokenType.EOF);
    }

    private boolean IsWS()
    {
        return (currentChar == ' ' || currentChar == '\t' || currentChar == '\r');
    }

    private boolean IsLetter()
    {
        return (currentChar >= 'a' && currentChar <= 'z') ||
                (currentChar >= 'A' && currentChar <= 'Z');
    }
    private Token ScanChar()
    {
        String val = "";
        do
        {
            val += currentChar;
            Advance();
        } while(currentChar != '\'');
        val+= currentChar;
        Advance();

        return MakeToken(val, TokenType.CHAR_LITERAL);
    }
    private Token ScanString()
    {
        StringBuilder sb = new StringBuilder();
        do
        {
            sb.append(currentChar);
            Advance();
        } while(currentChar != '\"');
        sb.append(currentChar);
        Advance();
        return MakeToken(sb.toString(), TokenType.STRING_LITERAL);
    }

    private Token ScanDigit()
    {
        StringBuilder sb = new StringBuilder();
        TokenType type = TokenType.INTEGER_LITERAL;
        do
        {
            sb.append(currentChar);

            if(IsLetter())
                Reporter.Error(new InvalidIdentifierException(sb + " is not a valid number"));

            Advance();
            if(currentChar == '.' && type == TokenType.INTEGER_LITERAL) {
                type = TokenType.FLOAT_LITERAL;
                sb.append(currentChar);
                Advance();
            }
        } while(Character.isDigit(currentChar) || IsLetter());
        return MakeToken(sb.toString(), type);
    }

    private Token ScanLetters()
    {
        StringBuilder sb = new StringBuilder();
        do
        {
            sb.append(currentChar);
            Advance();
        } while((IsLetter() || Character.isDigit(currentChar)) && !IsWS());

        String val = sb.toString();
        TokenType type = words.get(val);

        if(type != TokenType.IDENTIFIER)
            return MakeToken(val, type);

        return MakeToken(val, TokenType.IDENTIFIER);
    }

    private Token MakeToken (String value, TokenType type)
    {
        return new Token(value, type, lineNumber);
    }
}
