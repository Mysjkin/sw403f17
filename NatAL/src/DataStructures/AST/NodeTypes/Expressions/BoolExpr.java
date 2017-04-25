package DataStructures.AST.NodeTypes.Expressions;

import Syntax.Tokens.Token;

/**
 * Created by Anders Brams on 3/21/2017.
 */
public class BoolExpr extends Expr
{
    public BoolExpr(Expr expr1, Token operator, Expr expr2)
    {
        Operator = operator;
        AddChild(expr1);
        AddChild(expr2);
        SetValue("BoolExpr");
    }
    public final Token Operator;
    public Expr GetLeftExpr()
    {
        return (Expr) children.get(0);
    }
    public Expr GetRightExpr()
    {
        return (Expr) children.get(1);
    }
}
