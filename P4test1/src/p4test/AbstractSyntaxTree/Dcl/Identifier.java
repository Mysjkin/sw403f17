package p4test.AbstractSyntaxTree.Dcl;

import p4test.AbstractSyntaxTree.AST;
import p4test.AbstractSyntaxTree.Expr.Expression;

/**
 * Created by mysjkin on 3/10/17.
 */
public class Identifier extends Expression
{
    public Identifier(String id)
    {
        ID = id;
    }
    public String ID;
}

